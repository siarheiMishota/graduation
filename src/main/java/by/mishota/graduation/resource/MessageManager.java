package by.mishota.graduation.resource;

import java.util.ResourceBundle;

import static by.mishota.graduation.resource.FilePath.MESSAGE_FILE_NAME_PROPERTIES;

public class MessageManager {

    private static final ResourceBundle RESOURCE_BUNDLE=ResourceBundle.getBundle(MESSAGE_FILE_NAME_PROPERTIES);

    public static String getProperty(String key){
        return RESOURCE_BUNDLE.getString(key);
    }
}
