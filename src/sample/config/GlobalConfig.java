package sample.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GlobalConfig {
    private static final String CONFIG_NAME = "GymDB.properties";
    private static final Properties GLOBAL_COFIG = new Properties();

    public static void initGlobalConfig() throws IOException {
        GLOBAL_COFIG.load(new FileReader(CONFIG_NAME));
    }
    public static String getProperty(String property) {
        return GLOBAL_COFIG.getProperty(property);
    }
}
