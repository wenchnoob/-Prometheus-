package com.wench.prometheus.calculator;

public class Operator implements Comparable<Operator> {

    private String value;
    private Precedence precedence;

    public Operator(String value, Precedence precedence) {
        this.value = value;
        this.precedence = precedence;
    }

    public Operator(String value) {
        this(value, Precedence.NONE);
    }

    public String value() {
        return value;
    }

    public Precedence precedence() {
        return precedence;
    }

    public int compareTo(Operator o) {
        return precedence.compareTo(o.precedence());
    }

    enum Precedence {
        ZERO, FIRST, SECOND, THIRD, FOURTH, NONE;
    }
}