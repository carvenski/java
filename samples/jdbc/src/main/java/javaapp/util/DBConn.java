package javaapp.util;                                                                                        

import java.util.HashMap;
import java.util.UUID;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

// Mysql Conn
public class DBConn {

        private static String mysql_addr = "jdbc:mysql://localhost:3306/test";
        private static String mysql_user = "root";
        private static String mysql_passwd = "root";
        private static ComboPooledDataSource pool;
        private static HashMap<String, ConnWrapper> map;

        private DBConn (){}
        
        // DBConn单例模式   
        public static synchronized ComboPooledDataSource getPool() {
           if (pool == null) {
                pool = new ComboPooledDataSource();
                map = new HashMap<String, ConnWrapper>();
                try {
                    pool.setDriverClass("com.mysql.jdbc.Driver");
                    pool.setJdbcUrl(mysql_addr);
                    pool.setUser(mysql_user);
                    pool.setPassword(mysql_passwd);
                    pool.setMaxPoolSize(5); // 连接池最大连接数量
                    pool.setInitialPoolSize(3);
                    pool.setAcquireRetryAttempts(3); //定义在从数据库获取新连接失败后重复尝试的次数
                    pool.setAcquireRetryDelay(5000); //获得连接的最大等待毫秒数
                    pool.setMaxIdleTime(600); //最大空闲时间,600秒内未使用则连接被丢弃
                } catch (Exception e) {
                    e.printStackTrace();
                }
           }
           return pool;
        }

        // 每次从连接池获取连接,查询sql,解析结果
        public static String doQuery(String sql) {
          try {                
                pool = getPool();
                Connection cn = pool.getConnection();
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                String k = UUID.randomUUID().toString();
                map.put(k, new ConnWrapper(cn, st, rs));
                return k;
       	   } catch (Exception e) {
               e.printStackTrace();
           }
           return null;
      }

      public static ResultSet getResultSet(String k) {
          return map.get(k).rs;
      }

      // 每次用完连接需要关闭
      public static void close(String k) {
          // close conn, put conn back into conn_pool
          // System.out.println("=> close conn with k="+k);
          try{
            map.get(k).rs.close();
            map.get(k).stat.close();
            map.get(k).conn.close();
            map.remove(k);
          } catch (Exception e) {
               e.printStackTrace();
          }
      }
}

// ConnWrapper object for store Connection/Statement/ResultSet
class ConnWrapper {

    public Connection conn;
    public Statement stat;
    public ResultSet rs;

    public ConnWrapper(Connection conn, Statement stat, ResultSet rs) {
        this.conn = conn;
        this.stat = stat;
        this.rs = rs;
    }
}

