package by.epam.carrentalapp.dao.connection;

import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class);

    private static final String DB_CONFIG_PATH = "database";
    private static ResourceBundle resourceBundle;

    private DatabaseManager() {
    }

    public static String getProperty(String key) {
        if (resourceBundle == null) {
            init();
        }

        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            throw new RuntimeException("No object for the given key found", e);
        }
    }

    private static void init() {
        try {
            resourceBundle = ResourceBundle.getBundle(DB_CONFIG_PATH);
        } catch (MissingResourceException e) {
            throw new RuntimeException("Cannot read valid file by properties file path", e);
        }
    }

}