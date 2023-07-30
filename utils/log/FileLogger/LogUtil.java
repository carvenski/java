
import java.util.logging.*;
import java.io.*;

public class LogUtil {
	public static Logger getLogger() {
		System.setProperty("java.util.logging.config.file", "./log.properties");
		Logger logger = Logger.getLogger("myLogger");
		logger.setLevel(Level.ALL);
		return logger;
	}

    public static String getStackTrace(Throwable throwable) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            try {
                throwable.printStackTrace(pw);
                return sw.toString();
            } 
            finally {
                pw.close();
            }
    }

}