package com.wench.prometheus.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Calculator {

    private final static Set<Operator> supportedOperators = new HashSet<>();
    private final ArithmeticUnit au;

    @Autowired
    public Calculator(ArithmeticUnit au) {
        this.au = au;
    }

    static {
        supportedOperators.add(new Operator("()", Operator.Precedence.NONE));

        supportedOperators.add(new Operator("+", Operator.Precedence.FIRST));
        supportedOperators.add(new Operator("-", Operator.Precedence.FIRST));
        supportedOperators.add(new Operator("*", Operator.Precedence.SECOND));
        supportedOperators.add(new Operator("/", Operator.Precedence.SECOND));
        supportedOperators.add(new Operator("**", Operator.Precedence.THIRD));

        supportedOperators.add(new Operator("+-", Operator.Precedence.FIRST));
        supportedOperators.add(new Operator("--", Operator.Precedence.FIRST));
        supportedOperators.add(new Operator("*-", Operator.Precedence.SECOND));
        supportedOperators.add(new Operator("/-", Operator.Precedence.SECOND));
        supportedOperators.add(new Operator("**-", Operator.Precedence.THIRD));
    }

    public static Operator.Precedence checkPrecedence(Token token) throws InvalidExpressionException {
        if (token.value().getClass() == Double.class) return Operator.Precedence.NONE;
        for (Operator op: supportedOperators)
            if (op.value().equals(token.value())) return op.precedence();
        throw new InvalidExpressionException("There is an invalid operator in your expression!");
    }

    public static Operator getOperator(String opString) {
        for (Operator op: supportedOperators)
            if (op.value().equals(opString)) return op;
        return null;
    }

    public double solve(AST tree) {
        evalNode(tree.getRoot());
        return (Double)tree.getRoot().value().value();
    }

    public void evalNode (ASTNode node) {
        if (node == null || node.value().type() == Token.Type.NUMBER) return;


        evalNode(node.left());
        evalNode(node.right());

        if (node.value().value().equals("()")) {
            if (node.left() != null) {
                node.setValue(node.left().value());
                node.setRight(null);
                node.setLeft(null);
                return;
            } else if (node.right() != null) {
                node.setValue(node.right().value());
                node.setRight(null);
                node.setLeft(null);
                return;
            }
        } else {
            if (node.left() != null && node.right() != null) {
                Operator op = getOperator((String)(node.value().value()));
                System.out.println(node.left.value.value() + " " + op.value() + " " + node.right.value.value());
                Double ans = binaryEval(op, (Double)node.left().value().value(), (Double)node.right().value().value());
                node.setValue(new Token<>(ans, Token.Type.NUMBER));
            }

        }

    }

    private double binaryEval(Operator operator, Double oper1, Double oper2) {
        double ans = 0;
        switch(operator.value()) {
            case "+":
                ans = au.add(oper1, oper2);
                break;
            case "-":
                ans = au.subtract(oper1, oper2);
                break;
            case "*":
                ans = au.multiply(oper1, oper2);
                break;
            case "/":
                ans = au.divide(oper1, oper2);
                break;
            case "**":
                ans = au.exponentiate(oper1, oper2);
                break;

            case "+-":
                ans = au.add(oper1, -1*oper2);
                break;
            case "--":
                ans = au.subtract(oper1, -1*oper2);
                break;
            case "*-":
                ans = au.multiply(oper1, -1*oper2);
                break;
            case "/-":
                ans = au.divide(oper1, -1*oper2);
                break;
            case "**-":
                ans = au.exponentiate(oper1, -1*oper2);
                break;
        }
        return ans;
    }

    public double unaryEval(Operator operator, Double operand) {
        return operand;
    }
}
