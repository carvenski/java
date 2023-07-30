package db;

import java.util.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import com.mongodb.client.*;
import com.mongodb.*;

import org.influxdb.*;
import org.influxdb.dto.*;
import org.influxdb.dto.QueryResult.*;


public class Start{

    MongoClient mongoConn;
    MongoCollection<Document> collection;
    InfluxDB influxConn;

    public void start() {
        this.openConn();
        this.readMongo();
        this.closeConn();
    }

    public void openConn() {
        // mongo
        System.out.println("connect mongo...");
        String db = "metrics";
        String collectionName = "agent_reg";
        String username = "root";
        String password = "root";
        String mongoAddr = "localhost";
        int port = 6631;
        this.mongoConn = new MongoClient(mongoAddr, port);
        this.collection = this.mongoConn.getDatabase(db).getCollection(collectionName);
        // influx
        System.out.println("connect influx...");
        String dbName = "metric_second";
        String influxAddr = "http://localhost:8888";
        this.influxConn = InfluxDBFactory.connect(influxAddr, username, password);
        this.influxConn.setDatabase(dbName);
    }

    public void closeConn() {
        System.out.println("close mongo/influx conn...end");
        this.mongoConn.close();
        this.influxConn.close();
    }

    public void readMongo() {        
        // query: db.agent_reg.find({"type":"switch/router"},{"id":1,"ifList.ifIndex":1})
        BasicDBObject query = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.add(new BasicDBObject("type", "switch"));
        values.add(new BasicDBObject("type", "router"));
        query.put("$or", values);
        BasicDBObject keys = new BasicDBObject();
        keys.put("id", 1);
        keys.put("ifList.ifIndex", 1);
        FindIterable<Document> findIterable = this.collection.find(query).projection(keys);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
                Document d = mongoCursor.next();
                String uuid = d.getString("id");
                List<Document> l = (List<Document>)d.get("ifList");
                for(Document dd: l){
                    try {
                        int ifid = dd.getInteger("ifIndex");
                        this.readInflux(uuid, ifid, "ifHCInOctets");
                        this.readInflux(uuid, ifid, "ifHCOutOctets");
                    }
                    catch(Exception e) {
//                        e.printStackTrace();
//                        continue;
                        int ifid = dd.getDouble("ifIndex").intValue();
                        this.readInflux(uuid, ifid, "ifHCInOctets");
                        this.readInflux(uuid, ifid, "ifHCOutOctets");

                    }
                }
        }  
    }

    public void readInflux(String uuid, int ifid, String type) {
            //query influx: SELECT * FROM "5e7b618e43472210393b90b9" WHERE metric='ifHCOutOctets' AND "key"='98'  ORDER BY time
            String sql = String.format("SELECT value FROM \"%s\" WHERE metric='%s' AND \"key\"='%d' ORDER BY time;", uuid, type, ifid);
//            String sql = "SELECT value FROM \"5e7b618e43472210393b90b9\" WHERE metric='ifHCOutOctets' AND \"key\"='98'  ORDER BY time; ";
            System.out.println(sql);
            QueryResult results = this.influxConn.query(new Query(sql));
            for (Result result : results.getResults()) {
                List<Series> series = result.getSeries();
                if (series != null && series.size() > 0 ) {
                    System.out.println("influx query results ok");
                } else{
                    System.out.println("influx no records !");
                    break;
                }
                for (Series serie : series) {
                    List<List<Object>> values = serie.getValues();
                    ArrayList<Double> l1 = new ArrayList<Double>();
                    for (List<Object> value: values) {
                       l1.add( (Double)value.get(1) );
                    }
                    ///////////////////////////////////////////////////
                    // HERE do 95billing for every uuid + ifid + type
                    System.out.println("=> doing 95billing...");
 //                   System.out.println(l1.size());
                    Double sum = this.billing(l1);
                    String res = String.format(">>> Result: sum: %f, uuid: %s, ifid: %d, type: %s\n", sum, uuid, ifid, type);
                    System.out.println(res);
                }
            }
    }

    // 95计费计算逻辑
    public Double billing(ArrayList<Double> l1) {
        try{
//            System.out.println(l1.size());
            ArrayList<Double> l2 = new ArrayList<Double>();
            int time = 30; // 间隔秒数
            for (int i=1; i<l1.size(); i++){
                // 溢出值为0
                if (l1.get(i) == 0){
                    continue;
                }
                //流量算速率
                l2.add( (l1.get(i) - l1.get(i-1)) / time );
            }
            Collections.sort(l2);
            //去掉速率的top 5
            int count = (int)(l2.size() * 0.05);
            int count2 = count;
            while (count2 > 0) {
                l2.remove(l2.size()-1);
                count2--;
            }
 //           System.out.println(l2.size());
            //最后重算95的流量总和
            Double sum = 0.0;
            for (Double d: l2){
                sum += d*time;
            }
            System.out.println( String.format(">>> Process: l1.size: %d, count: %d, l2.size: %d", l1.size(), count, l2.size()) );
//            System.out.println(l1);
//            System.out.println(l2);
            return sum;
        }
        catch(Exception e) {
            e.printStackTrace();
            return 0.0;
        } 
    }
}


