package by.mishota.graduation.resource;

import java.util.ResourceBundle;

public class MessageManager {

    private static final ResourceBundle RESOURCE_BUNDLE=ResourceBundle.getBundle("resources.messages");

    private MessageManager() {
    }

    public static String getProperty(String key){
        return RESOURCE_BUNDLE.getString(key);
    }
}
