package eu.petlack.android.bundleinjector.test;

import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import eu.petlack.android.bundleinjector.Utils;

import static junit.framework.Assert.*;

/**
 * Created by petlack on 4/19/14.
 */
@RunWith(RobolectricTestRunner.class)
public class UtilsTest {

    @Test
    public void testGetStringIfStringPresent() {
        Bundle bundle = new Bundle();
        String expected = "test value";
        String key = "test key";
        bundle.putString(key, expected);
        String real = Utils.getString(bundle, key, "def value");
        assertEquals(expected, real);
    }

    @Test
    public void testGetDefStringIfStringNotPresent() {
        Bundle bundle = new Bundle();
        String expected = "def value";
        String key = "test key";
        String real = Utils.getString(bundle, key, expected);
        assertEquals(expected, real);
    }

}
