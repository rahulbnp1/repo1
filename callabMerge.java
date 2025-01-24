import java.sql.*;
import java.util.Properties;

public class callabMerge{
    public static void main(String[] args) throws Exception{

        try {
           //load drivers
           Class.forName("com.mysql.cj.jdbc.Driver");
            
           //established connection
           String url ="jdbc:mysql://localhost:3306/java";
           String username ="root";
           String password = "123456789";
        Connection con = DriverManager.getConnection(url,username,password);
            // Connect to the database and call the stored procedure
            
            CallableStatement cstmt  = con.prepareCall("{call proc1()}");
            boolean hasResult = cstmt.execute();

            // Process the result sets
            while (hasResult) {
                ResultSet rs = cstmt.getResultSet();
                ResultSetMetaData rsmd = rs.getMetaData();
                int numCols = rsmd.getColumnCount();

                // Print the column headers
                for (int i = 1; i <= numCols; i++) 
                {
                    System.out.print(rsmd.getColumnLabel(i) + "\t");
                }
                System.out.println();

                // Print the rows of the result set
                while (rs.next()) {
                    for (int i = 1; i <= numCols; i++) {
                        System.out.print(rs.getString(i) + "\t");
                    }
                    System.out.println();
                }
               System.out.println("\n");
                // Check if there are more result sets
                hasResult = cstmt.getMoreResults();
            }
            cstmt.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        
    }
}
