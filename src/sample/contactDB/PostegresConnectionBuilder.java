package sample.contactDB;

import sample.config.GlobalConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostegresConnectionBuilder implements ConnectionBuilder{
    public PostegresConnectionBuilder() {
        try {
            Class.forName(GlobalConfig.getProperty("db.driver.class"));
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Connection getConnection() throws SQLException {
        String url = GlobalConfig.getProperty("db.url");
        String login = GlobalConfig.getProperty("db.login");
        String password = GlobalConfig.getProperty("db.password");
        return DriverManager.getConnection(url, login, password);
    }
}
