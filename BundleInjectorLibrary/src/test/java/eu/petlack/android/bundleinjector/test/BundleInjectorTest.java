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
        public double injectedVarDouble = 0d;
        @InjectBundle
        public int injectedVarInt = 0;
        @InjectBundle
        public boolean injectedVarBoolean = false;
        @InjectBundle
        public boolean[] injectedVarBooleanArray = { false };
        @InjectBundle
        public byte injectedVarByte = 0;
        @InjectBundle
        public byte[] injectedVarByteArray = { 0 };
        @InjectBundle
        public char injectedVarChar = 'a';

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
        assertEquals(expected, test.injectedVarDouble);
    }

    @Test
    public void testInjectInteger() {
        Bundle bundle = new Bundle();
        int expected = 12345;
        bundle.putInt("injectedVarInt", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarInt);
    }

    @Test
    public void testInjectBoolean() {
        Bundle bundle = new Bundle();
        boolean expected = true;
        bundle.putBoolean("injectedVarBoolean", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarBoolean);
        expected = false;
        bundle.putBoolean("injectedVarBoolean", expected);
        test.inject(bundle);
        assertEquals(expected, test.injectedVarBoolean);
    }

    @Test
    public void testInjectBooleanArray() {
        Bundle bundle = new Bundle();
        boolean[] expected = { true, false };
        bundle.putBooleanArray("injectedVarBooleanArray", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarBooleanArray);
    }

    @Test
    public void testInjectBundle() {
        Bundle bundle = new Bundle();
        Bundle expected = new Bundle();
        expected.putString("test key", "test value");
        bundle.putBundle("injectedVar", expected);
        PublicMember<Bundle> test = new PublicMember<Bundle>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectByte() {
        Bundle bundle = new Bundle();
        byte expected = 123;
        bundle.putByte("injectedVarByte", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarByte);
    }

    @Test
    public void testInjectByteArray() {
        Bundle bundle = new Bundle();
        byte[] expected = { 123, 45 };
        bundle.putByteArray("injectedVarByteArray", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarByteArray);
    }

    @Test
    public void testInjectChar() {
        Bundle bundle = new Bundle();
        char expected = 'X';
        bundle.putChar("injectedVarChar", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarChar);
    }

}
