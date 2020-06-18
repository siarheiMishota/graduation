package by.mishota.graduation.resource;

import java.util.ResourceBundle;

import static by.mishota.graduation.resource.ParamStringManager.PARAM_MESSAGES_NAME;

public class MessageManager {

    private static final ResourceBundle RESOURCE_BUNDLE=ResourceBundle.getBundle(PARAM_MESSAGES_NAME);

    public static String getProperty(String key){
        return RESOURCE_BUNDLE.getString(key);
    }
}
