package db;
import java.sql.*;

public class DBConnect {
    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // optional
            String url = "jdbc:mysql://localhost:3306/attendance_db";
            String user = "root";
            String password = "Denniesraju@123";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
