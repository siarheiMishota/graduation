package by.mishota.graduation.resource;

import java.util.ResourceBundle;

import static by.mishota.graduation.resource.PagePath.*;

public class ConfigurationManager {


    private static final ResourceBundle RESOURCE_BUNDLE=ResourceBundle.getBundle(PARAM_CONFIG_NAME);

    public static String getProperty(String key){
        return RESOURCE_BUNDLE.getString(key);
    }

    public static void main(String[] args) {
        System.out.println( ConfigurationManager.getProperty(PATH_PAGE_LOGIN));

    }
}
