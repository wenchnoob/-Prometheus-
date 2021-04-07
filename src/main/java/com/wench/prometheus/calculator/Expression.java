package com.wench.prometheus.calculator;


/**
 * Represents a expression.
 *
 * @Author Wenchy Dutreuil
 * @Version 1
 * @Since 2/2/2021
 * */
public class Expression {
    private final String val;
    private final String solution;

    /**
     * Only constructor for obtaining an expression object.
     * Stores the value of the expression, and solves and stores the solution to the expression.
     *
     * @author Wenchy Dutreuil
     * @version 1
     * @since 2/2/2021
     * */
    public Expression(String val) {
        String solution1;
        this.val = val;
        try {
            solution1 = String.valueOf(new Calculator().solve(new AST(val)));
        } catch (InvalidExpressionException ex) {
            solution1 = "Invalid expression!";
        }
        solution = solution1;
    }

    /**
     * Simple getter method.
     * @return Returns the solution to this expression.
     * */
    public String solution() {
        return solution;
    }

    /**
     * Simple getter method.
     * @return Returns the string representation of this expression.
     * */
    public String val() {
        return val;
    }
}
