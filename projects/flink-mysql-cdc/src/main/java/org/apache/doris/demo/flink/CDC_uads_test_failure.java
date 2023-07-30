package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_uads_test_failure {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_uads_test_failure";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +
          
              " `_id` int, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `action` varchar(255), \n" +
              " `auto_task_id` varchar(255), \n" +
              " `bios_version` varchar(255), \n" +
              " `branch` varchar(255), \n" +
              " `build_link` varchar(255), \n" +
              " `meta_subtask_id` bigint, \n" +
              " `case_id` bigint, \n" +
              " `case_name` varchar(255), \n" +
              " `category` varchar(255), \n" +
              " `change_link` varchar(255), \n" +
              " `code` varchar(255), \n" +
              " `combo` varchar(255), \n" +
              " `code_combo` varchar(255), \n" +
              " `commit_sha` varchar(255), \n" +
              " `component` varchar(255), \n" +
              " `configuration` varchar(255), \n" +
              " `duration` int, \n" +
              " `event_publish_time` TIMESTAMP, \n" +
              " `event_routing_key` varchar(255), \n" +
              " `event_source` varchar(255), \n" +
              " `event_type` varchar(255), \n" +
              " `failure_report` varchar(255), \n" +
              " `instance_url` varchar(255), \n" +
              " `issue_type` varchar(255), \n" +
              " `oss_report_link` varchar(255), \n" +
              " `priority` varchar(255), \n" +
              " `queue_time` int, \n" +
              " `repo` varchar(255), \n" +
              " `report_name` varchar(255), \n" +
              " `report_type` varchar(255), \n" +
              " `reporter` varchar(255), \n" +
              " `result` varchar(255), \n" +
              " `scope` varchar(255), \n" +
              " `server` varchar(255), \n" +
              " `solution` STRING, \n" +
              " `solution_id` int, \n" +
              " `start_time` TIMESTAMP, \n" +
              " `status` varchar(255), \n" +
              " `subscriber` varchar(255), \n" +
              " `tags` varchar(255), \n" +
              " `target` varchar(255), \n" +
              " `target_build_link` varchar(255), \n" +
              " `task_type` varchar(255), \n" +
              " `title` varchar(255), \n" +
              " `tracked_link` varchar(255), \n" +
              " `uads_ack` int, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_intel_calendar_weekday` int, \n" +
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
                "  'table-name' = 'uads_test_failure',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +


              " `_id` int, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `action` varchar(255), \n" +
              " `auto_task_id` varchar(255), \n" +
              " `bios_version` varchar(255), \n" +
              " `branch` varchar(255), \n" +
              " `build_link` varchar(255), \n" +
              " `meta_subtask_id` bigint, \n" +
              " `case_id` bigint, \n" +
              " `case_name` varchar(255), \n" +
              " `category` varchar(255), \n" +
              " `change_link` varchar(255), \n" +
              " `code` varchar(255), \n" +
              " `combo` varchar(255), \n" +
              " `code_combo` varchar(255), \n" +
              " `commit_sha` varchar(255), \n" +
              " `component` varchar(255), \n" +
              " `configuration` varchar(255), \n" +
              " `duration` int, \n" +
              " `event_publish_time` TIMESTAMP, \n" +
              " `event_routing_key` varchar(255), \n" +
              " `event_source` varchar(255), \n" +
              " `event_type` varchar(255), \n" +
              " `failure_report` varchar(255), \n" +
              " `instance_url` varchar(255), \n" +
              " `issue_type` varchar(255), \n" +
              " `oss_report_link` varchar(255), \n" +
              " `priority` varchar(255), \n" +
              " `queue_time` int, \n" +
              " `repo` varchar(255), \n" +
              " `report_name` varchar(255), \n" +
              " `report_type` varchar(255), \n" +
              " `reporter` varchar(255), \n" +
              " `result` varchar(255), \n" +
              " `scope` varchar(255), \n" +
              " `server` varchar(255), \n" +
              " `solution` STRING, \n" +
              " `solution_id` int, \n" +
              " `start_time` TIMESTAMP, \n" +
              " `status` varchar(255), \n" +
              " `subscriber` varchar(255), \n" +
              " `tags` varchar(255), \n" +
              " `target` varchar(255), \n" +
              " `target_build_link` varchar(255), \n" +
              " `task_type` varchar(255), \n" +
              " `title` varchar(255), \n" +
              " `tracked_link` varchar(255), \n" +
              " `uads_ack` int, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_intel_calendar_weekday` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` varchar(255) \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.uads_test_failure',\n" +
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
 



