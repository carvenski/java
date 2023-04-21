package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_individual_build {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_individual_build";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +
                        
            " `buildId` string, \n" +
            " `agent_name` string, \n" +
            " `agent_os_name` string, \n" +
            " `buildName` string, \n" +
            " `build_status` string, \n" +
            " `build_wrapper_queue_time` TIMESTAMP, \n" +
            " `build_wrapper_end_time` TIMESTAMP, \n" +
            " `build_wrapper_start_time` TIMESTAMP, \n" +
            " `codename_and_combo` string, \n" +
            " `cpu` string, \n" +
            " `host` string, \n" +
            " `rerun_flag` string, \n" +
            " `rest_link` string, \n" +
            " `statusText` string, \n" +
            " `tcBuildDuration` float, \n" +
            " `tcFinishTime` TIMESTAMP, \n" +
            " `tcFullDuration` float, \n" +
            " `tcQueuedTime` TIMESTAMP, \n" +
            " `tcQueuedDuration` float, \n" +
            " `tcStartTime` TIMESTAMP, \n" +
            " `target_build_duration` float, \n" +
            " `tc_link` string, \n" +
            " `wrapper_id` string, \n" +
            " `wrapper_server` string, \n" +
            " `splunk_indextime` TIMESTAMP, \n" +
            " `splunk_time` TIMESTAMP, \n" +
            " `build_failure` int, \n" +
            " `total_caf_failure` int, \n" +
            " `target_status` string, \n" +
            " `gerrit_project` string, \n" +
            " `gerrit_branch` string, \n" +
            " `_wrapper_trigger_type` string, \n" +
            " `_wrapper_first_run` string, \n" +
            " `_wrapper_rerunFlag` string, \n" +
            " `gerrit_link` string, \n" +
            " `gerrit_number` int, \n" +
            " `patch_set` string, \n" +
            " `patch_set_created` TIMESTAMP, \n" +
            " `build_type` string, \n" +
            " `original_control_build_id` int, \n" +
            " `original_control_rest_api` string, \n" +
            " `_id` int, \n" +
            " `server` string, \n" +
            " `CR_server` string, \n" +
            " `_validity` string, \n" +
            " `_remark` string, \n" +
            " `_created_at` TIMESTAMP, \n" +
            " `_updated_at` TIMESTAMP, \n" +
            " `_wrapper_build_type` string, \n" +
            " `_mined` tinyint, \n" +
            " `_update_count` int, \n" +
            " `_auto_rerun_be_triggered` tinyint, \n" +
            " `_build_failure` tinyint, \n" +
            " `_caf_failure` tinyint, \n" +
            " `_caf_failure_category` string, \n" +
            " `_build_failure_category` string, \n" +
            " `_trunk_failure` tinyint, \n" +
            " `_trunk_failure_category` string, \n" +
            " `_latest_uads_build_job_id` int, \n" +
            " `_latest_uads_caf_job_id` int, \n" +
            " `_latest_uads_job_id` int, \n" +
            " `_intel_calendar_year` int, \n" +
            " `_intel_calendar_workweek` int, \n" +
            " `_natural_year` int, \n" +
            " `_natural_month` string, \n" +
            " `uads_build_start_time` TIMESTAMP, \n" +
            " `uads_build_end_time` TIMESTAMP, \n" +
            " `uads_caf_start_time` TIMESTAMP, \n" +
            " `uads_caf_end_time` TIMESTAMP, \n" +
            " `uads_start_time` TIMESTAMP, \n" +
            " `uads_end_time` TIMESTAMP, \n" +
            " `uads_build_ack` int, \n" +
            " `uads_caf_ack` int, \n" +
            " `uads_ack` int, \n" +
            " `uads_build_duration` int, \n" +
            " `uads_caf_duration` int, \n" +
            " `uads_duration` int, \n" +
            " `uads_queue` int, \n" +
            " `uads_build_queue` int, \n" +
            " `uads_caf_queue` int, \n" +
            " `original_control_build_link` string, \n" +
            " `target_type_category` string, \n" +
            " `target_type` string, \n" +
            " `build_scope` string, \n" +
            " `bios_version` string, \n" +
            " `build_report` string, \n" +
            " `test_report` string, \n" +
            " `codename` string, \n" +
            " `combo` string, \n" +
            " `target_build_ack` int, \n" +
            " `ignore_state` string, \n" +
            " `auto_task_id` int, \n" +
            " `meta_task_id` int, \n" +
            " `original_meta_task_id` int, \n" +
            " `rerun_type` string, \n" +
            " `at_start_time` TIMESTAMP, \n" +
            " `at_end_time` TIMESTAMP, \n" +
            " `at_test_result` string, \n" +
            " `caf_failure` int, \n" +
            " `control_build_link` string, \n" +
            


       
                "  PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'bios-ci-metrics-staging.sh.intel.com',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'doris_test',\n" +
                "  'password' = 'doris_test@intel',\n" +
                "  'database-name' = 'bios',\n" +
                "  'table-name' = 'individual_build',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +

            " `buildId` string, \n" +
            " `agent_name` string, \n" +
            " `agent_os_name` string, \n" +
            " `buildName` string, \n" +
            " `build_status` string, \n" +
            " `build_wrapper_queue_time` TIMESTAMP, \n" +
            " `build_wrapper_end_time` TIMESTAMP, \n" +
            " `build_wrapper_start_time` TIMESTAMP, \n" +
            " `codename_and_combo` string, \n" +
            " `cpu` string, \n" +
            " `host` string, \n" +
            " `rerun_flag` string, \n" +
            " `rest_link` string, \n" +
            " `statusText` string, \n" +
            " `tcBuildDuration` float, \n" +
            " `tcFinishTime` TIMESTAMP, \n" +
            " `tcFullDuration` float, \n" +
            " `tcQueuedTime` TIMESTAMP, \n" +
            " `tcQueuedDuration` float, \n" +
            " `tcStartTime` TIMESTAMP, \n" +
            " `target_build_duration` float, \n" +
            " `tc_link` string, \n" +
            " `wrapper_id` string, \n" +
            " `wrapper_server` string, \n" +
            " `splunk_indextime` TIMESTAMP, \n" +
            " `splunk_time` TIMESTAMP, \n" +
            " `build_failure` int, \n" +
            " `total_caf_failure` int, \n" +
            " `target_status` string, \n" +
            " `gerrit_project` string, \n" +
            " `gerrit_branch` string, \n" +
            " `_wrapper_trigger_type` string, \n" +
            " `_wrapper_first_run` string, \n" +
            " `_wrapper_rerunFlag` string, \n" +
            " `gerrit_link` string, \n" +
            " `gerrit_number` int, \n" +
            " `patch_set` string, \n" +
            " `patch_set_created` TIMESTAMP, \n" +
            " `build_type` string, \n" +
            " `original_control_build_id` int, \n" +
            " `original_control_rest_api` string, \n" +
            " `_id` int, \n" +
            " `server` string, \n" +
            " `CR_server` string, \n" +
            " `_validity` string, \n" +
            " `_remark` string, \n" +
            " `_created_at` TIMESTAMP, \n" +
            " `_updated_at` TIMESTAMP, \n" +
            " `_wrapper_build_type` string, \n" +
            " `_mined` tinyint, \n" +
            " `_update_count` int, \n" +
            " `_auto_rerun_be_triggered` tinyint, \n" +
            " `_build_failure` tinyint, \n" +
            " `_caf_failure` tinyint, \n" +
            " `_caf_failure_category` string, \n" +
            " `_build_failure_category` string, \n" +
            " `_trunk_failure` tinyint, \n" +
            " `_trunk_failure_category` string, \n" +
            " `_latest_uads_build_job_id` int, \n" +
            " `_latest_uads_caf_job_id` int, \n" +
            " `_latest_uads_job_id` int, \n" +
            " `_intel_calendar_year` int, \n" +
            " `_intel_calendar_workweek` int, \n" +
            " `_natural_year` int, \n" +
            " `_natural_month` string, \n" +
            " `uads_build_start_time` TIMESTAMP, \n" +
            " `uads_build_end_time` TIMESTAMP, \n" +
            " `uads_caf_start_time` TIMESTAMP, \n" +
            " `uads_caf_end_time` TIMESTAMP, \n" +
            " `uads_start_time` TIMESTAMP, \n" +
            " `uads_end_time` TIMESTAMP, \n" +
            " `uads_build_ack` int, \n" +
            " `uads_caf_ack` int, \n" +
            " `uads_ack` int, \n" +
            " `uads_build_duration` int, \n" +
            " `uads_caf_duration` int, \n" +
            " `uads_duration` int, \n" +
            " `uads_queue` int, \n" +
            " `uads_build_queue` int, \n" +
            " `uads_caf_queue` int, \n" +
            " `original_control_build_link` string, \n" +
            " `target_type_category` string, \n" +
            " `target_type` string, \n" +
            " `build_scope` string, \n" +
            " `bios_version` string, \n" +
            " `build_report` string, \n" +
            " `test_report` string, \n" +
            " `codename` string, \n" +
            " `combo` string, \n" +
            " `target_build_ack` int, \n" +
            " `ignore_state` string, \n" +
            " `auto_task_id` int, \n" +
            " `meta_task_id` int, \n" +
            " `original_meta_task_id` int, \n" +
            " `rerun_type` string, \n" +
            " `at_start_time` TIMESTAMP, \n" +
            " `at_end_time` TIMESTAMP, \n" +
            " `at_test_result` string, \n" +
            " `caf_failure` int, \n" +
            " `control_build_link` string \n" +            

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.individual_build',\n" +
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
 



