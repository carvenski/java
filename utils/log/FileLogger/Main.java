
import java.util.logging.*;

public class Main {
    
    public static void main(String[] args) throws Exception {

    	Logger logger = LogUtil.getLogger();
    	logger.info("test log");
    	try {
    		int a = 1/0;
    	}
    	catch (Exception e) {
    		logger.info("Exception =>");
    		logger.info(LogUtil.getStackTrace(e));
    	}    	

    }
}