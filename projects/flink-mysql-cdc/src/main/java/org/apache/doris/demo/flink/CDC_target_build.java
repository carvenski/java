package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_target_build {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_target_build";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +
        

              " `_id` int, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `build_id` int, \n" +
              " `build_link` varchar(255), \n" +
              " `build_queued_time` TIMESTAMP, \n" +
              " `build_server` varchar(255), \n" +
              " `build_status` varchar(255), \n" +
              " `build_statusText` varchar(255), \n" +
              " `build_type` varchar(255), \n" +
              " `buildname` varchar(255), \n" +
              " `change_link` varchar(255), \n" +
              " `codename` varchar(255), \n" +
              " `combo` varchar(255), \n" +
              " `combo_codename` varchar(255), \n" +
              " `commit_sha` varchar(255), \n" +
              " `commit_created_time` TIMESTAMP, \n" +
              " `build_ack` int, \n" +
              " `duration` int, \n" +
              " `end_time` TIMESTAMP, \n" +
              " `parent_build_link` varchar(255), \n" +
              " `repo_branch` varchar(255), \n" +
              " `repo_name` varchar(255), \n" +
              " `start_time` TIMESTAMP, \n" +
              " `scope` varchar(255), \n" +
              " `target_type` varchar(255), \n" +
              " `target_type_category` varchar(255), \n" +
              " `control_build_id` bigint, \n" +
              " `control_build_first_run` varchar(255), \n" +
              " `original_control_build_link` varchar(255), \n" +
              " `control_build_status` varchar(255), \n" +
              " `control_build_trigger_type` varchar(255), \n" +
              " `control_build_queued_time` TIMESTAMP, \n" +
              " `control_build_start_time` TIMESTAMP, \n" +
              " `control_build_end_time` TIMESTAMP, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` varchar(255), \n" +


                "  PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'bios-ci-metrics-staging.sh.intel.com',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'doris_test',\n" +
                "  'password' = 'doris_test@intel',\n" +
                "  'database-name' = 'bios',\n" +
                "  'table-name' = 'target_build',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +


              " `_id` int, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `build_id` int, \n" +
              " `build_link` varchar(255), \n" +
              " `build_queued_time` TIMESTAMP, \n" +
              " `build_server` varchar(255), \n" +
              " `build_status` varchar(255), \n" +
              " `build_statusText` varchar(255), \n" +
              " `build_type` varchar(255), \n" +
              " `buildname` varchar(255), \n" +
              " `change_link` varchar(255), \n" +
              " `codename` varchar(255), \n" +
              " `combo` varchar(255), \n" +
              " `combo_codename` varchar(255), \n" +
              " `commit_sha` varchar(255), \n" +
              " `commit_created_time` TIMESTAMP, \n" +
              " `build_ack` int, \n" +
              " `duration` int, \n" +
              " `end_time` TIMESTAMP, \n" +
              " `parent_build_link` varchar(255), \n" +
              " `repo_branch` varchar(255), \n" +
              " `repo_name` varchar(255), \n" +
              " `start_time` TIMESTAMP, \n" +
              " `scope` varchar(255), \n" +
              " `target_type` varchar(255), \n" +
              " `target_type_category` varchar(255), \n" +
              " `control_build_id` bigint, \n" +
              " `control_build_first_run` varchar(255), \n" +
              " `original_control_build_link` varchar(255), \n" +
              " `control_build_status` varchar(255), \n" +
              " `control_build_trigger_type` varchar(255), \n" +
              " `control_build_queued_time` TIMESTAMP, \n" +
              " `control_build_start_time` TIMESTAMP, \n" +
              " `control_build_end_time` TIMESTAMP, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` varchar(255) \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.target_build',\n" +
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
 



