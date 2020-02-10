package by.training.vashkevichyura.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class JDBCManager {

    private static JDBCManager instance;
    private ResourceBundle resourceBundle;
    private static final String RESOURCE_NAME = "jdbc";

    private JDBCManager() {
    }

    public static JDBCManager getInstance() {
        if (instance == null) {
            instance = new JDBCManager();
            Locale.setDefault(Locale.forLanguageTag("ru"));
            instance.resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {

        return (String) resourceBundle.getObject(key);
    }
}
