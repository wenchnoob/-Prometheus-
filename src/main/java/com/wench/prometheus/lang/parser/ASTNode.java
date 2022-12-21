package com.wench.prometheus.lang.parser;

import com.wench.prometheus.lang.lexer.Token;

import java.util.List;

public record ASTNode(Type type, Object value, List<ASTNode> children) {

    public void addChild(ASTNode child) {
        children.add(child);
    }

    public enum Type {
        // Math
        NUMBER, MINUS, PLUS, STAR, SLASH,

        // Comparisons
        BANG, BANG_EQUAL,
        EQUAL, EQUAL_EQUAL,
        GREATER, GREATER_EQUAL,
        LESS, LESS_EQUAL,

        // Other
        ERROR, LIST, IDENTIFIER, STRING
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        stringify(0, sb);
        return sb.toString();
    }

    private void stringify(int depth, StringBuilder sb) {
       StringBuilder tabs = new StringBuilder();
       for (int i = 0; i < depth; i++) tabs.append('\t');

       sb.append(tabs);
       sb.append("Type: %s -- Value: %s\n".formatted(type, value));
       for (ASTNode child: children) {
           child.stringify(depth + 1, sb);
       }
    }
}
