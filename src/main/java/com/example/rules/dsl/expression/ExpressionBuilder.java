package com.example.rules.dsl.expression;

import java.util.function.Predicate;

public class ExpressionBuilder<T> {
    private static final String MUST_BE_USED_FOR_PRE_INITIALIZED_EXPRESSION = "must be used for pre-initialized expression only";
    private Predicate<T> predicate;

    public ExpressionBuilder() {
    }

    public ExpressionBuilder<T> withPredicate(Predicate<T> predicate) {
        this.predicate = predicate;
        return this;
    }

    public Expression<T> build() {
        return new Expression<T>(predicate);
    }

    public ExpressionBuilder<T> or(Predicate<T> predicate) {
        if(isNotInitialized()) {
            throw new UnsupportedOperationException("or " + MUST_BE_USED_FOR_PRE_INITIALIZED_EXPRESSION);
        }
        this.predicate = this.predicate.or(predicate);
        return this;
    }

    public ExpressionBuilder<T> ornot(Predicate<T> predicate) {
        if(isNotInitialized()) {
            throw new UnsupportedOperationException("ornot " + MUST_BE_USED_FOR_PRE_INITIALIZED_EXPRESSION);
        }
        this.predicate = this.predicate.or(predicate.negate());
        return this;
    }

    public ExpressionBuilder<T> and(Predicate<T> predicate) {
        if(isNotInitialized()) {
            throw new UnsupportedOperationException("and " + MUST_BE_USED_FOR_PRE_INITIALIZED_EXPRESSION);
        }
        this.predicate = this.predicate.and(predicate);
        return this;
    }

    public ExpressionBuilder<T> andnot(Predicate<T> predicate) {
        if(isNotInitialized()) {
            throw new UnsupportedOperationException("andnot " + MUST_BE_USED_FOR_PRE_INITIALIZED_EXPRESSION);
        }
        this.predicate = this.predicate.and(predicate.negate());
        return this;
    }

    public ExpressionBuilder<T> not(Predicate<T> predicate) {
        if(isInitialized()) {
            throw new UnsupportedOperationException("not with argument must not accompany predefined expression");
        }
        this.predicate = predicate.negate();
        return this;
    }

    private boolean isInitialized() {
        return this.predicate != null;
    }

    private boolean isNotInitialized() {
        return !isInitialized();
    }


}
