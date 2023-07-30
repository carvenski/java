package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_target_test_fdws {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_target_test_fdws";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +

              " `_id` int, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `_mined` tinyint, \n" +
              " `_update_count` int, \n" +
              " `test_scope` STRING, \n" +
              " `start_time` TIMESTAMP, \n" +
              " `end_time` TIMESTAMP, \n" +
              " `at_test_duration` int, \n" +
              " `et_test_duration` int, \n" +
              " `test_ack` int, \n" +
              " `at_start_time` TIMESTAMP, \n" +
              " `at_end_time` TIMESTAMP, \n" +
              " `et_start_time` TIMESTAMP, \n" +
              " `et_end_time` TIMESTAMP, \n" +
              " `at_test_result` STRING, \n" +
              " `et_test_result` STRING, \n" +
              " `auto_task_id` STRING, \n" +
              " `codename` STRING, \n" +
              " `combo` STRING, \n" +
              " `codename_combo` STRING, \n" +
              " `change_link` STRING, \n" +
              " `commit_sha` STRING, \n" +
              " `control_build_link` STRING, \n" +
              " `control_build_type` STRING, \n" +
              " `first_run` STRING, \n" +
              " `target_build_link` STRING, \n" +
              " `repo_branch` STRING, \n" +
              " `repo_name` STRING, \n" +
              " `category` STRING, \n" +
              " `target_name` STRING, \n" +
              " `target_type` STRING, \n" +
              " `meta_task_id` STRING, \n" +
              " `original_meta_task_id` int, \n" +
              " `queued_time` TIMESTAMP, \n" +
              " `test_name` STRING, \n" +
              " `at_rerun_type` STRING, \n" +
              " `at_trigger_type` STRING, \n" +
              " `et_rerun_type` STRING, \n" +
              " `et_trigger_type` STRING, \n" +
              " `event_source` STRING, \n" +
              " `target_build_start_time` TIMESTAMP, \n" +
              " `target_build_end_time` TIMESTAMP, \n" +
              " `target_build_status` STRING, \n" +
              " `fail` int, \n" +
              " `pass` int, \n" +
              " `total` int, \n" +
              " `at_fail` int, \n" +
              " `at_pass` int, \n" +
              " `at_total` int, \n" +
              " `et_fail` int, \n" +
              " `et_pass` int, \n" +
              " `et_total` int, \n" +
              " `test_report` STRING, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` STRING, \n" +
              " `_validity` STRING, \n" +
              " `at_auto_task_end_time` TIMESTAMP, \n" +
              " `et_auto_task_end_time` TIMESTAMP, \n" +
              " `auto_task_end_time` TIMESTAMP, \n" +
       
                "  PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'bios-ci-metrics-staging.sh.intel.com',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'doris_test',\n" +
                "  'password' = 'doris_test@intel',\n" +
                "  'database-name' = 'bios',\n" +
                "  'table-name' = 'target_test_fdws',\n" +
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
              " `test_scope` STRING, \n" +
              " `start_time` TIMESTAMP, \n" +
              " `end_time` TIMESTAMP, \n" +
              " `at_test_duration` int, \n" +
              " `et_test_duration` int, \n" +
              " `test_ack` int, \n" +
              " `at_start_time` TIMESTAMP, \n" +
              " `at_end_time` TIMESTAMP, \n" +
              " `et_start_time` TIMESTAMP, \n" +
              " `et_end_time` TIMESTAMP, \n" +
              " `at_test_result` STRING, \n" +
              " `et_test_result` STRING, \n" +
              " `auto_task_id` STRING, \n" +
              " `codename` STRING, \n" +
              " `combo` STRING, \n" +
              " `codename_combo` STRING, \n" +
              " `change_link` STRING, \n" +
              " `commit_sha` STRING, \n" +
              " `control_build_link` STRING, \n" +
              " `control_build_type` STRING, \n" +
              " `first_run` STRING, \n" +
              " `target_build_link` STRING, \n" +
              " `repo_branch` STRING, \n" +
              " `repo_name` STRING, \n" +
              " `category` STRING, \n" +
              " `target_name` STRING, \n" +
              " `target_type` STRING, \n" +
              " `meta_task_id` STRING, \n" +
              " `original_meta_task_id` int, \n" +
              " `queued_time` TIMESTAMP, \n" +
              " `test_name` STRING, \n" +
              " `at_rerun_type` STRING, \n" +
              " `at_trigger_type` STRING, \n" +
              " `et_rerun_type` STRING, \n" +
              " `et_trigger_type` STRING, \n" +
              " `event_source` STRING, \n" +
              " `target_build_start_time` TIMESTAMP, \n" +
              " `target_build_end_time` TIMESTAMP, \n" +
              " `target_build_status` STRING, \n" +
              " `fail` int, \n" +
              " `pass` int, \n" +
              " `total` int, \n" +
              " `at_fail` int, \n" +
              " `at_pass` int, \n" +
              " `at_total` int, \n" +
              " `et_fail` int, \n" +
              " `et_pass` int, \n" +
              " `et_total` int, \n" +
              " `test_report` STRING, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` STRING, \n" +
              " `_validity` STRING, \n" +
              " `at_auto_task_end_time` TIMESTAMP, \n" +
              " `et_auto_task_end_time` TIMESTAMP, \n" +
              " `auto_task_end_time` TIMESTAMP \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.target_test_fdws',\n" +
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
 



