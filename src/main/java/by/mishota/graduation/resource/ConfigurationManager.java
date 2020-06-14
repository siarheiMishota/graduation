package by.mishota.graduation.resource;

import java.util.ResourceBundle;

public class ConfigurationManager {

    private static final ResourceBundle RESOURCE_BUNDLE=ResourceBundle.getBundle("resources.config");

    private ConfigurationManager() {
    }

    public static String getProperty(String key){
        return RESOURCE_BUNDLE.getString(key);
    }
}
