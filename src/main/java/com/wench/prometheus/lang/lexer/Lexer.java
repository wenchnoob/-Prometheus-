package com.wench.prometheus.lang.lexer;

import java.util.LinkedList;
import java.util.Queue;
public class Lexer {

    private String src;

    private int start, current, line, col;

    public Lexer(String src) {
        this.src = src;
    }

    public Queue<Token> tokens() {
        Queue<Token> queue = new LinkedList<>();

        while(!isAtEnd()) {
            queue.offer(nextToken());
        }

        return queue;
    }

    public Token nextToken() {
        if (isAtEnd()) return new Token(Token.Type.EOF, "", null, line, col);

        char start = advance();
        return switch (start) {
            case '(' -> new Token(Token.Type.LEFT_PAREN, getLexeme(), null, line, col);
            case ')' -> new Token(Token.Type.RIGHT_PAREN, getLexeme(), null, line, col);
            case '{' -> new Token(Token.Type.LEFT_BRACE, getLexeme(), null, line, col);
            case '}' -> new Token(Token.Type.RIGHT_BRACE, getLexeme(), null, line, col);
            case '+' -> new Token(Token.Type.PLUS, getLexeme(), null, line, col);
            case '-' -> new Token(Token.Type.MINUS, getLexeme(), null, line, col);
            case '/' -> new Token(Token.Type.SLASH, getLexeme(), null, line, col);
            case '*' -> new Token(Token.Type.STAR, getLexeme(), null, line, col);
            case '!' ->
                    match('=') ?
                            new Token(Token.Type.BANG_EQUAL, getLexeme(), null, line, col)
                            : new Token(Token.Type.BANG, getLexeme(), null, line, col);
            case '=' ->
                    match('=') ?
                            new Token(Token.Type.EQUAL_EQUAL, getLexeme(), null, line, col)
                            : new Token(Token.Type.EQUAL, getLexeme(), null, line, col);
            case '>' ->
                    match('=') ?
                            new Token(Token.Type.GREATER_EQUAL, getLexeme(), null, line, col)
                            : new Token(Token.Type.GREATER, getLexeme(), null, line, col);
            case '<' ->
                    match('=') ?
                            new Token(Token.Type.LESS_EQUAL, getLexeme(), null, line, col)
                            : new Token(Token.Type.LESS, getLexeme(), null, line, col);
            case '.' -> new Token(Token.Type.DOT, getLexeme(), null, line, col);
            case ',' -> new Token(Token.Type.COMMA, getLexeme(), null, line, col);
            case ';' -> new Token(Token.Type.SEMICOLON, getLexeme(), null, line, col);
            case ' ', '\r','\t' -> nextToken();
            case '\n' -> {
                line++;
                col = 0;
                yield nextToken();
            }
            default -> {
                if (Character.isDigit(start)) {
                    yield  number();
                }
                System.out.println("Failure");
                yield null;
            }
        };
    }

    private Token number() {
        while (Character.isDigit(peek())) advance();

        if(peek() == '.' && Character.isDigit(peekNext())) {
            advance();
            while (Character.isDigit(peek())) advance();
        }

        String lex = getLexeme();
        return new Token(Token.Type.NUMBER, lex, Double.parseDouble(lex), line, col);
    }


    private String getLexeme() {
        String lex = src.substring(start, current);
        start = current;
        return lex;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return src.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= src.length()) return '\0';
        return src.charAt(current + 1);
    }

    private char advance() {
        col++;
        return src.charAt(current++);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (src.charAt(current) != expected) return false;
        col++;
        current++;
        return true;
    }


    private boolean isAtEnd() {
        return current == src.length();
    }

}
