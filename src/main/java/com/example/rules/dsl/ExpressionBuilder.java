package com.example.rules.dsl;

import java.util.function.Predicate;

public class ExpressionBuilder<T> {
    private Predicate<T> predicate;

    public ExpressionBuilder() {
    }

    public ExpressionBuilder<T> withPredicate(Predicate<T> predicate) {
        this.predicate = predicate;
        return this;
    }

    public ExpressionBuilder<T> withExpression(Expression<T> expression) {
        this.predicate = expression != null
                                && expression.getPredicate() != null
                            ? expression.getPredicate()
                            : this.predicate;
        return this;
    }

    public ExpressionBuilder<T> withExpressionBuilder(ExpressionBuilder<T> expressionBuilder) {
        this.predicate = expressionBuilder != null
                                && expressionBuilder.getPredicate() != null
                            ? expressionBuilder.getPredicate()
                            : this.predicate;
        return this;
    }

    public Expression<T> build() {
        return new Expression<T>(predicate);
    }

    public ExpressionBuilder<T> or(Predicate<T> predicate) {
        this.predicate = this.predicate.or(predicate);
        return this;
    }

    public ExpressionBuilder<T> and(Predicate<T> predicate) {
        this.predicate = this.predicate.and(predicate);
        return this;
    }

    public ExpressionBuilder<T> not() {
        this.predicate = this.predicate.negate();
        return this;
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    public static Predicate not(Predicate predicate) {
        return predicate.negate();
    }

}
