package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_test_case_fdws {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_test_case_fdws";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +
          
              " `_id` int, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `_mined` tinyint, \n" +
              " `_update_count` int, \n" +
              " `case_duration` int, \n" +
              " `case_id` STRING, \n" +
              " `case_name` STRING, \n" +
              " `calc_case_name` STRING, \n" +
              " `calc_case_config` STRING, \n" +
              " `result` STRING, \n" +
              " `comment` STRING, \n" +
              " `start_time` TIMESTAMP, \n" +
              " `end_time` TIMESTAMP, \n" +
              " `log_path` STRING, \n" +
              " `configuration` STRING, \n" +
              " `test_state` STRING, \n" +
              " `auto_task_id` STRING, \n" +
              " `build_type` STRING, \n" +
              " `category` STRING, \n" +
              " `change_link` STRING, \n" +
              " `codename` STRING, \n" +
              " `combo` STRING, \n" +
              " `codename_combo` STRING, \n" +
              " `commit_sha` STRING, \n" +
              " `control_build_link` STRING, \n" +
              " `control_build_type` STRING, \n" +
              " `test_plan_end_time` TIMESTAMP, \n" +
              " `event_publish_time` TIMESTAMP, \n" +
              " `event_routing_key` STRING, \n" +
              " `event_source` STRING, \n" +
              " `event_type` STRING, \n" +
              " `hardware_type` STRING, \n" +
              " `local_build_type` STRING, \n" +
              " `meta_task_id` STRING, \n" +
              " `plan_name` STRING, \n" +
              " `plan_result` STRING, \n" +
              " `plan_state` STRING, \n" +
              " `plan_task_id` STRING, \n" +
              " `test_plan_queued_time` TIMESTAMP, \n" +
              " `repo_branch` STRING, \n" +
              " `repo_name` STRING, \n" +
              " `test_plan_start_time` TIMESTAMP, \n" +
              " `target_build_link` STRING, \n" +
              " `target_name` STRING, \n" +
              " `test_name` STRING, \n" +
              " `test_scope` STRING, \n" +
              " `test_type` STRING, \n" +
              " `simics_release` STRING, \n" +
              " `device_type` STRING, \n" +
              " `tcd_id` STRING, \n" +
              " `tcd_title` STRING, \n" +
              " `tcd_domain` STRING, \n" +
              " `feature_id` STRING, \n" +
              " `bios_version` STRING, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` STRING, \n" +
              " `_auto_rerun_be_triggered` tinyint, \n" +
              " `failure_issue_title` STRING, \n" +
              " `failure_issue_type` STRING, \n" +
              " `failure_report` STRING, \n" +
              " `failure_solution` STRING, \n" +
       
                "  PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'bios-ci-metrics-staging.sh.intel.com',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'doris_test',\n" +
                "  'password' = 'doris_test@intel',\n" +
                "  'database-name' = 'bios',\n" +
                "  'table-name' = 'test_case_fdws',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +

              " `_id` int, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `_mined` tinyint, \n" +
              " `_update_count` int, \n" +
              " `case_duration` int, \n" +
              " `case_id` STRING, \n" +
              " `case_name` STRING, \n" +
              " `calc_case_name` STRING, \n" +
              " `calc_case_config` STRING, \n" +
              " `result` STRING, \n" +
              " `comment` STRING, \n" +
              " `start_time` TIMESTAMP, \n" +
              " `end_time` TIMESTAMP, \n" +
              " `log_path` STRING, \n" +
              " `configuration` STRING, \n" +
              " `test_state` STRING, \n" +
              " `auto_task_id` STRING, \n" +
              " `build_type` STRING, \n" +
              " `category` STRING, \n" +
              " `change_link` STRING, \n" +
              " `codename` STRING, \n" +
              " `combo` STRING, \n" +
              " `codename_combo` STRING, \n" +
              " `commit_sha` STRING, \n" +
              " `control_build_link` STRING, \n" +
              " `control_build_type` STRING, \n" +
              " `test_plan_end_time` TIMESTAMP, \n" +
              " `event_publish_time` TIMESTAMP, \n" +
              " `event_routing_key` STRING, \n" +
              " `event_source` STRING, \n" +
              " `event_type` STRING, \n" +
              " `hardware_type` STRING, \n" +
              " `local_build_type` STRING, \n" +
              " `meta_task_id` STRING, \n" +
              " `plan_name` STRING, \n" +
              " `plan_result` STRING, \n" +
              " `plan_state` STRING, \n" +
              " `plan_task_id` STRING, \n" +
              " `test_plan_queued_time` TIMESTAMP, \n" +
              " `repo_branch` STRING, \n" +
              " `repo_name` STRING, \n" +
              " `test_plan_start_time` TIMESTAMP, \n" +
              " `target_build_link` STRING, \n" +
              " `target_name` STRING, \n" +
              " `test_name` STRING, \n" +
              " `test_scope` STRING, \n" +
              " `test_type` STRING, \n" +
              " `simics_release` STRING, \n" +
              " `device_type` STRING, \n" +
              " `tcd_id` STRING, \n" +
              " `tcd_title` STRING, \n" +
              " `tcd_domain` STRING, \n" +
              " `feature_id` STRING, \n" +
              " `bios_version` STRING, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` STRING, \n" +
              " `_auto_rerun_be_triggered` tinyint, \n" +
              " `failure_issue_title` STRING, \n" +
              " `failure_issue_type` STRING, \n" +
              " `failure_report` STRING, \n" +
              " `failure_solution` STRING \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.test_case_fdws',\n" +
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
 



