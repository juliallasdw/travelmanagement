
package travel.and.tourism.management.system;

import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;


    
public class Conn{
    Connection c;
    Statement s;
    Conn(){
        
    
     try
        {
        Class.forName("com.mysql.cj.jdbc.Driver");
        c=DriverManager.getConnection("jdbc:mysql:///travelmanagementsystem","root","");
        s=c.createStatement();
        }
     catch (Exception e)
     {
         e.printStackTrace();
     }
     
        }
public PreparedStatement prepareStatement(String sql) throws SQLException {
    return c.prepareStatement(sql);
}
}
