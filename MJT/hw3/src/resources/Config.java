package resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static String getApiKey() throws IOException {
        Properties properties = new Properties();

        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Properties file not found");
            }

            properties.load(input);
        }

        return properties.getProperty("newsApiKey");
    }
}