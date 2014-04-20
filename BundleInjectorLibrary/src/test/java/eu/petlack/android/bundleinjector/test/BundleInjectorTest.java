package eu.petlack.android.bundleinjector.test;

import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import eu.petlack.android.bundleinjector.BundleInjector;
import eu.petlack.android.bundleinjector.InjectBundle;

import static junit.framework.Assert.*;

/**
 * Created by petlack on 4/19/14.
 */
@RunWith(RobolectricTestRunner.class)
public class BundleInjectorTest {

    class Member {
        public void inject(Bundle bundle) {
            BundleInjector.inject(this, bundle);
        }
    }

    class PublicMember<T> extends Member {
        @InjectBundle
        public T injectedVar;
    }

    class PrivateMember<T> extends Member {
        @InjectBundle
        private T injectedVar;

        public void setInjectedVar(T injectedVar) {
            this.injectedVar = injectedVar;
        }

        public T getInjectedVar() {
            return this.injectedVar;
        }

    }

    class ProtectedMember<T> extends Member {
        @InjectBundle
        protected T injectedVar;

        public void setInjectedVar(T injectedVar) {
            this.injectedVar = injectedVar;
        }

        public T getInjectedVar() {
            return this.injectedVar;
        }
    }

    class PrimitiveMember extends Member {

        @InjectBundle
        private double injectedVarDouble = 0d;
        @InjectBundle
        private int injectedVarInt = 0;
        @InjectBundle
        private boolean injectedVarBoolean = false;

        public double getInjectedVarDouble() {
            return injectedVarDouble;
        }

        public void setInjectedVarDouble(double injectedVarDouble) {
            this.injectedVarDouble = injectedVarDouble;
        }

        public int getInjectedVarInt() {
            return injectedVarInt;
        }

        public void setInjectedVarInt(int injectedVarInt) {
            this.injectedVarInt = injectedVarInt;
        }

        public boolean getInjectedVarBoolean() {
            return injectedVarBoolean;
        }

        public void setInjectedVarBoolean(boolean injectedVarBoolean) {
            this.injectedVarBoolean = injectedVarBoolean;
        }

    }

    @Test
    public void testInjectStringIntoPublicMember() {
        Bundle bundle = new Bundle();
        String expected = "expected value";
        bundle.putString("injectedVar", expected);
        PublicMember<String> test = new PublicMember<String>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectStringIntoPrivateMember() {
        Bundle bundle = new Bundle();
        String expected = "expected value";
        bundle.putString("injectedVar", expected);
        PrivateMember<String> test = new PrivateMember<String>();
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVar());
    }

    @Test
    public void testInjectStringIntoProtectedMember() {
        Bundle bundle = new Bundle();
        String expected = "expected value";
        bundle.putString("injectedVar", expected);
        ProtectedMember<String> test = new ProtectedMember<String>();
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVar());
    }

    @Test
    public void testInjectDouble() {
        Bundle bundle = new Bundle();
        double expected = 2.5f;
        bundle.putDouble("injectedVarDouble", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVarDouble());
    }

    @Test
    public void testInjectInteger() {
        Bundle bundle = new Bundle();
        int expected = 12345;
        bundle.putInt("injectedVarInt", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVarInt());
    }

    @Test
    public void testInjectBoolean() {
        Bundle bundle = new Bundle();
        boolean expected = true;
        bundle.putBoolean("injectedVarBoolean", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVarBoolean());
        expected = false;
        bundle.putBoolean("injectedVarBoolean", expected);
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVarBoolean());
    }

}
