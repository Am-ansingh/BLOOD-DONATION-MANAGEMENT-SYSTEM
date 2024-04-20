import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BloodDB{
    static Connection conn = null;
    public Connection connect() throws SQLException{
        try{
            String url="jdbc:mysql://localhost:3306/bloodbank";
            String userName = "root";
            String password = "ansh@sql10";
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
