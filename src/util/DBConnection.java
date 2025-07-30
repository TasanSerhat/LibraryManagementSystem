
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class DBConnection {
    
//    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
//    private static final String USER = "root";
//    private static final String PASSWORD = "password";
//    
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
    
    private static Connection connection = null;
    
    public static Connection getConnection() {
    try {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        // config.properties dosyasını oku
        Properties props = new Properties();
        InputStream input = new FileInputStream("connection_config.properties");
        props.load(input);

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        connection = DriverManager.getConnection(url, user, password);
    } catch (IOException e) {
        System.err.println("config.properties dosyası okunamadı.");
        e.printStackTrace();
    } catch (SQLException e) {
        System.err.println("Veritabanına bağlanılamadı.");
        e.printStackTrace();
    }

    return connection;
}

            
}
