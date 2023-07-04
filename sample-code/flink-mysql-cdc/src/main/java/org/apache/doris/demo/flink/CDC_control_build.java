package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_control_build {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_control_build";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +


            " `splunk_indextime` TIMESTAMP, \n" +
            " `splunk_time` TIMESTAMP, \n" +
            " `wrapper_id` int, \n" +
            " `wrapper_name` varchar(255), \n" +
            " `wrapper_work_dir` varchar(255), \n" +
            " `wrapper_agent_cpu` int, \n" +
            " `wrapper_agent_memory` int, \n" +
            " `wrapper_agent_name` varchar(255), \n" +
            " `preflight_link` varchar(255), \n" +
            " `oss_report_link` varchar(255), \n" +
            " `preflight_status` varchar(255), \n" +
            " `preflight_statusText` STRING, \n" +
            " `preflight_queuedTime` TIMESTAMP, \n" +
            " `preflight_startTime` TIMESTAMP, \n" +
            " `preflight_endTime` TIMESTAMP, \n" +
            " `rest_api` varchar(255), \n" +
            " `build_type` varchar(255), \n" +
            " `build_triggeredBy` varchar(255), \n" +
            " `codename_and_combo` STRING, \n" +
            " `tool_set` varchar(255), \n" +
            " `individual_builds_count` int, \n" +
            " `gerrit_status` varchar(255), \n" +
            " `gerrit_number` int, \n" +
            " `gerrit_link` varchar(255), \n" +
            " `patch_set_created` TIMESTAMP, \n" +
            " `publish_time` TIMESTAMP, \n" +
            " `ci_verify_time` TIMESTAMP, \n" +
            " `ci_duration` int, \n" +
            " `build_duration` int, \n" +
            " `test_duration` int, \n" +
            " `ack_duration` int, \n" +
            " `patch_set` varchar(255), \n" +
            " `gerrit_change_id` varchar(255), \n" +
            " `gerrit_updated` TIMESTAMP, \n" +
            " `gerrit_subject` STRING, \n" +
            " `gerrit_review_owner` varchar(255), \n" +
            " `gerrit_project` varchar(255), \n" +
            " `gerrit_branch` varchar(255), \n" +
            " `caf_id` varchar(255), \n" +
            " `caf_status` varchar(255), \n" +
            " `caf_start_time` TIMESTAMP, \n" +
            " `caf_end_time` TIMESTAMP, \n" +
            " `caf_pss_end_time` TIMESTAMP, \n" +
            " `caf_hw_end_time` TIMESTAMP, \n" +
            " `caf_test_passed_count` int, \n" +
            " `caf_test_failed_count` int, \n" +
            " `caf_test_failed_category` varchar(255), \n" +
            " `caf_test_disabled_count` int, \n" +
            " `caf_test_total_count` int, \n" +
            " `verifiedBy_minus1` varchar(255), \n" +
            " `verifiedBy_plus1` varchar(255), \n" +
            " `testVerifiedBy_minus1` varchar(255), \n" +
            " `testVerifiedBy_plus1` varchar(255), \n" +
            " `kwBy_plus1` varchar(255), \n" +
            " `kwBy_minus1` varchar(255), \n" +
            " `code_reviewers` STRING, \n" +
            " `files_modified` STRING, \n" +
            " `reviewedBy_plus1` STRING, \n" +
            " `reviewedBy_plus2` varchar(255), \n" +
            " `reviewedBy_minus1` varchar(255), \n" +
            " `first_run` varchar(255), \n" +
            " `trigger_type` varchar(255), \n" +
            " `build_vote` varchar(255), \n" +
            " `build_vote_timestamp` TIMESTAMP, \n" +
            " `original_control_build_id` int, \n" +
            " `original_control_build_link` varchar(255), \n" +
            " `original_control_rest_api` varchar(255), \n" +
            " `rerunFlag` varchar(255), \n" +
            " `_id` int, \n" +
            " `server` varchar(255), \n" +
            " `changeUrls` STRING, \n" +
            " `CR_server` varchar(255), \n" +
            " `bios_version` varchar(255), \n" +
            " `CR_PushUrl` varchar(255), \n" +
            " `CR_HeadBranch` varchar(255), \n" +
            " `CR_CommitterEmail` varchar(255), \n" +
            " `CR_CommitterName` varchar(255), \n" +
            " `source` varchar(255), \n" +
            " `_validity`  varchar(255), \n" +
            " `_remark` STRING, \n" +
            " `_created_at` TIMESTAMP, \n" +
            " `_updated_at` TIMESTAMP, \n" +
            " `_build_type` varchar(255), \n" +
            " `_build_duration` int, \n" +
            " `_caf_duration` int, \n" +
            " `_caf_pss_duration` int, \n" +
            " `_caf_hw_duration` int, \n" +
            " `_patchset_ack` int, \n" +
            " `_build_queue_duration` int, \n" +
            " `_build_runtime_duration` int, \n" +
            " `_caf_delay` int, \n" +
            " `_caf_runtime_duration` int, \n" +
            " `_intel_calendar_year` int, \n" +
            " `_intel_calendar_workweek` int, \n" +
            " `_natural_year` int, \n" +
            " `_natural_month` varchar(255), \n" +
            " `_mined` tinyint, \n" +
            " `_update_count` int, \n" +
            " `ab_build_status` varchar(255), \n" +
            " `eb_build_status` varchar(255), \n" +
            " `ab_end_time` TIMESTAMP, \n" +
            " `eb_end_time` TIMESTAMP, \n" +



                "  PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'bios-ci-metrics-staging.sh.intel.com',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'doris_test',\n" +
                "  'password' = 'doris_test@intel',\n" +
                "  'database-name' = 'bios',\n" +
                "  'table-name' = 'control_build',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +


            " `splunk_indextime` TIMESTAMP, \n" +
            " `splunk_time` TIMESTAMP, \n" +
            " `wrapper_id` int, \n" +
            " `wrapper_name` varchar(255), \n" +
            " `wrapper_work_dir` varchar(255), \n" +
            " `wrapper_agent_cpu` int, \n" +
            " `wrapper_agent_memory` int, \n" +
            " `wrapper_agent_name` varchar(255), \n" +
            " `preflight_link` varchar(255), \n" +
            " `oss_report_link` varchar(255), \n" +
            " `preflight_status` varchar(255), \n" +
            " `preflight_statusText` STRING, \n" +
            " `preflight_queuedTime` TIMESTAMP, \n" +
            " `preflight_startTime` TIMESTAMP, \n" +
            " `preflight_endTime` TIMESTAMP, \n" +
            " `rest_api` varchar(255), \n" +
            " `build_type` varchar(255), \n" +
            " `build_triggeredBy` varchar(255), \n" +
            " `codename_and_combo` STRING, \n" +
            " `tool_set` varchar(255), \n" +
            " `individual_builds_count` int, \n" +
            " `gerrit_status` varchar(255), \n" +
            " `gerrit_number` int, \n" +
            " `gerrit_link` varchar(255), \n" +
            " `patch_set_created` TIMESTAMP, \n" +
            " `publish_time` TIMESTAMP, \n" +
            " `ci_verify_time` TIMESTAMP, \n" +
            " `ci_duration` int, \n" +
            " `build_duration` int, \n" +
            " `test_duration` int, \n" +
            " `ack_duration` int, \n" +
            " `patch_set` varchar(255), \n" +
            " `gerrit_change_id` varchar(255), \n" +
            " `gerrit_updated` TIMESTAMP, \n" +
            " `gerrit_subject` STRING, \n" +
            " `gerrit_review_owner` varchar(255), \n" +
            " `gerrit_project` varchar(255), \n" +
            " `gerrit_branch` varchar(255), \n" +
            " `caf_id` varchar(255), \n" +
            " `caf_status` varchar(255), \n" +
            " `caf_start_time` TIMESTAMP, \n" +
            " `caf_end_time` TIMESTAMP, \n" +
            " `caf_pss_end_time` TIMESTAMP, \n" +
            " `caf_hw_end_time` TIMESTAMP, \n" +
            " `caf_test_passed_count` int, \n" +
            " `caf_test_failed_count` int, \n" +
            " `caf_test_failed_category` varchar(255), \n" +
            " `caf_test_disabled_count` int, \n" +
            " `caf_test_total_count` int, \n" +
            " `verifiedBy_minus1` varchar(255), \n" +
            " `verifiedBy_plus1` varchar(255), \n" +
            " `testVerifiedBy_minus1` varchar(255), \n" +
            " `testVerifiedBy_plus1` varchar(255), \n" +
            " `kwBy_plus1` varchar(255), \n" +
            " `kwBy_minus1` varchar(255), \n" +
            " `code_reviewers` STRING, \n" +
            " `files_modified` STRING, \n" +
            " `reviewedBy_plus1` STRING, \n" +
            " `reviewedBy_plus2` varchar(255), \n" +
            " `reviewedBy_minus1` varchar(255), \n" +
            " `first_run` varchar(255), \n" +
            " `trigger_type` varchar(255), \n" +
            " `build_vote` varchar(255), \n" +
            " `build_vote_timestamp` TIMESTAMP, \n" +
            " `original_control_build_id` int, \n" +
            " `original_control_build_link` varchar(255), \n" +
            " `original_control_rest_api` varchar(255), \n" +
            " `rerunFlag` varchar(255), \n" +
            " `_id` int, \n" +
            " `server` varchar(255), \n" +
            " `changeUrls` STRING, \n" +
            " `CR_server` varchar(255), \n" +
            " `bios_version` varchar(255), \n" +
            " `CR_PushUrl` varchar(255), \n" +
            " `CR_HeadBranch` varchar(255), \n" +
            " `CR_CommitterEmail` varchar(255), \n" +
            " `CR_CommitterName` varchar(255), \n" +
            " `source` varchar(255), \n" +
            " `_validity`  varchar(255), \n" +
            " `_remark` STRING, \n" +
            " `_created_at` TIMESTAMP, \n" +
            " `_updated_at` TIMESTAMP, \n" +
            " `_build_type` varchar(255), \n" +
            " `_build_duration` int, \n" +
            " `_caf_duration` int, \n" +
            " `_caf_pss_duration` int, \n" +
            " `_caf_hw_duration` int, \n" +
            " `_patchset_ack` int, \n" +
            " `_build_queue_duration` int, \n" +
            " `_build_runtime_duration` int, \n" +
            " `_caf_delay` int, \n" +
            " `_caf_runtime_duration` int, \n" +
            " `_intel_calendar_year` int, \n" +
            " `_intel_calendar_workweek` int, \n" +
            " `_natural_year` int, \n" +
            " `_natural_month` varchar(255), \n" +
            " `_mined` tinyint, \n" +
            " `_update_count` int, \n" +
            " `ab_build_status` varchar(255), \n" +
            " `eb_build_status` varchar(255), \n" +
            " `ab_end_time` TIMESTAMP, \n" +
            " `eb_end_time` TIMESTAMP \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.control_build',\n" +
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
 



