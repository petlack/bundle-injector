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

        for (Field f : o.getClass().getDeclaredFields()) {
            try {
                System.out.println(String.format("Field %s.%s (%s)", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                if (f.isAnnotationPresent(InjectBundle.class)) {
                    f.setAccessible(true);
                    if (bundle.containsKey(f.getName())) {
                        if (f.isAccessible()) {
                            System.out.println(String.format("Injecting field %s.%s (%s)", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                            Log.d(TAG, String.format("Injecting field %s.%s (%s)", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                            f.set(o, bundle.get(f.getName()));
                        } else {
                            System.out.println(String.format("Field %s.%s (%s) is not accessible", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                            Log.w(TAG, String.format("Field %s.%s (%s) is not accessible", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println(String.format("Failed to inject bundle params: %s", e));
                Log.e(TAG, String.format("Failed to inject bundle params: %s", e));
            }
        }

    }

}
