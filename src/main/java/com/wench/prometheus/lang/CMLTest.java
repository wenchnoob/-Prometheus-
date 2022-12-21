package com.wench.prometheus.lang;

import com.wench.prometheus.lang.interpreter.ExpressionInterpreter;
import com.wench.prometheus.lang.lexer.Lexer;
import com.wench.prometheus.lang.parser.Parser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CMLTest implements CommandLineRunner {

    @Override
    public void run(String...args) throws IOException {
        InputStream is = new BufferedInputStream(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String val;
        while ((val = br.readLine()) != null) {
            ExpressionInterpreter interpreter = new ExpressionInterpreter();
            System.out.print(interpreter.interpret(val));
        }
    }
}
