package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dev
 */
public class MyDB_Connection {
    private Connection conn=null;
 public Connection getConnection() throws SQLException{
     try{
         Class.forName("com.mysql.cj.jdbc.Driver");
         conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/api","root", "");
         return conn;
     }catch(Exception e){
         System.out.print(e);
     }
       return null; 
 }
}
