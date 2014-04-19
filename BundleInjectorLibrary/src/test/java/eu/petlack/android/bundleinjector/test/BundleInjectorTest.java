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

    class PublicStringMember extends Member {
        @InjectBundle
        public String injectedVar;
    }

    class PrivateStringMember extends Member {
        @InjectBundle
        private String injectedVar;

        public void setInjectedVar(String injectedVar) {
            this.injectedVar = injectedVar;
        }

        public String getInjectedVar() {
            return this.injectedVar;
        }

    }

    class ProtectedStringMember extends Member {
        @InjectBundle
        protected String injectedVar;

        public void setInjectedVar(String injectedVar) {
            this.injectedVar = injectedVar;
        }

        public String getInjectedVar() {
            return this.injectedVar;
        }
    }

    class DoubleMember extends Member {
        @InjectBundle
        private double injectedVar = 0d;

        public void setInjectedVar(double injectedVar) {
            this.injectedVar = injectedVar;
        }

        public double getInjectedVar() {
            return this.injectedVar;
        }

    }

    class IntegerMember extends Member {
        @InjectBundle
        private int injectedVar = 0;

        public void setInjectedVar(int injectedVar) {
            this.injectedVar = injectedVar;
        }

        public int getInjectedVar() {
            return this.injectedVar;
        }

    }

    @Test
    public void testInjectStringIntoPublicMember() {
        Bundle bundle = new Bundle();
        String expected = "expected value";
        bundle.putString("injectedVar", expected);
        PublicStringMember test = new PublicStringMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectStringIntoPrivateMember() {
        Bundle bundle = new Bundle();
        String expected = "expected value";
        bundle.putString("injectedVar", expected);
        PrivateStringMember test = new PrivateStringMember();
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVar());
    }

    @Test
    public void testInjectStringIntoProtectedMember() {
        Bundle bundle = new Bundle();
        String expected = "expected value";
        bundle.putString("injectedVar", expected);
        ProtectedStringMember test = new ProtectedStringMember();
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVar());
    }

    @Test
    public void testInjectDouble() {
        Bundle bundle = new Bundle();
        double expected = 2.5f;
        bundle.putDouble("injectedVar", expected);
        DoubleMember test = new DoubleMember();
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVar());
    }

    @Test
    public void testInjectInteger() {
        Bundle bundle = new Bundle();
        int expected = 12345;
        bundle.putInt("injectedVar", expected);
        IntegerMember test = new IntegerMember();
        test.inject(bundle);
        assertEquals(expected, test.getInjectedVar());
    }

}
