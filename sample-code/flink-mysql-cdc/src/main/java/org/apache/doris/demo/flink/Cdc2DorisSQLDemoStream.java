package cdc;

import java.util.*;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import com.ververica.cdc.debezium.table.RowDataDebeziumDeserializeSchema;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;

import org.apache.doris.flink.sink.*;
import org.apache.doris.flink.cfg.*;
import org.apache.doris.flink.sink.writer.*;
import org.apache.flink.table.data.*;
import org.apache.flink.table.types.*;
import org.apache.flink.table.api.*;

public class Cdc2DorisSQLDemoStream {

  public static void main(String[] args) throws Exception {

    // MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
    MySqlSource<RowData> mySqlSource = MySqlSource.<RowData>builder()
        .hostname("10.239.174.41")
        .port(3306)
        .databaseList("test") 
        .tableList("test.flink_mysql_cdc_test")
        .username("root")
        .password("root")
        // .deserializer(new JsonDebeziumDeserializationSchema()) // converts SourceRecord to JSON String
        .deserializer(
          RowDataDebeziumDeserializeSchema      // converts SourceRecord to RowData
          .newBuilder()
               // .setFieldNames(fields)               
               // .setFieldType(types)          // TODO must set those right
          .build())  
        .build();


    //doris sink option
    DorisSink.Builder<RowData> builder = DorisSink.builder();
    // DorisSink.Builder<String> builder = DorisSink.builder();
    DorisOptions.Builder dorisBuilder = DorisOptions.builder();
    dorisBuilder.setFenodes("10.165.40.11:18030")
            .setTableIdentifier("test.flink_mysql_cdc_test")
            .setUsername("root")
            .setPassword("root");

    // json format to streamload
    Properties properties = new Properties();
    // sink.enable-delete
    properties.setProperty("format", "json");
    properties.setProperty("read_json_by_line", "true");
    DorisExecutionOptions.Builder  executionBuilder = DorisExecutionOptions.builder();
    executionBuilder.setLabelPrefix(UUID.randomUUID().toString())  //streamload label prefix
                    .setStreamLoadProp(properties); //streamload params

    //flink rowdata‘s schema
    String[] fields = {"id", "name", "desc", "create_time"};
    DataType[] types = {
      DataTypes.INT(), 
      DataTypes.VARCHAR(256), 
      DataTypes.VARCHAR(256), 
      DataTypes.TIMESTAMP()
    };

    builder.setDorisReadOptions(DorisReadOptions.builder().build())
            .setDorisExecutionOptions(executionBuilder.build())
            .setSerializer(
              // new SimpleStringSerializer()
              RowDataSerializer.builder()    //serialize according to rowdata 
               .setFieldNames(fields)               
               .setFieldType(types)
               .setType("json")           //json format               
               .enableDelete(true)
               .build()
            )
            .setDorisOptions(dorisBuilder.build());


    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    // enable checkpoint
    env.enableCheckpointing(10*1000);

    env
      .fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQL Source").setParallelism(1)
      .sinkTo(builder.build()).setParallelism(1);

    env.execute("Test");



  }
}