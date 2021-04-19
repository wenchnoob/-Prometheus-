package com.wench.prometheus.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

public class ArithmeticUnitTest {

    private ArithmeticUnit underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ArithmeticUnit();
    }

    @Test
    void testAdd() {
        // given
        int first = 10;
        int second = 20;

        // when
        double sum = underTest.add(first, second);

        // then
        assertThat(sum).isEqualTo(30);
    }

    @Test
    void testSubtract() {
        // given
        int first = 10;
        int second = 20;

        // when
        double difference = underTest.subtract(first, second);

        // then
        assertThat(difference).isEqualTo(-10);
    }

    @Test
    void testMultiply() {
        // given
        int first = 10;
        int second = 20;

        // when
        double product = underTest.multiply(first, second);

        // then
        assertThat(product).isEqualTo(200);
    }

    @Test
    void testDivide() {
        // given
        int first = 10;
        int second = 20;

        // when
        double quotient = underTest.divide(first, second);

        // then
        assertThat(quotient).isEqualTo(.5);
    }

    @Test
    void testExponentiate() {
        // given
        int first = 2;
        int second = 3;

        // when
        double exp = underTest.exponentiate(first, second);

        // then
        assertThat(exp).isEqualTo(8);
    }
}