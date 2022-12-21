package com.wench.prometheus.lang.parser;

import com.wench.prometheus.lang.lexer.Lexer;
import com.wench.prometheus.lang.lexer.Token;
import com.wench.prometheus.lang.errors.Error;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Parser {

    private Lexer lexer;
    private Queue<Token> tokens;

    public Parser(String src) {
        this.lexer = new Lexer(src);
        this.tokens = this.lexer.tokens();
    }


    public ASTNode expression() {
        if (tokens.isEmpty())
            return new ASTNode(ASTNode.Type.ERROR, Error.unexpectedEOF(), new LinkedList<>());

        return additiveExpression();
    }

    private ASTNode additiveExpression() {
        ASTNode lhs = multiplicativeExpression();
        if (tokens.isEmpty()) return lhs;

        Token op = tokens.peek();
        while (op.type() == Token.Type.PLUS || op.type() == Token.Type.MINUS) {
            op = tokens.poll();
            ASTNode rhs = multiplicativeExpression();
            lhs = new ASTNode(op.type() == Token.Type.PLUS ?
                    ASTNode.Type.PLUS
                    : ASTNode.Type.MINUS, op.lexeme(), mutableList(lhs, rhs));
            if (tokens.isEmpty()) break;
            op = tokens.peek();
        }

        return lhs;
    }

    private ASTNode multiplicativeExpression() {
        ASTNode lhs = literal();
        if (tokens.isEmpty()) return lhs;

        Token op = tokens.peek();
        while (op.type() == Token.Type.STAR || op.type() == Token.Type.SLASH) {
            op = tokens.poll();
            ASTNode rhs = literal();
            lhs = new ASTNode(op.type() == Token.Type.STAR ?
                    ASTNode.Type.STAR
                    : ASTNode.Type.SLASH, op.lexeme(), mutableList(lhs, rhs));
            if (tokens.isEmpty()) break;
            op = tokens.peek();
        }

        return lhs;
    }

    private ASTNode literal() {
        if (tokens.isEmpty())
            return new ASTNode(ASTNode.Type.ERROR, Error.unexpectedEOF(), new LinkedList<>());

        Token tok = tokens.peek();
        if (tok.type() != Token.Type.LEFT_PAREN) {
            return number();
        } else {
            return parenthetical();
        }
    }

    private ASTNode parenthetical() {
        if (tokens.isEmpty())
            return new ASTNode(ASTNode.Type.ERROR, Error.unexpectedEOF(), new LinkedList<>());

        Token cur = tokens.poll();
        if (cur.type() != Token.Type.LEFT_PAREN)
            return new ASTNode(ASTNode.Type.ERROR, Error.illegalToken(cur, "("), new LinkedList<>());

        ASTNode exp = expression();

        cur = tokens.poll();
        if (cur.type() != Token.Type.RIGHT_PAREN)
            return new ASTNode(ASTNode.Type.ERROR, Error.illegalToken(cur, ")"), new LinkedList<>());

        return exp;
    }

    private ASTNode number() {
        if (tokens.isEmpty())
            return new ASTNode(ASTNode.Type.ERROR, Error.unexpectedEOF(), new LinkedList<>());

        Token cur = tokens.poll();
        if (cur.type() != Token.Type.NUMBER) {
            return new ASTNode(ASTNode.Type.ERROR, Error.illegalToken(cur, "number"), new LinkedList<>());
        }

        return new ASTNode(ASTNode.Type.NUMBER, cur.value(), new LinkedList<>());
    }

    private List<ASTNode> mutableList(ASTNode...elems) {
        List<ASTNode> ls = new LinkedList<>();
        Collections.addAll(ls, elems);
        return ls;
    }
}
