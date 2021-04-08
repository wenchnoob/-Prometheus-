package com.wench.prometheus.data.expression;


import com.wench.prometheus.calculator.AST;
import com.wench.prometheus.calculator.Calculator;
import com.wench.prometheus.calculator.InvalidExpressionException;
import com.wench.prometheus.data.user.User;
import org.hibernate.annotations.GenericGenerator;

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
    private final String solution;
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
    public Expression(String val) {
        this.id = 0;
        String solution1;
        this.val = val;
        try {
            solution1 = String.valueOf(new Calculator().solve(new AST(val)));
        } catch (InvalidExpressionException ex) {
            solution1 = "Invalid expression!";
        }
        this.solution = solution1;
        this.username = null;
    }

    public Expression(String val, String username) {
        this.id = 0;
        String solution1;
        this.val = val;
        try {
            solution1 = String.valueOf(new Calculator().solve(new AST(val)));
        } catch (InvalidExpressionException ex) {
            solution1 = "Invalid expression!";
        }
        this.solution = solution1;
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
