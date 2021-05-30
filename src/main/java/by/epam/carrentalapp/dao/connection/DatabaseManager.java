package by.epam.carrentalapp.dao.connection;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DatabaseManager {
    private static final String DB_CONFIG_PATH = "resources/configuration/database.properties";
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
            throw new RuntimeException("couldn't load resources, no object for the given key can be found", e);
        }
    }

    private static void init() {
        try {
            resourceBundle = ResourceBundle.getBundle(DB_CONFIG_PATH);
        } catch (MissingResourceException e) {
            throw new RuntimeException("couldn't load resources, no resource bundle for the specified base name can be found", e);
        }
    }

}
