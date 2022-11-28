package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_onedb_reviews {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(10*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_onedb_reviews";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +

            " `_id` bigint, \n" +
            " `_created_at` TIMESTAMP, \n" +
            " `_updated_at` TIMESTAMP, \n" +
            " `pr_link` varchar, \n" +
            " `review_id` bigint, \n" +
            " `commit_id` varchar, \n" +
            " `state` varchar, \n" +
            " `sender` varchar, \n" +
            " `submitted_at` TIMESTAMP \n" +      
       
                " ,\n   PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'sh-cluster-ingress.iglb.intel.com',\n" +
                "  'port' = '42294',\n" +
                "  'username' = 'doris_sync',\n" +
                "  'password' = 'doris_sync@intel',\n" +
                "  'database-name' = 'onedb_mysql',\n" +
                "  'table-name' = 'reviews',\n" +
                "  'scan.startup.mode' = 'latest-offset',\n" +
                String.format("  'server-id' = '%s' ", args[0]) +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +

            " `_id` bigint, \n" +
            " `_created_at` TIMESTAMP, \n" +
            " `_updated_at` TIMESTAMP, \n" +
            " `pr_link` varchar, \n" +
            " `review_id` bigint, \n" +
            " `commit_id` varchar, \n" +
            " `state` varchar, \n" +
            " `sender` varchar, \n" +
            " `submitted_at` TIMESTAMP \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = '10.165.40.11:18030',\n" +
                "  'table.identifier' = 'fdws_doris.reviews',\n" +
                "  'username' = 'root',\n" +
                "  'password' = 'root',\n" +    
                "  'sink.label-prefix' = 'doris_label_"+UUID.randomUUID().toString()+"',\n" +               
                "  'sink.enable-delete' = 'true',\n" +
                "  'sink.properties.format' = 'json',\n" +  
                "  'sink.properties.read_json_by_line' = 'true'\n" +
                ")");

        //insert into mysql table to doris table
        tEnv.executeSql("INSERT INTO D select * from S ;");
    }
}
 



