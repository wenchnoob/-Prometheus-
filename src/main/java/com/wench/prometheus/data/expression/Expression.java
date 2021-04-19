package com.wench.prometheus.data.expression;


import com.wench.prometheus.calculator.AST;
import com.wench.prometheus.calculator.Calculator;
import com.wench.prometheus.calculator.InvalidExpressionException;

import javax.persistence.*;

/**
 * Represents a expression.
 *
 * @Author Wenchy Dutreuil
 * @Version 1
 * @Since 2/2/2021
 * */
@Entity
@Table(name = "expressions")
public class Expression {

    @Id
    @GeneratedValue(
            generator = "increment"
    )
    private final long id;

    @Column (name = "expression")
    private final String val;
    private String solution;
    private final String username;

    public Expression() {
        this.id = 0;
        this.val = null;
        this.solution = null;
        this.username = null;
    }

    /**
     * Stores the value of the expression, and solves and stores the solution to the expression.
     *
     * @author Wenchy Dutreuil
     * @version 1
     * @since 2/2/2021
     * */
    public Expression(Calculator calculator, String val, String username) {
        this.id = 0;
        this.val = val;
        try {
            solution = String.valueOf(calculator.solve(new AST(val)));
        } catch (InvalidExpressionException ex) {
            solution = "Invalid expression!";
        }
        this.username = username;
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

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
