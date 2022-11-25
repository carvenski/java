package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_individual_caf {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(10*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_individual_caf";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +

              "`_id` int, \n" +
              "`buildId` int, \n" +
              "`build_id` int, \n" +
              "`server` string, \n" +
              "`CR_server` string, \n" +
              "`build_name` string, \n" +
              "`build_trigger_type` string, \n" +
              "`build_type` string, \n" +
              "`unified_build_type` string, \n" +
              "`tcQueuedTime` TIMESTAMP, \n" +
              "`tcStartTime` TIMESTAMP, \n" +
              "`tcFinishTime` TIMESTAMP, \n" +
              "`caf_id` int, \n" +
              "`caf_original_id` int, \n" +
              "`caf_start_time` TIMESTAMP, \n" +
              "`caf_end_time` TIMESTAMP, \n" +
              "`first_run` string, \n" +
              "`caf_test_level` int, \n" +
              "`gerrit_branch` string, \n" +
              "`gerrit_link` string, \n" +
              "`gerrit_project` string, \n" +
              "`gerrit_number` int, \n" +
              "`patch_set` string, \n" +
              "`patch_set_created` TIMESTAMP, \n" +
              "`caf_rerun_flag` string, \n" +
              "`caf_rerun_type` string, \n" +
              "`caf_first_rerun` string, \n" +
              "`caf_test_case_id` int, \n" +
              "`caf_test_type` string, \n" +
              "`codename_and_combo` string, \n" +
              "`control_rest_link` string, \n" +
              "`name` string, \n" +
              "`test_case_config` string, \n" +
              "`test_case_name` string, \n" +
              "`test_case_duration` float, \n" +
              "`rest_link` string, \n" +
              "`result` string, \n" +
              "`simics_release` string, \n" +
              "`sut` string, \n" +
              "`wrapper_id` int, \n" +
              "`trigger_type` string, \n" +
              "`oss_report_link` string, \n" +
              "`starttime` TIMESTAMP, \n" +
              "`finishtime` TIMESTAMP, \n" +
              "`caf_target_start_time` TIMESTAMP, \n" +
              "`caf_target_end_time` TIMESTAMP, \n" +
              "`target_caf_duration` float, \n" +
              "`splunk_indextime` TIMESTAMP, \n" +
              "`splunk_time` TIMESTAMP, \n" +
              "`_intel_calendar_year` int, \n" +
              "`_intel_calendar_workweek` int, \n" +
              "`_natural_year` int, \n" +
              "`_natural_month` string, \n" +
              "`_validity` string, \n" +
              "`_remark` string, \n" +
              "`_created_at` TIMESTAMP, \n" +
              "`_updated_at` TIMESTAMP, \n" +
              "`_mined` tinyint, \n" +
              "`_update_count` int, \n" +
              "`_auto_rerun_be_triggered` tinyint, \n" +                   
       
                "  PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'bios-ci-metrics-staging.sh.intel.com',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'doris_test',\n" +
                "  'password' = 'doris_test@intel',\n" +
                "  'database-name' = 'bios',\n" +
                "  'table-name' = 'individual_caf',\n" +
                "  'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +

              "`_id` int, \n" +
              "`buildId` int, \n" +
              "`build_id` int, \n" +
              "`server` string, \n" +
              "`CR_server` string, \n" +
              "`build_name` string, \n" +
              "`build_trigger_type` string, \n" +
              "`build_type` string, \n" +
              "`unified_build_type` string, \n" +
              "`tcQueuedTime` TIMESTAMP, \n" +
              "`tcStartTime` TIMESTAMP, \n" +
              "`tcFinishTime` TIMESTAMP, \n" +
              "`caf_id` int, \n" +
              "`caf_original_id` int, \n" +
              "`caf_start_time` TIMESTAMP, \n" +
              "`caf_end_time` TIMESTAMP, \n" +
              "`first_run` string, \n" +
              "`caf_test_level` int, \n" +
              "`gerrit_branch` string, \n" +
              "`gerrit_link` string, \n" +
              "`gerrit_project` string, \n" +
              "`gerrit_number` int, \n" +
              "`patch_set` string, \n" +
              "`patch_set_created` TIMESTAMP, \n" +
              "`caf_rerun_flag` string, \n" +
              "`caf_rerun_type` string, \n" +
              "`caf_first_rerun` string, \n" +
              "`caf_test_case_id` int, \n" +
              "`caf_test_type` string, \n" +
              "`codename_and_combo` string, \n" +
              "`control_rest_link` string, \n" +
              "`name` string, \n" +
              "`test_case_config` string, \n" +
              "`test_case_name` string, \n" +
              "`test_case_duration` float, \n" +
              "`rest_link` string, \n" +
              "`result` string, \n" +
              "`simics_release` string, \n" +
              "`sut` string, \n" +
              "`wrapper_id` int, \n" +
              "`trigger_type` string, \n" +
              "`oss_report_link` string, \n" +
              "`starttime` TIMESTAMP, \n" +
              "`finishtime` TIMESTAMP, \n" +
              "`caf_target_start_time` TIMESTAMP, \n" +
              "`caf_target_end_time` TIMESTAMP, \n" +
              "`target_caf_duration` float, \n" +
              "`splunk_indextime` TIMESTAMP, \n" +
              "`splunk_time` TIMESTAMP, \n" +
              "`_intel_calendar_year` int, \n" +
              "`_intel_calendar_workweek` int, \n" +
              "`_natural_year` int, \n" +
              "`_natural_month` string, \n" +
              "`_validity` string, \n" +
              "`_remark` string, \n" +
              "`_created_at` TIMESTAMP, \n" +
              "`_updated_at` TIMESTAMP, \n" +
              "`_mined` tinyint, \n" +
              "`_update_count` int, \n" +
              "`_auto_rerun_be_triggered` tinyint \n" +   

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = '10.165.40.11:18030',\n" +
                "  'table.identifier' = 'test.individual_caf',\n" +
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
 



