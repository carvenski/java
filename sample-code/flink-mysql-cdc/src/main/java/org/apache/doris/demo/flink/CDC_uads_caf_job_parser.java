package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_uads_caf_job_parser {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_uads_caf_job_parser";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +
          
              " `id` int, \n" +
              " `user` STRING, \n" +
              " `component_name` STRING, \n" +
              " `start_time` TIMESTAMP, \n" +
              " `end_time` TIMESTAMP, \n" +
              " `result` STRING, \n" +
              " `complete` tinyint, \n" +
              " `report` tinyint, \n" +
              " `action_state` tinyint, \n" +
              " `actions` STRING, \n" +
              " `action_scan` tinyint, \n" +
              " `action_sessions` STRING, \n" +
              " `report_link` STRING, \n" +
              " `auto_test_case_id` STRING, \n" +
              " `url` STRING, \n" +
              " `title` STRING, \n" +
              " `server` STRING, \n" +
              " `priority` STRING, \n" +
              " `issue_type` STRING, \n" +
              " `solution_id` int, \n" +
              " `solution` STRING, \n" +
              " `url_result` STRING, \n" +
              " `span` STRING, \n" +
              " `source` STRING, \n" +
              " `matched` STRING, \n" +
              " `tracked_link` STRING, \n" +
              " `sources_description` STRING, \n" +
              " `urls_description` STRING, \n" +
              " `subject` STRING, \n" +
              " `indicator` STRING, \n" +
              " `job_id` int, \n" +
              " `oss_report_link` STRING, \n" +
              " `auto_task_id` STRING, \n" +
              " `agent_name` STRING, \n" +
              " `gerrit_review` STRING, \n" +
              " `repo` STRING, \n" +
              " `branch` STRING, \n" +
              " `type` STRING, \n" +
              " `build_target` STRING, \n" +
              " `combo` STRING, \n" +
              " `case_name` STRING, \n" +
              " `external_id` STRING, \n" +
              " `duration` int, \n" +
              " `queue_time` int, \n" +
              " `case_id` STRING, \n" +
              " `target_build_link` STRING, \n" +
              " `tags` STRING, \n" +
              " `buildId` STRING, \n" +
              " `build_server` STRING, \n" +
              " `_id` int, \n" +              
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` STRING, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `_mined` tinyint, \n" +
              " `_update_count` int, \n" +
              " `_failure_score` STRING, \n" +
              " `_highest_score` tinyint, \n" +
       
                "  PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'bios-ci-metrics-staging.sh.intel.com',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'doris_test',\n" +
                "  'password' = 'doris_test@intel',\n" +
                "  'database-name' = 'bios',\n" +
                "  'table-name' = 'uads_caf_job_parser',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +

              " `id` int, \n" +
              " `user` STRING, \n" +
              " `component_name` STRING, \n" +
              " `start_time` TIMESTAMP, \n" +
              " `end_time` TIMESTAMP, \n" +
              " `result` STRING, \n" +
              " `complete` tinyint, \n" +
              " `report` tinyint, \n" +
              " `action_state` tinyint, \n" +
              " `actions` STRING, \n" +
              " `action_scan` tinyint, \n" +
              " `action_sessions` STRING, \n" +
              " `report_link` STRING, \n" +
              " `auto_test_case_id` STRING, \n" +
              " `url` STRING, \n" +
              " `title` STRING, \n" +
              " `server` STRING, \n" +
              " `priority` STRING, \n" +
              " `issue_type` STRING, \n" +
              " `solution_id` int, \n" +
              " `solution` STRING, \n" +
              " `url_result` STRING, \n" +
              " `span` STRING, \n" +
              " `source` STRING, \n" +
              " `matched` STRING, \n" +
              " `tracked_link` STRING, \n" +
              " `sources_description` STRING, \n" +
              " `urls_description` STRING, \n" +
              " `subject` STRING, \n" +
              " `indicator` STRING, \n" +
              " `job_id` int, \n" +
              " `oss_report_link` STRING, \n" +
              " `auto_task_id` STRING, \n" +
              " `agent_name` STRING, \n" +
              " `gerrit_review` STRING, \n" +
              " `repo` STRING, \n" +
              " `branch` STRING, \n" +
              " `type` STRING, \n" +
              " `build_target` STRING, \n" +
              " `combo` STRING, \n" +
              " `case_name` STRING, \n" +
              " `external_id` STRING, \n" +
              " `duration` int, \n" +
              " `queue_time` int, \n" +
              " `case_id` STRING, \n" +
              " `target_build_link` STRING, \n" +
              " `tags` STRING, \n" +
              " `buildId` STRING, \n" +
              " `build_server` STRING, \n" +
              " `_id` int, \n" +              
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` STRING, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `_mined` tinyint, \n" +
              " `_update_count` int, \n" +
              " `_failure_score` STRING, \n" +
              " `_highest_score` tinyint \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.uads_caf_job_parser',\n" +
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
 



