package com.wench.prometheus.calculator;

public class Token<T> {

    private Type type;
    private T value;

    public Token(T value, Type type) {
        this.value = value;
        this.type = type;
    }

    public Type type() {
        return type;
    }

    public T value() {
        return value;
    }

    @Override
    public String toString() {
        if (type == Type.NUMBER) {
            return "Number: " + value;
        } else if (type == Type.OPERATOR) {
            return "Operator: " + value;
        } else {
            return "Expression: " + value;
        }
    }

    enum Type {
        NUMBER, OPERATOR, EXPRESSION;
    }

}
