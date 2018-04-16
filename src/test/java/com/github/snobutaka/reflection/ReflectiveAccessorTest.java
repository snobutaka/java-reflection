package com.github.snobutaka.reflection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

import com.github.snobutaka.reflection.MyProperty.InnerProperty;

public class ReflectiveAccessorTest {

    @Test
    public void testGetField() throws Exception {
        Random random = new Random();
        int testIntValue = random.nextInt();
        long testLongValue = random.nextLong();
        boolean testBooleanValue = random.nextInt() % 2 == 0;
        String testStringValue = UUID.randomUUID().toString();

        MyProperty myProp = new MyProperty();
        myProp.setIntProp(testIntValue);
        myProp.setLongProp(testLongValue);
        myProp.setBooleanProp(testBooleanValue);
        myProp.setStringProp(testStringValue);

        ReflectiveAccessor<MyProperty> reflectiveProp = new ReflectiveAccessor<MyProperty>(myProp);
        assertThat(reflectiveProp.getField("intProp"), is(testIntValue));
        assertThat(reflectiveProp.getField("longProp"), is(testLongValue));
        assertThat(reflectiveProp.getField("booleanProp"), is(testBooleanValue));
        assertThat(reflectiveProp.getField("stringProp"), is(testStringValue));
    }

    @Test
    public void testGetFieldOfNestedObject() throws Exception {
        Random random = new Random();
        int testIntValue = random.nextInt();
        long testLongValue = random.nextLong();
        boolean testBooleanValue = random.nextInt() % 2 == 0;
        String testStringValue = UUID.randomUUID().toString();

        MyProperty myProp = new MyProperty();
        InnerProperty innerProp = myProp.getInnerProp();
        innerProp.setIntProp(testIntValue);
        innerProp.setLongProp(testLongValue);
        innerProp.setBooleanProp(testBooleanValue);
        innerProp.setStringProp(testStringValue);

        ReflectiveAccessor<MyProperty> reflectiveProp = new ReflectiveAccessor<MyProperty>(myProp);
        assertThat(reflectiveProp.getField("innerProp.intProp"), is(testIntValue));
        assertThat(reflectiveProp.getField("innerProp.longProp"), is(testLongValue));
        assertThat(reflectiveProp.getField("innerProp.booleanProp"), is(testBooleanValue));
        assertThat(reflectiveProp.getField("innerProp.stringProp"), is(testStringValue));
    }

    @Test
    public void testSetField() throws Exception {
        Random random = new Random();
        int testIntValue = new Random().nextInt();
        long testLongValue = random.nextLong();
        boolean testBooleanValue = random.nextInt() % 2 == 0;
        String testStringValue = UUID.randomUUID().toString();

        ReflectiveAccessor<MyProperty> reflectiveProp = new ReflectiveAccessor<MyProperty>(new MyProperty());
        reflectiveProp.setField("intProp", testIntValue);
        reflectiveProp.setField("longProp", testLongValue);
        reflectiveProp.setField("booleanProp", testBooleanValue);
        reflectiveProp.setField("stringProp", testStringValue);

        MyProperty myProp = reflectiveProp.getData();
        assertThat(myProp.getIntProp(), is(testIntValue));
        assertThat(myProp.getLongProp(), is(testLongValue));
        assertThat(myProp.isBooleanProp(), is(testBooleanValue));
        assertThat(myProp.getStringProp(), is(testStringValue));
    }

    @Test
    public void testSetFieldOfNestedObject() throws Exception {
        Random random = new Random();
        int testIntValue = new Random().nextInt();
        long testLongValue = random.nextLong();
        boolean testBooleanValue = random.nextInt() % 2 == 0;
        String testStringValue = UUID.randomUUID().toString();

        ReflectiveAccessor<MyProperty> reflectiveProp = new ReflectiveAccessor<MyProperty>(new MyProperty());
        reflectiveProp.setField("innerProp.intProp", testIntValue);
        reflectiveProp.setField("innerProp.longProp", testLongValue);
        reflectiveProp.setField("innerProp.booleanProp", testBooleanValue);
        reflectiveProp.setField("innerProp.stringProp", testStringValue);

        InnerProperty innerProp = reflectiveProp.getData().getInnerProp();
        assertThat(innerProp.getIntProp(), is(testIntValue));
        assertThat(innerProp.getLongProp(), is(testLongValue));
        assertThat(innerProp.isBooleanProp(), is(testBooleanValue));
        assertThat(innerProp.getStringProp(), is(testStringValue));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDataIsIllegal() {
        new ReflectiveAccessor<Object>((Object) null);
    }

    @Test
    public void testGetFieldsList() {
        try {
            ReflectiveAccessor.getFieldsList(null);
            fail("Exception is expected.");
        } catch (IllegalArgumentException expectedException) {}

        try {
            ReflectiveAccessor.getFieldsList("");
            fail("Exception is expected.");
        } catch (IllegalArgumentException expectedException) {}

        assertThat(ReflectiveAccessor.getFieldsList("a"), is(Arrays.asList("a")));
        assertThat(ReflectiveAccessor.getFieldsList("a.b"), is(Arrays.asList("a", "b")));
        assertThat(ReflectiveAccessor.getFieldsList("a.b.c"), is(Arrays.asList("a", "b", "c")));
    }

    @Test
    public void testTail() {
        try {
            assertThat(ReflectiveAccessor.tail(Collections.emptyList()), is(Collections.emptyList()));
            fail("Exception is expected.");
        } catch (IllegalArgumentException expectedException) {}

        assertThat(ReflectiveAccessor.tail(Arrays.asList(1)), is(Collections.emptyList()));
        assertThat(ReflectiveAccessor.tail(Arrays.asList(1, 2)), is(Arrays.asList(2)));
        assertThat(ReflectiveAccessor.tail(Arrays.asList(1, 2, 3)), is(Arrays.asList(2, 3)));
    }

    @Test
    public void testInit() {
        try {
            assertThat(ReflectiveAccessor.init(Collections.emptyList()), is(Collections.emptyList()));
            fail("Exception is expected.");
        } catch (IllegalArgumentException expectedException) {}

        assertThat(ReflectiveAccessor.init(Arrays.asList(1)), is(Collections.emptyList()));
        assertThat(ReflectiveAccessor.init(Arrays.asList(1, 2)), is(Arrays.asList(1)));
        assertThat(ReflectiveAccessor.init(Arrays.asList(1, 2, 3)), is(Arrays.asList(1, 2)));
    }

    @Test
    public void testCapitalizeFirstChar() {
        assertThat(ReflectiveAccessor.capitalizeFirstChar("test"), is("Test"));
        assertThat(ReflectiveAccessor.capitalizeFirstChar("testValue"), is("TestValue"));
    }

    @Test
    public void testComparePrimitiveAndItsWrapperClass() {
        assertThat(int.class, is(not(Integer.class)));
        assertThat(long.class, is(not(Long.class)));
        assertThat(boolean.class, is(not(Boolean.class)));
    }
}
