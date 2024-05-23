package model;

import java.util.Collection;

public abstract class Validator<T> {
    public abstract Notification validate(T type);

    public static Boolean nullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static Boolean nullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}
