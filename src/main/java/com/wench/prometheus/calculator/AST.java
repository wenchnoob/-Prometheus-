package com.wench.prometheus.calculator;

public class AST {
    ASTNode root;
    String expression;

    public AST(String expression) throws InvalidExpressionException {
        this.expression = expression;
        init();
    }

    public ASTNode getRoot() {
        if (expression.trim().equals("")) root = new ASTNode(new Token(Double.NaN, Token.Type.NUMBER));
        return root;
    }

    private void init() throws InvalidExpressionException {
        Lexer lex = new Lexer(expression);
        while(lex.hasMoreTokens()) {
            insert(lex.nextToken());
        }
    }

    private void insert(Token token) throws InvalidExpressionException {

        if (token.type() == Token.Type.EXPRESSION) {
            ASTNode head = new ASTNode(new Token<String>("()", Token.Type.OPERATOR));
            String value = (String)token.value();
            AST temp = new AST(value.substring(1, value.length()-1));
            head.setRight(temp.getRoot());
            if (root == null) {
                this.root = head;
            } else {
                insertTo(root, head);
            }
        } else {
            if (root == null) {
                root = new ASTNode(token);
            } else {
                insertTo(root, new ASTNode(token));
            }
        }
    }

    private void insertTo(ASTNode insertionPoint, ASTNode toInsert) throws InvalidExpressionException {
        if (Calculator.checkPrecedence(insertionPoint.value()).compareTo(Calculator.checkPrecedence(toInsert.value())) >= 0) {
            if (toInsert.left() == null) {
                toInsert.setLeft(insertionPoint);

                ASTNode oldParent = insertionPoint.parent();
                if (oldParent != null) {
                    if (oldParent.right() == insertionPoint) {
                        oldParent.setRight(toInsert);
                    } else {
                        oldParent.setLeft(toInsert);
                    }
                } else {
                    this.root = toInsert;
                    insertionPoint.setParent(toInsert);
                }
            } else if (toInsert.right() == null) {
                toInsert.setRight(insertionPoint);

                ASTNode oldParent = insertionPoint.parent();
                if (oldParent != null) {
                    if (oldParent.right() == insertionPoint) {
                        oldParent.setRight(toInsert);
                    } else {
                        oldParent.setLeft(toInsert);
                    }
                } else {
                    this.root = toInsert;
                    insertionPoint.setParent(toInsert);
                }
            } else {
                insertTo(insertionPoint.right(), toInsert);
            }
        } else {
            if (insertionPoint.left() == null) {
                insertionPoint.setLeft(toInsert);
                toInsert.setParent(insertionPoint);
            } else if (insertionPoint.right() == null) {
                insertionPoint.setRight(toInsert);
                toInsert.setParent(insertionPoint);
            } else {
                insertTo(insertionPoint.right(), toInsert);
            }
        }
    }

    public void  print() {
        printFrom(root);
        System.out.println();
    }

    private void printFrom(ASTNode from) {
        if (from == null) return;
        if(String.valueOf(from.value().value()).equals("()")) System.out.print("(");
        printFrom(from.left());
        if(!String.valueOf(from.value().value()).equals("()")) System.out.print(from);
        printFrom(from.right());
        if(String.valueOf(from.value().value()).equals("()")) System.out.print(")");
    }
}
