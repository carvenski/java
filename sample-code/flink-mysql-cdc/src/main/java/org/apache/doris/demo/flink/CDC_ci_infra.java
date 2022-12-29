package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_ci_infra {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_ci_infra";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +
                "  id INT,\n" +
                "  service_name STRING,\n" +
                "  `time` INT,\n" +
                "  flag INT,\n" +                
                "  PRIMARY KEY(id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'api.k8s-fie.intel.com',\n" +
                "  'port' = '31005',\n" +
                "  'username' = 'fv_db_dev',\n" +
                "  'password' = 'fvdev51$',\n" +
                "  'database-name' = 'ci_infra_dashboard',\n" +
                "  'table-name' = 'ci_infra',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +
                "  id INT,\n" +
                "  service_name STRING,\n" +
                "  `time` INT,\n" +
                "  flag INT\n" +    
                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.ci_infra',\n" +
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
 



