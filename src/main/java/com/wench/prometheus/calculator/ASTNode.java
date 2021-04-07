package com.wench.prometheus.calculator;

public class ASTNode {

    Token value;
    ASTNode parent;
    ASTNode left;
    ASTNode right;

    public ASTNode(Token value) {
        this.value = value;
    }

    public Token value() {
        return value;
    }

    public ASTNode left() {
        return left;
    }

    public ASTNode right() {
        return right;
    }

    public ASTNode parent() {
        return parent;
    }

    public void setValue(Token value) {
        this.value = value;
    }

    public void setLeft(ASTNode left) {
        this.left = left;
    }

    public void setRight(ASTNode right) {
        this.right = right;
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

    public String toString() {
        return String.valueOf(value.value());
    }
}
