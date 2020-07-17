package by.mishota.graduation.resource;

import java.util.Locale;
import java.util.ResourceBundle;

import static by.mishota.graduation.resource.FilePath.*;

public class ConfigurationManager {


    private static final ResourceBundle RESOURCE_BUNDLE=ResourceBundle.getBundle(PAGE_FILE_NAME_PROPERTIES);

    public static String getProperty(String key){
        return RESOURCE_BUNDLE.getString(key);
    }

}
