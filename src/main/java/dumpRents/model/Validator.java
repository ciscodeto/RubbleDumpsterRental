package com.model;

import com.model.entities.Rental;
import com.model.entities.RubbleDumpsterStatus;

import java.util.Collection;

public abstract class Validator<T> {
    public abstract Notification validate(T type);

    public static Boolean nullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static Boolean nullOrEmpty(double num) {
        return num == 0;
    }
    public static Boolean nullOrEmpty(RubbleDumpsterStatus status ) {return status == null;}

    public static Boolean nullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static Boolean nullOrEmpty(Rental rental) {return rental == null;}
}
