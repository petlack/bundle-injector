package eu.petlack.android.bundleinjector;

import android.os.Bundle;

/**
 * Created by petlack on 4/19/14.
 */
public class Utils {

    /**
     * Get string from Bundle or return def if not found.
     * Bundle.getString(key, def) was added in API 12, therefore this method.
     * @param bundle
     * @param key
     * @param def
     * @return
     */
    public static String getString(Bundle bundle, String key, String def) {
        String fromBundle = bundle.getString(key);
        if (fromBundle == null) {
            return def;
        }
        return fromBundle;
    }

}
