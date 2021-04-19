package com.wench.prometheus.calculator;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ASTNodeTest {

    Token tok = new Token("", Token.Type.EXPRESSION);
    public ASTNode home, parent, left, right;


    @BeforeEach
    void setUp() {
        home = new ASTNode(tok);
        parent = new ASTNode(tok);
        left = new ASTNode(tok);
        right = new ASTNode(tok);

        home.setParent(parent);
        home.setLeft(left);
        home.setRight(right);
    }

    @AfterEach
    void tearDown() {
        home = null;
        parent = null;
        left = null;
        right = null;
    }

    @Test
    void value() {
        // given

        // when
        Token token = home.value();

        // then
        assertEquals(token, tok);
    }

    @Test
    void left() {
    }

    @Test
    void right() {
    }

    @Test
    void parent() {
    }

    @Test
    void setValue() {
    }

    @Test
    void setLeft() {
    }

    @Test
    void setRight() {
    }

    @Test
    void setParent() {
    }

    @Test
    void testToString() {
    }
}