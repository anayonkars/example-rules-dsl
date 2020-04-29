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
        if(this.predicate == null) {
            throw new UnsupportedOperationException("or must be used for pre-initialized expression");
        }
        this.predicate = this.predicate.or(predicate);
        return this;
    }

    public ExpressionBuilder<T> ornot(Predicate<T> predicate) {
        if(this.predicate == null) {
            throw new UnsupportedOperationException("ornot must be used for pre-initialized expression");
        }
        this.predicate = this.predicate.or(predicate.negate());
        return this;
    }

    public ExpressionBuilder<T> and(Predicate<T> predicate) {
        if(this.predicate == null) {
            throw new UnsupportedOperationException("and must be used for pre-initialized expression");
        }
        this.predicate = this.predicate.and(predicate);
        return this;
    }

    public ExpressionBuilder<T> andnot(Predicate<T> predicate) {
        if(this.predicate == null) {
            throw new UnsupportedOperationException("andnot must be used for pre-initialized expression");
        }
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
