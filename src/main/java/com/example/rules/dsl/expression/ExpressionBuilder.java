package com.example.rules.dsl.expression;

import java.util.function.Predicate;

public class ExpressionBuilder<T> {
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
        this.predicate = this.predicate.or(predicate);
        return this;
    }

    public ExpressionBuilder<T> ornot(Predicate<T> predicate) {
        this.predicate = this.predicate.or(predicate.negate());
        return this;
    }

    public ExpressionBuilder<T> and(Predicate<T> predicate) {
        this.predicate = this.predicate.and(predicate);
        return this;
    }

    public ExpressionBuilder<T> andnot(Predicate<T> predicate) {
        this.predicate = this.predicate.and(predicate.negate());
        return this;
    }

    public ExpressionBuilder<T> not(Predicate<T> predicate) {
        if(this.predicate != null) {
            throw new UnsupportedOperationException("not with argument must not accompany predefined expression");
        }
        this.predicate = predicate.negate();
        return this;
    }


}
