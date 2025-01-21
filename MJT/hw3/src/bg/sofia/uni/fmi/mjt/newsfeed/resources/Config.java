package bg.sofia.uni.fmi.mjt.newsfeed.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public static String getApiKey() throws IOException {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        }

        return properties.getProperty("newsApiKey");
    }

}