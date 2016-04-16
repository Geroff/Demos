package com.sharedream.fragmenttest.util;

/**
 * Created by as on 2016/4/16.
 */
public class Utils {
    /* AP Security type */
    public final static int AP_SECURITY_OPEN = 0;
    public final static int AP_SECURITY_WEP = 1;
    public final static int AP_SECURITY_WPA = 2;
    public final static int AP_SECURITY_EAP = 3;
    public static int getSecurity(String capabilities) {
        if (capabilities.toUpperCase().contains("WEP")) {
            return AP_SECURITY_WEP;
        } else if (capabilities.toUpperCase().contains("PSK")) {
            return AP_SECURITY_WPA;
        } else if (capabilities.toUpperCase().contains("EAP")) {
            return AP_SECURITY_EAP;
        }

        return AP_SECURITY_OPEN;
    }

    public static boolean validateString(String string) {
        if (string != null && !string.equals("")) {
            return true;
        }

        return false;
    }


}
