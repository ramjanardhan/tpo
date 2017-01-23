package com.mss.tpo.util;

import java.util.ResourceBundle;
import java.util.MissingResourceException;

public class Properties {

    private static final String BUNDLE_NAME = "com/mss/tpo/config/mscvp";
    private static final ResourceBundle RESOURCE_BUNDLE
            = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String getProperty(String property) {
        try {
            return RESOURCE_BUNDLE.getString(property);
        } catch (MissingResourceException e) {
            return '!' + property + '!';
        }
    }
}
