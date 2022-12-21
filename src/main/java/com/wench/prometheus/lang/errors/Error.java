package com.wench.prometheus.lang.errors;

import com.wench.prometheus.lang.lexer.Token;

public record Error(Type type, String msg, int line, int col) {
    public enum Type {
        LEXICAL,
        SYNTAX
    }

    public static Error unexpectedEOF() {
        return new Error(Type.SYNTAX, "Unexpected end of input", -1, -1);
    }

    public static Error illegalToken(Token token, String exp) {
        return new Error(Type.SYNTAX, "Unexpected token %s, expected %s".formatted(token.lexeme(), exp), token.line(), token.col());
    }
}
