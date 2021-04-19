package com.wench.prometheus;

import com.wench.prometheus.calculator.ASTNodeTest;
import com.wench.prometheus.calculator.ArithmeticUnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = {ArithmeticUnitTest.class, ASTNodeTest.class})
// No longer needed: @ExtendWith(SpringExtension.class) - a part of @SpringBootTest
class ChattyApplicationTests {

    @Test
    void contextLoads() {
    }

}
