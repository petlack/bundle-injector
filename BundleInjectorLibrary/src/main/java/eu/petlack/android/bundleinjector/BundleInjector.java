package eu.petlack.android.bundleinjector;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by petlack on 4/19/14.
 */
public class BundleInjector {

    private static final String TAG = "BundleInjector";

    public static void inject(Object o, Bundle bundle) {
        if (bundle == null) {
            Log.e(TAG, "Failed to inject from null bundle");
            return;
        }
        try {
            for (Field f : o.getClass().getDeclaredFields()) {
                if (f.isAnnotationPresent(InjectBundle.class)) {
                    f.setAccessible(true);
                    if (f.isAccessible()) {
                        Log.d(TAG, String.format("Injecting field %s.%s (%s)", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                        if (f.getType().equals(String.class)) {
                            f.set(o, Utils.getString(bundle, f.getName(), ""));
                        }
                        else if (f.getType().equals(Integer.class)) {
                            f.set(o, bundle.getInt(f.getName(), 0));
                        }
                        else if (f.getType().equals(Double.class)) {
                            f.set(o, bundle.getDouble(f.getName(), 0));
                        }
                        else {
                            Log.w(TAG, String.format("Unknown type of field %s.%s (%s)", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                        }
                    }
                    else {
                        Log.w(TAG, String.format("Field %s.%s (%s) is not accessible", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                    }
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, String.format("Failed to inject bundle params: %s", e));
        }
    }

}
