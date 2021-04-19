package com.wench.prometheus.calculator;

import org.springframework.stereotype.Component;

@Component
public class ArithmeticUnit {

    public ArithmeticUnit() {}

    public double add(double addend1, double addend2) {
        return addend1 + addend2;
    }

    public double subtract(double minuend, double subtrahend) {
        return minuend - subtrahend;
    }

    public double multiply(double multiplicand, double multiplier) {
        return multiplicand * multiplier;
    }

    public double divide(double dividend, double divisor) {
        return dividend/divisor;
    }

    public double exponentiate(double base, double exponent) {
        return Math.pow(base, exponent);
    }
}
