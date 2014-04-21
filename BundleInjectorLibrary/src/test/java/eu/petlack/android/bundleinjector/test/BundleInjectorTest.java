package eu.petlack.android.bundleinjector.test;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.Serializable;
import java.util.ArrayList;

import eu.petlack.android.bundleinjector.BundleInjector;
import eu.petlack.android.bundleinjector.InjectBundle;

import static junit.framework.Assert.*;

/**
 * Created by petlack on 4/19/14.
 */
@Config(manifest=Config.NONE)
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
        public boolean injectedVarBoolean = false;
        @InjectBundle
        public boolean[] injectedVarBooleanArray = { false };
        @InjectBundle
        public byte injectedVarByte = 0;
        @InjectBundle
        public byte[] injectedVarByteArray = { 0 };
        @InjectBundle
        public char injectedVarChar = 'a';
        @InjectBundle
        public char[] injectedVarCharArray = { 'a' };
        @InjectBundle
        public double injectedVarDouble = 0d;
        @InjectBundle
        public double[] injectedVarDoubleArray = { 0d };
        @InjectBundle
        public float injectedVarFloat = 0f;
        @InjectBundle
        public float[] injectedVarFloatArray = { 0f };
        @InjectBundle
        public int injectedVarInt = 0;
        @InjectBundle
        public int[] injectedVarIntArray = { 0 };
        @InjectBundle
        public long injectedVarLong = 0;
        @InjectBundle
        public long[] injectedVarLongArray = { 0 };
        @InjectBundle
        public short injectedVarShort = 0;
        @InjectBundle
        public short[] injectedVarShortArray = { 0 };

    }

    class WrongMember extends Member {

        @InjectBundle
        public String injectedVarString;
        @InjectBundle
        public Integer injectedVarInteger;

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

    @Test
    public void testInjectCharArray() {
        Bundle bundle = new Bundle();
        char[] expected = { 'X', 'Y' };
        bundle.putCharArray("injectedVarCharArray", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarCharArray);
    }

    @Test
    public void testInjectCharSequence() {
        Bundle bundle = new Bundle();
        CharSequence expected = "abcd";
        bundle.putCharSequence("injectedVar", expected);
        PublicMember<CharSequence> test = new PublicMember<CharSequence>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectCharSequenceArray() {
        Bundle bundle = new Bundle();
        CharSequence[] expected = { "abcd", "efgh" };
        bundle.putCharSequenceArray("injectedVar", expected);
        PublicMember<CharSequence[]> test = new PublicMember<CharSequence[]>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectCharSequenceArrayList() {
        Bundle bundle = new Bundle();
        ArrayList<CharSequence> expected = new ArrayList<CharSequence>();
        expected.add("abcd");
        expected.add("efgh");
        bundle.putCharSequenceArrayList("injectedVar", expected);
        PublicMember<ArrayList<CharSequence>> test = new PublicMember<ArrayList<CharSequence>>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
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
    public void testInjectDoubleArray() {
        Bundle bundle = new Bundle();
        double[] expected = { 2.5d, 3.14d };
        bundle.putDoubleArray("injectedVarDoubleArray", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarDoubleArray);
    }

    @Test
    public void testInjectFloat() {
        Bundle bundle = new Bundle();
        float expected = 2.5f;
        bundle.putFloat("injectedVarFloat", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarFloat);
    }

    @Test
    public void testInjectFloatArray() {
        Bundle bundle = new Bundle();
        float[] expected = { 2.5f, 3.14f };
        bundle.putFloatArray("injectedVarFloatArray", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarFloatArray);
    }

    @Test
    public void testInjectInt() {
        Bundle bundle = new Bundle();
        int expected = 12345;
        bundle.putInt("injectedVarInt", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarInt);
    }

    @Test
    public void testInjectIntArray() {
        Bundle bundle = new Bundle();
        int[] expected = { 31, 41 };
        bundle.putIntArray("injectedVarIntArray", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarIntArray);
    }

    @Test
    public void testInjectIntegerArrayList() {
        Bundle bundle = new Bundle();
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(31);
        expected.add(41);
        bundle.putIntegerArrayList("injectedVar", expected);
        PublicMember<ArrayList<Integer>> test = new PublicMember<ArrayList<Integer>>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectLong() {
        Bundle bundle = new Bundle();
        long expected = 12345;
        bundle.putLong("injectedVarLong", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarLong);
    }

    @Test
    public void testInjectLongArray() {
        Bundle bundle = new Bundle();
        long[] expected = { 31, 41 };
        bundle.putLongArray("injectedVarLongArray", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarLongArray);
    }

    @Test
    public void testInjectParcelable() {
        Bundle bundle = new Bundle();
        Parcelable expected = new Bundle();
        ((Bundle) expected).putString("test key", "test value");
        bundle.putParcelable("injectedVar", expected);
        PublicMember<Parcelable> test = new PublicMember<Parcelable>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectParcelableArray() {
        Bundle bundle = new Bundle();
        Parcelable expected1 = new Bundle();
        ((Bundle) expected1).putString("test key 1", "test value 1");
        Parcelable expected2 = new Bundle();
        ((Bundle) expected2).putString("test key 2", "test value 2");
        Parcelable[] expected = { expected1, expected2 };
        bundle.putParcelableArray("injectedVar", expected);
        PublicMember<Parcelable> test = new PublicMember<Parcelable>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectParcelableArrayList() {
        Bundle bundle = new Bundle();
        Parcelable expected1 = new Bundle();
        ((Bundle) expected1).putString("test key 1", "test value 1");
        Parcelable expected2 = new Bundle();
        ((Bundle) expected2).putString("test key 2", "test value 2");
        ArrayList<Parcelable> expected = new ArrayList<Parcelable>();
        expected.add(expected1);
        expected.add(expected2);
        bundle.putParcelableArrayList("injectedVar", expected);
        PublicMember<ArrayList<Parcelable>> test = new PublicMember<ArrayList<Parcelable>>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectSerializable() {
        Bundle bundle = new Bundle();
        Serializable expected = "abcd";
        bundle.putSerializable("injectedVar", expected);
        PublicMember<Serializable> test = new PublicMember<Serializable>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectShort() {
        Bundle bundle = new Bundle();
        short expected = 3141;
        bundle.putShort("injectedVarShort", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarShort);
    }

    @Test
    public void testInjectShortArray() {
        Bundle bundle = new Bundle();
        short[] expected = { 31, 41 };
        bundle.putShortArray("injectedVarShortArray", expected);
        PrimitiveMember test = new PrimitiveMember();
        test.inject(bundle);
        assertEquals(expected, test.injectedVarShortArray);
    }

    @Test
    public void testInjectSparseParcelableArray() {
        Bundle bundle = new Bundle();
        Parcelable expected1 = new Bundle();
        ((Bundle) expected1).putString("test key 1", "test value 1");
        Parcelable expected2 = new Bundle();
        ((Bundle) expected2).putString("test key 2", "test value 2");
        SparseArray<Parcelable> expected = new SparseArray<Parcelable>();
        expected.append(1, expected1);
        expected.append(2, expected2);
        bundle.putSparseParcelableArray("injectedVar", expected);
        PublicMember<SparseArray<Parcelable>> test = new PublicMember<SparseArray<Parcelable>>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectStringArray() {
        Bundle bundle = new Bundle();
        String[] expected = { "expected value 1", "expected value 2" };
        bundle.putStringArray("injectedVar", expected);
        PublicMember<String[]> test = new PublicMember<String[]>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testInjectStringArrayList() {
        Bundle bundle = new Bundle();
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("a");
        expected.add("b");
        bundle.putStringArrayList("injectedVar", expected);
        PublicMember<ArrayList<String>> test = new PublicMember<ArrayList<String>>();
        test.inject(bundle);
        assertEquals(expected, test.injectedVar);
    }

    @Test
    public void testWrongType() {
        Bundle bundle = new Bundle();
        bundle.putString("injectedVarInteger", "expected value");
        WrongMember test = new WrongMember();
        test.inject(bundle);
        assertNull(test.injectedVarInteger);
    }

    @Test
    public void testWhenNotPresent() {
        Bundle bundle = new Bundle();
        bundle.putString("injectedVarNonExisting", "expected value");
        PublicMember<String> test = new PublicMember<String>();
        test.inject(bundle);
        assertNull(test.injectedVar);
    }

}
