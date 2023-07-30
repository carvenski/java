package javaapp.orm;

import java.sql.ResultSet;
import javaapp.util.DBConn;

// User 用户表
public class User {

    public static void addUser() {
        try{
            String sql = "select * from User;";
            String k = DBConn.doQuery(sql);
            ResultSet rs = DBConn.getResultSet(k);
            System.out.println(rs);
            while (rs.next()){
                System.out.println("inside");
                System.out.println(rs.getString("id"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("detail"));
            }
            DBConn.close(k);
        }
        catch (Exception e){            
        }
    }


}




