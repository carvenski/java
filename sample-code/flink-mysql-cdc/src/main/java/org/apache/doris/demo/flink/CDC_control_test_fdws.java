package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_control_test_fdws {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_table_control_test_fdws";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +
          
              " `_id` int, \n" +
              " `_created_at` TIMESTAMP, \n" +
              " `_updated_at` TIMESTAMP, \n" +
              " `_mined` tinyint, \n" +
              " `_update_count` int, \n" +
              " `repo_name` STRING, \n" +
              " `repo_branch` STRING, \n" +
              " `commit_sha` STRING, \n" +
              " `build_name` STRING, \n" +
              " `control_build_link` STRING, \n" +
              " `control_build_report` STRING, \n" +
              " `commit_created_time` TIMESTAMP, \n" +
              " `build_status` STRING, \n" +
              " `control_build_first_run` STRING, \n" +
              " `original_build_link` STRING, \n" +
              " `build_trigger_type` STRING, \n" +
              " `build_queued_time` TIMESTAMP, \n" +
              " `build_start_time` TIMESTAMP, \n" +
              " `build_end_time` TIMESTAMP, \n" +
              " `auto_task_id` STRING, \n" +
              " `control_build_type` STRING, \n" +
              " `pr_number` int, \n" +
              " `change_link` STRING, \n" +
              " `test_start_time` TIMESTAMP, \n" +
              " `test_end_time` TIMESTAMP, \n" +
              " `at_end_time` TIMESTAMP, \n" +
              " `at_start_time` TIMESTAMP, \n" +
              " `et_end_time` TIMESTAMP, \n" +
              " `et_start_time` TIMESTAMP, \n" +
              " `at_test_duration` int, \n" +
              " `et_test_duration` int, \n" +
              " `test_ack` int, \n" +
              " `at_fail` int, \n" +
              " `at_pass` int, \n" +
              " `at_total` int, \n" +
              " `et_fail` int, \n" +
              " `et_pass` int, \n" +
              " `et_total` int, \n" +
              " `fail` int, \n" +
              " `pass` int, \n" +
              " `total` int, \n" +
              " `first_rerun` STRING, \n" +
              " `original_test_link` STRING, \n" +
              " `test_queued_time` TIMESTAMP, \n" +
              " `rerun_type` STRING, \n" +
              " `trigger_type` STRING, \n" +
              " `test_report` STRING, \n" +
              " `at_test_result` STRING, \n" +
              " `et_test_result` STRING, \n" +
              " `test_scope` STRING, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` STRING, \n" +
       
                "  PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'bios-ci-metrics-staging.sh.intel.com',\n" +
                "  'port' = '3306',\n" +
                "  'username' = 'doris_test',\n" +
                "  'password' = 'doris_test@intel',\n" +
                "  'database-name' = 'bios',\n" +
                "  'table-name' = 'control_test_fdws',\n" +
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
              " `repo_name` STRING, \n" +
              " `repo_branch` STRING, \n" +
              " `commit_sha` STRING, \n" +
              " `build_name` STRING, \n" +
              " `control_build_link` STRING, \n" +
              " `control_build_report` STRING, \n" +
              " `commit_created_time` TIMESTAMP, \n" +
              " `build_status` STRING, \n" +
              " `control_build_first_run` STRING, \n" +
              " `original_build_link` STRING, \n" +
              " `build_trigger_type` STRING, \n" +
              " `build_queued_time` TIMESTAMP, \n" +
              " `build_start_time` TIMESTAMP, \n" +
              " `build_end_time` TIMESTAMP, \n" +
              " `auto_task_id` STRING, \n" +
              " `control_build_type` STRING, \n" +
              " `pr_number` int, \n" +
              " `change_link` STRING, \n" +
              " `test_start_time` TIMESTAMP, \n" +
              " `test_end_time` TIMESTAMP, \n" +
              " `at_end_time` TIMESTAMP, \n" +
              " `at_start_time` TIMESTAMP, \n" +
              " `et_end_time` TIMESTAMP, \n" +
              " `et_start_time` TIMESTAMP, \n" +
              " `at_test_duration` int, \n" +
              " `et_test_duration` int, \n" +
              " `test_ack` int, \n" +
              " `at_fail` int, \n" +
              " `at_pass` int, \n" +
              " `at_total` int, \n" +
              " `et_fail` int, \n" +
              " `et_pass` int, \n" +
              " `et_total` int, \n" +
              " `fail` int, \n" +
              " `pass` int, \n" +
              " `total` int, \n" +
              " `first_rerun` STRING, \n" +
              " `original_test_link` STRING, \n" +
              " `test_queued_time` TIMESTAMP, \n" +
              " `rerun_type` STRING, \n" +
              " `trigger_type` STRING, \n" +
              " `test_report` STRING, \n" +
              " `at_test_result` STRING, \n" +
              " `et_test_result` STRING, \n" +
              " `test_scope` STRING, \n" +
              " `_intel_calendar_year` int, \n" +
              " `_intel_calendar_workweek` int, \n" +
              " `_natural_year` int, \n" +
              " `_natural_month` STRING \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'test.control_test_fdws',\n" +
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
 



