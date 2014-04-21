package eu.petlack.android.bundleinjector;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

/**
 * Created by petlack on 4/19/14.
 */
public class BundleInjector {

    private static final String TAG = "BundleInjector";

    private static HashMap<Class<?>, Class<?>> primitiveRefMapping = new HashMap<Class<?>, Class<?>>();
    static {
        primitiveRefMapping.put(boolean.class, Boolean.class);
        primitiveRefMapping.put(byte.class, Byte.class);
        primitiveRefMapping.put(char.class, Character.class);
        primitiveRefMapping.put(double.class, Double.class);
        primitiveRefMapping.put(float.class, Float.class);
        primitiveRefMapping.put(int.class, Integer.class);
        primitiveRefMapping.put(long.class, Long.class);
        primitiveRefMapping.put(short.class, Short.class);
    }

    public static void inject(Object o, Bundle bundle) {
        if (bundle == null) {
            Log.e(TAG, "Failed to inject from null bundle");
            return;
        }

        for (Field f : o.getClass().getDeclaredFields()) {
            try {
                if (f.isAnnotationPresent(InjectBundle.class)) {
                    f.setAccessible(true);
                    if (bundle.containsKey(f.getName())) {
                        Object value = bundle.get(f.getName());
                        if (typeIsCorrect(f, value)) {
                            if (f.isAccessible()) {
                                Log.d(TAG, String.format("Injecting field %s.%s (%s)", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                                f.set(o, value);
                            } else {
                                Log.w(TAG, String.format("Field %s.%s (%s) is not accessible", o.getClass().getCanonicalName(), f.getName(), f.getType()));
                            }
                        }
                        else {
                            Log.e(TAG, String.format("Field %s.%s (%s) has wrong type, expected type: %s", o.getClass().getCanonicalName(), f.getName(), f.getType(), value.getClass()));
                        }
                    }
                }
            }
            catch (Exception e) {
                Log.e(TAG, String.format("Failed to inject bundle params: %s", e));
            }
        }

    }

    private static boolean typeIsCorrect(Field f, Object value) {
        return f.getType().isAssignableFrom(value.getClass()) ||
                (isPrimitiveRef(f.getType(), value.getClass()));
    }

    private static boolean isPrimitiveRef(Class<?> c1, Class<?> c2) {
        return (
                (primitiveRefMapping.get(c1) != null && primitiveRefMapping.get(c1).equals(c2)) ||
                (primitiveRefMapping.get(c2) != null && primitiveRefMapping.get(c1).equals(c1))
               );
    }

}
