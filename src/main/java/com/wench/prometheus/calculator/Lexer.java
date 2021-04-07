package com.wench.prometheus.calculator;

public class Lexer {
    private int position = 0;
    private final String expression;

    public Lexer(String expression) {
        this.expression = expression;
    }

    public Token nextToken() {
        if (Character.isDigit(expression.charAt(position))) {
            return getNumber();
        } else {
            if (expression.charAt(position) == '(') {
                return getSubExpression();
            } else {
                return getOperator();
            }
        }
    }

    private Token getNumber() {
        int initialPosition = position;
        StringBuilder value = new StringBuilder();

        while(position < expression.length() && Character.isDigit(expression.charAt(position))) {
            value.append(expression.charAt(position));
            position++;
        }

        return new Token<Double>(Double.parseDouble(value.toString()) , Token.Type.NUMBER);
    }

    private Token getOperator() {
        int initialPosition = position;

        StringBuilder value = new StringBuilder();
        while(position < expression.length() && !Character.isDigit(expression.charAt(position)) && expression.charAt(position) != '(') {
            value.append(expression.charAt(position));
            position++;
        }

        return new Token<String>(value.toString(), Token.Type.OPERATOR);
    }

    private Token getSubExpression() {
        int initialPosition = position;

        StringBuilder value = new StringBuilder();
        while(position < expression.length() && expression.charAt(position) != ')') {
            value.append(expression.charAt(position));
            position++;
        }
        try {
            value.append(expression.charAt(position++));
        } catch (IndexOutOfBoundsException ex) {};

        return new Token<String>(value.toString(), Token.Type.EXPRESSION);
    }

    public boolean hasMoreTokens() {
        if (position >= expression.length()) return false;
        return true;
    }
}
