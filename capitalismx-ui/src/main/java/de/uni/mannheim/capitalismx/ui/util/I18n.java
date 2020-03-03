package de.uni.mannheim.capitalismx.ui.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.UIManager;

/**
 * Helper class that takes an enum value as input key and returns the localised String from the correct Message Bundle.
 * @author Alex
 */

public final class I18n {
    private I18n() {
    }

    private static ResourceBundle bundle;

    public static String getMessage(String key) {
        if(bundle == null) {
            bundle = UIManager.getResourceBundle();
        }
        return bundle.getString(key);
    }

    public static String getMessage(String key, Object ... arguments) {
        return MessageFormat.format(getMessage(key), arguments);
    }
    
    public static String getMessage(Enum<?> enumVal) {
        return getMessage(enumVal.toString().toLowerCase());
    }
}