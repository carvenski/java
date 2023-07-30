package com.finebi.plugin.biproxy.logger;

import com.fr.log.FineLoggerFactory;
import com.fr.log.FineLoggerProvider;
import com.fr.plugin.context.PluginContexts;
import com.fr.stable.StringUtils;

public final class LogUtils {
  private static String LOG_PREFIX = "[报表转发插件] ";
  
  private static final String PLUGIN_VERSION;
  
   private static final FineLoggerProvider LOGGER = FineLoggerFactory.getLogger();
  
  static {
     String version = PluginContexts.currentContext().getMarker().getVersion();
     if (StringUtils.isNotBlank(version)) {
       PLUGIN_VERSION = "[v" + version + "] ";
    } else {
       PLUGIN_VERSION = "[unknown version] ";
    } 
     LOG_PREFIX += PLUGIN_VERSION;
  }
  
  public static void setPrefix(String prefix) {
     if (prefix != null)
       LOG_PREFIX = prefix; 
  }
  
  public static boolean isDebugEnabled() {
     return LOGGER.isDebugEnabled();
  }
  
  public static void debug(String s) {
     LOGGER.debug(LOG_PREFIX + s);
  }
  
  public static void info(String s) {
    LOGGER.info(LOG_PREFIX + s);
  }
  
  public static void error(Exception e) {
     LOGGER.error(LOG_PREFIX + e.getMessage(), e);
  }
  
  public static void error(String s) {
     LOGGER.error(LOG_PREFIX + s);
  }
}
