package cdc;

import java.util.UUID;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CDC_onedb_pull_requests {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // must open checkpoint, Flink Doris Connector write data by it
        env.enableCheckpointing(60*1000);

        env.setParallelism(1);
      
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        
        String jobName = "cdc_onedb_pull_requests";
        tEnv.getConfig().getConfiguration().setString("pipeline.name", jobName);
        
        // register a table in the catalog
        tEnv.executeSql(
            "CREATE TABLE S (\n" +

            " `_id` bigint, \n" +
            " `_created_at` TIMESTAMP, \n" +
            " `_updated_at` TIMESTAMP, \n" +

            " `title` STRING, \n" +
            " `number` int, \n" +
            " `link` varchar, \n" +
            " `state` varchar, \n" +
            " `head_label` STRING, \n" +
            " `head_sha` varchar, \n" +
            " `head_ref` varchar, \n" +
            " `base_label` STRING, \n" +
            " `base_ref` varchar, \n" +
            " `base_sha` varchar, \n" +
            " `created_at` TIMESTAMP, \n" +
            " `updated_at` TIMESTAMP, \n" +
            " `code_review_completed_at` TIMESTAMP, \n" +
            " `build_verified_at` TIMESTAMP, \n" +
            " `test_verified_at` TIMESTAMP, \n" +
            " `controlled_merge_at` TIMESTAMP, \n" +
            " `submittable_at` TIMESTAMP, \n" +
            " `merged_at` TIMESTAMP, \n" +
            " `closed_at` TIMESTAMP, \n" +
            " `labels` STRING, \n" +
            " `merge_commit_sha` varchar, \n" +
            " `draft` tinyint, \n" +
            " `rebaseable` tinyint, \n" +
            " `mergeable` tinyint, \n" +
            " `mergeable_state` varchar, \n" +
            " `merged` tinyint, \n" +
            " `created_by` varchar, \n" +
            " `merged_by` varchar, \n" +
            " `comments` int, \n" +
            " `review_comments` int, \n" +
            " `commits` int, \n" +
            " `additions` int, \n" +
            " `deletions` int, \n" +
            " `changed_files` int, \n" +
            " `repo_name` varchar, \n" +
            " `repo_full_name` varchar, \n" +
            " `repo_branch` varchar, \n" +
            " `repo_private` tinyint, \n" +
            " `source` varchar, \n" +
         
                "\n PRIMARY KEY(_id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'mysql-cdc',\n" +
                "  'hostname' = 'sh-cluster-ingress.iglb.intel.com',\n" +
                "  'port' = '42294',\n" +
                "  'username' = 'doris_sync',\n" +
                "  'password' = 'doris_sync@intel',\n" +
                "  'database-name' = 'onedb_mysql',\n" +
                "  'table-name' = 'pull_requests',\n" +
                "  'scan.startup.mode' = 'latest-offset',\n" +
                String.format("  'server-id' = '%s' ", args[0]) +
                ")");
        
        //doris table
        tEnv.executeSql(
            "CREATE TABLE D (\n" +

            " `_id` bigint, \n" +
            " `_created_at` TIMESTAMP, \n" +
            " `_updated_at` TIMESTAMP, \n" +

            " `title` STRING, \n" +
            " `number` int, \n" +
            " `link` varchar, \n" +
            " `state` varchar, \n" +
            " `head_label` STRING, \n" +
            " `head_sha` varchar, \n" +
            " `head_ref` varchar, \n" +
            " `base_label` STRING, \n" +
            " `base_ref` varchar, \n" +
            " `base_sha` varchar, \n" +
            " `created_at` TIMESTAMP, \n" +
            " `updated_at` TIMESTAMP, \n" +
            " `code_review_completed_at` TIMESTAMP, \n" +
            " `build_verified_at` TIMESTAMP, \n" +
            " `test_verified_at` TIMESTAMP, \n" +
            " `controlled_merge_at` TIMESTAMP, \n" +
            " `submittable_at` TIMESTAMP, \n" +
            " `merged_at` TIMESTAMP, \n" +
            " `closed_at` TIMESTAMP, \n" +
            " `labels` STRING, \n" +
            " `merge_commit_sha` varchar, \n" +
            " `draft` tinyint, \n" +
            " `rebaseable` tinyint, \n" +
            " `mergeable` tinyint, \n" +
            " `mergeable_state` varchar, \n" +
            " `merged` tinyint, \n" +
            " `created_by` varchar, \n" +
            " `merged_by` varchar, \n" +
            " `comments` int, \n" +
            " `review_comments` int, \n" +
            " `commits` int, \n" +
            " `additions` int, \n" +
            " `deletions` int, \n" +
            " `changed_files` int, \n" +
            " `repo_name` varchar, \n" +
            " `repo_full_name` varchar, \n" +
            " `repo_branch` varchar, \n" +
            " `repo_private` tinyint, \n" +
            " `source` varchar \n" +

                ") \n" +
                "WITH (\n" +
                "  'connector' = 'doris',\n" +
                "  'fenodes' = 'doris-web.datainfra.intel.com',\n" +
                "  'table.identifier' = 'fdws_doris.pull_requests',\n" +
                "  'username' = 'root',\n" +
                "  'password' = 'root',\n" +    
                "  'sink.label-prefix' = 'doris_label_"+UUID.randomUUID().toString()+"',\n" +               
                "  'sink.enable-delete' = 'true',\n" +
                "  'sink.properties.format' = 'json',\n" +  
                "  'sink.properties.read_json_by_line' = 'true'\n" +
                ")");

        //insert into mysql table to doris table
        tEnv.executeSql("INSERT INTO D select * from S ;");
    }
}
 



