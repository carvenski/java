package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_fiv_ise_bug {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_fiv_ise_bug";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +

              " `_id` int, \n" +
              " `hsd_id` bigint, \n" +    
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `_mined` tinyint, \n" +
              " `title` STRING, \n" +
              " `closed_date` TIMESTAMP, \n" +
              " `release_affected` STRING, \n" +
              " `release` STRING, \n" +
              " `days_open` int, \n" +
              " `status` STRING, \n" +
              " `submitted_date` TIMESTAMP, \n" +
              " `program_milestone` STRING, \n" +
              " `owner` STRING, \n" +
              " `component` STRING, \n" +
              " `subject` STRING, \n" +
              " `code_review_link` STRING, \n" +
              " `change_id` STRING, \n" +
              " `sha` STRING, \n" +
              " `implemented_in_branch` STRING, \n" +
              " `bios_version` STRING, \n" +
              " `eta` STRING, \n" +
              " `calc_code_review_link` STRING, \n" +
              " `calc_family` STRING, \n" +
              " `calc_prog_product` STRING, \n" +
              " `calc_prog_name` STRING, \n" +
              " `parent_id` STRING, \n" +
              " `parent_subject` STRING, \n" +
              " `parent_status` STRING, \n" +
              " `calc_release` STRING, \n" +              
       
                "  PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'bios-ci-metrics-staging.sh.intel.com',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'doris_test',\n" +
                "  'password' = 'doris_test@intel',\n" +
                "  'database-name' = 'bios',\n" +
                "  'table-name' = 'fiv_ise_bug',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +

                  " `_id` int, \n" +
                  " `hsd_id` bigint, \n" +
                  " `_created_at` TIMESTAMP, \n" +
                  " `_updated_at` TIMESTAMP, \n" +
                  " `_mined` tinyint, \n" +
                  " `title` STRING, \n" +
                  " `closed_date` TIMESTAMP, \n" +
                  " `release_affected` STRING, \n" +
                  " `release` STRING, \n" +
                  " `days_open` int, \n" +
                  " `status` STRING, \n" +
                  " `submitted_date` TIMESTAMP, \n" +
                  " `program_milestone` STRING, \n" +
                  " `owner` STRING, \n" +
                  " `component` STRING, \n" +
                  " `subject` STRING, \n" +
                  " `code_review_link` STRING, \n" +
                  " `change_id` STRING, \n" +
                  " `sha` STRING, \n" +
                  " `implemented_in_branch` STRING, \n" +
                  " `bios_version` STRING, \n" +
                  " `eta` STRING, \n" +
                  " `calc_code_review_link` STRING, \n" +
                  " `calc_family` STRING, \n" +
                  " `calc_prog_product` STRING, \n" +
                  " `calc_prog_name` STRING, \n" +
                  " `parent_id` STRING, \n" +
                  " `parent_subject` STRING, \n" +
                  " `parent_status` STRING, \n" +
                  " `calc_release` STRING \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.fiv_ise_bug',\n" +
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
 



