package com.github.snobutaka.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;

public class ReflectiveAccessor<V> {

    private final V data;

    public V getData() {
        return this.data;
    }

    public ReflectiveAccessor(V data) {
        if (data == null) throw new IllegalArgumentException("Given data is null.");
        this.data = data;
    }

    public <T> T getField(String key) throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

        return getFieldRecursive(this.getData(), getFieldsList(key));
    }

    @SuppressWarnings("unchecked")
    <T, U> T getFieldRecursive(U obj, List<String> fields) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {

        if (fields.isEmpty()) throw new IllegalArgumentException("Given fields is empty.");

        Method getter = obj.getClass().getMethod(getterName(fields.get(0)));
        Object value = getter.invoke(obj);

        if (fields.size() == 1) {
            return (T) value;
        } else {
            return getFieldRecursive(value, tail(fields));
        }
    }

    public <T> void setField(String key, T value) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

        List<String> fields = getFieldsList(key);
        Object valueOwner;
        String fieldName;
        if (fields.size() == 1) {
            valueOwner = this.getData();
            fieldName = fields.get(0);
        } else {
            valueOwner = getFieldRecursive(this.getData(), init(fields));
            fieldName = last(fields);
        }
        Class<?> paramType = decideClass(valueOwner, fieldName, value);
        Method setter = valueOwner.getClass().getMethod(setterName(fieldName), paramType);
        setter.invoke(valueOwner, value);
    }

    <T, U> Class<?> decideClass(U obj, String fieldName, T value) throws NoSuchFieldException, SecurityException {

        Class<?> argumentClass = value.getClass();
        Field field = obj.getClass().getDeclaredField(fieldName);

        if (field.getType().isPrimitive()) {
            return ClassUtils.wrapperToPrimitive(argumentClass);
        } else {
            return argumentClass;
        }
    }

    String getterName(String fieldName) throws NoSuchFieldException, SecurityException {
        Field field = this.getData().getClass().getDeclaredField(fieldName);
        Class<?> fieldType = field.getType();
        if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
            return "is" + capitalizeFirstChar(fieldName);
        } else {
            return "get" + capitalizeFirstChar(fieldName);
        }
    }

    String setterName(String fieldName) {
        return "set" + capitalizeFirstChar(fieldName);
    }

    static String capitalizeFirstChar(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    static List<String> getFieldsList(String key) {
        if (key == null || key.isEmpty()) throw new IllegalArgumentException("Given key is null or empty.");
        return Arrays.asList(key.split("\\."));
    }

    static <T> List<T> tail(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("Given list is empty.");
        return list.subList(1, list.size());
    }

    static <T> List<T> init(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("Given list is empty.");
        return list.subList(0, list.size() - 1);
    }

    static <T> T last(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("Given list is empty.");
        return list.get(list.size() - 1);
    }
}
