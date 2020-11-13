package hr.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static Connection instance = null;
    private final Properties props;

    public JDBCUtils(Properties props) {
        this.props = props;
    }

    private Connection getNewConnection() {
        String driver = props.getProperty("hr.jdbc.driver");
        String url = props.getProperty("hr.jdbc.url");
        String user = props.getProperty("hr.jdbc.user");
        String pass = props.getProperty("hr.jdbc.pass");
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver " + e);
        } catch (SQLException e) {
            System.out.println("Error getting connection " + e);
        }
        return con;
    }

    public Connection getConnection() {
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();

        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return instance;
    }
}
