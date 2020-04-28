package com.example.rules.dsl;

import java.util.function.Predicate;

public class Expression<T> {
    private Predicate<T> predicate;

    public Expression(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public Expression() {
        //default no-arg constructor
    }

    public boolean execute(T t) {
        return predicate != null && predicate.test(t);
    }


}
