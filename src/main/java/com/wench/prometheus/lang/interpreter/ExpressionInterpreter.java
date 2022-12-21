package com.wench.prometheus.lang.interpreter;

import com.wench.prometheus.lang.parser.ASTNode;
import com.wench.prometheus.lang.parser.Parser;

import java.util.LinkedList;

import static com.wench.prometheus.lang.parser.ASTNode.Type.*;

public class ExpressionInterpreter {

    public ASTNode interpret(String src) {
        ASTNode node = new Parser(src).expression();
        return interpret(node);
    }

    public ASTNode interpret(ASTNode node) {
        return switch (node.type()) {
            case NUMBER -> node;
            case MINUS -> {
                ASTNode lhs = interpret(node.children().get(0));
                ASTNode rhs = interpret(node.children().get(1));
                Double res = (Double) lhs.value() - (Double) rhs.value();
                yield  new ASTNode(NUMBER, res, new LinkedList<>());
            }
            case PLUS -> {
                ASTNode lhs = interpret(node.children().get(0));
                ASTNode rhs = interpret(node.children().get(1));
                Double res = (Double) lhs.value() + (Double) rhs.value();
                yield  new ASTNode(NUMBER, res, new LinkedList<>());
            }
            case STAR -> {
                ASTNode lhs = interpret(node.children().get(0));
                ASTNode rhs = interpret(node.children().get(1));
                Double res = (Double) lhs.value() * (Double) rhs.value();
                yield  new ASTNode(NUMBER, res, new LinkedList<>());
            }
            case SLASH -> {
                ASTNode lhs = interpret(node.children().get(0));
                ASTNode rhs = interpret(node.children().get(1));
                Double res = (Double) lhs.value() / (Double) rhs.value();
                yield  new ASTNode(NUMBER, res, new LinkedList<>());
            }
            default -> null;
        };
    }
}
