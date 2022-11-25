package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class Cdc2DorisSQLDemoSQL {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(10*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +
                "  id INT,\n" +
                "  name STRING,\n" +
                "  age INT,\n" +
                "  desc STRING,\n" +
                "  create_time TIMESTAMP,\n" +                
                "  PRIMARY KEY(id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = '10.239.174.41',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'root',\n" +
                "  'password' = 'root',\n" +
                "  'database-name' = 'test',\n" +
                "  'table-name' = 'flink_mysql_cdc_test',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");

        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +
                "id INT,\n" +
                "name STRING,\n" +
                "age INT,\n" +
                "descc STRING,\n" +
                "create_time TIMESTAMP\n" +
                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = '10.165.40.11:18030',\n" +
                "  'table.identifier' = 'test.flink_mysql_cdc_test',\n" +
                "  'username' = 'root',\n" +
                "  'password' = 'root',\n" +    
                "  'sink.label-prefix' = 'doris_label_"+UUID.randomUUID().toString()+"',\n" +               
                "  'sink.enable-delete' = 'true',\n" +
                "  'sink.properties.format' = 'json',\n" +  
                "  'sink.properties.read_json_by_line' = 'true'\n" +
                ")");

        //insert into mysql table to doris table
        tEnv.executeSql("INSERT INTO D select * from S");
    }
}



