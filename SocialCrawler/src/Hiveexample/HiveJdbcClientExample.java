package iSAT; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

 

public class HiveJdbcClientExample {
	
	 private static String connectionString = "jdbc:hive://192.168.206.130:8888/beeswax/;auth=noSasl";
       private static String driverName = "org.apache.hive.jdbc.HiveDriver";
       private static String queryString = "select count(*) from sample_07";
       private static Connection con;
       private static ResultSet resultSet;
       private static Statement sqlStatement;
       public static void main(String[] args)
       {
               System.out.println("Loaded the driver successfully. Trying to establish connection");
               try
               {
                       Class.forName(driverName);
                       con = DriverManager.getConnection(connectionString);
                       System.out.println("Created connection. Preparing statement");
                       sqlStatement = con.createStatement();
                       System.out.println("Executing "+queryString);
                       resultSet = sqlStatement.executeQuery(queryString);
                       while(resultSet.next())
                       {
                               System.out.println("Result set "+resultSet.getString(1));
                       }
                       con.close();
               }
               catch(SQLException sqle)
               {
                       System.out.println("Got sql exception");
                       sqle.printStackTrace();
               }
               catch(Exception e)
               {
                       System.out.println("Got exception");
                       e.printStackTrace();
               }
       }

}
