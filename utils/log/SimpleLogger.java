import java.util.logging.*;
import java.io.*;

// SimpleLogger.java
public static class KafkaSplitter extends RichFlatMapFunction<ObjectNode, Tuple7<String, String, String, String, String, String, String>> {

        public Logger logger;
        
        @Override
        public void open(Configuration parameters) throws Exception {
            this.logger = Logger.getLogger("map");
            FileHandler fh = new FileHandler("/tmp/flinkmobile.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        }
        
        public static String getStackTrace(Throwable throwable) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                try {throwable.printStackTrace(pw); return sw.toString();} 
                finally {pw.close();}
            }

        @Override
        public void flatMap(ObjectNode objNode, Collector<Tuple2<String, String>> out) {    
            logger.info("into flatMap");            
        }
    }
    
    
    
    
