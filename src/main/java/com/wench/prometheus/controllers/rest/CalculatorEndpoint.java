package com.wench.prometheus.controllers.rest;

import com.wench.prometheus.calculator.AST;
import com.wench.prometheus.calculator.Calculator;
import com.wench.prometheus.calculator.InvalidExpressionException;
import com.wench.prometheus.data.expression.Expression;
import com.wench.prometheus.data.expression.ExpressionRepository;
import com.wench.prometheus.data.user.User;
import com.wench.prometheus.lang.interpreter.ExpressionInterpreter;
import com.wench.prometheus.lang.parser.ASTNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;

@RestController
@ResponseBody
public class CalculatorEndpoint {

    private final ExpressionRepository expressionRepository;
    private final Calculator calculator;

    public static final UnaryOperator<String> removeWhiteSpace =
            str -> str.codePoints()
                    .boxed()
                    .filter(code -> !Character.isWhitespace((char)code.intValue()))
                    .collect(Collector.of(
                            () -> new String[] {""},
                            (result, item) -> result[0] += (char) item.intValue(),
                            (r1, r2) ->  {
                                r1[0] += r2[0];
                                return r1; },
                            r -> r[0]));

    public static final UnaryOperator<String> tryToInt = num -> {
        int indexOfDecimal = num.indexOf(".");
        if(indexOfDecimal > -1) {
            try{
                if (Integer.parseInt(num.substring(indexOfDecimal+1)) == 0) return num.substring(0, indexOfDecimal);
            } catch (NumberFormatException ex) { }
        }
        return num;
    };

    @Autowired
    public CalculatorEndpoint(ExpressionRepository expressionRepository, Calculator calculator) {
        this.expressionRepository = expressionRepository;
        this.calculator = calculator;
    }

    @PostMapping(path="/calculator/solve", produces="application/json")
    public String solve(@RequestHeader String expression_input, HttpServletRequest request) {
        expression_input = expression_input.replaceAll("plusSign", "+").transform(removeWhiteSpace);
        User loggedInUser = (User)request.getSession().getAttribute("loggedInUser");
        Expression expression = new Expression(calculator, expression_input, loggedInUser == null? null: loggedInUser.getUserName());
        expressionRepository.save(expression);
        return "{ \"val\": \"" + expression.solution().transform(tryToInt) + "\"}";
    }

    @PostMapping(path="/calculator/v2/solve", produces="application/json")
    public String solve_v2(@RequestHeader String expression_input, HttpServletRequest request) {
        expression_input = expression_input.replaceAll("plusSign", "+");
        ExpressionInterpreter exp = new ExpressionInterpreter();
        ASTNode res = exp.interpret(expression_input);
        return "{ \"val\": \"" + res.value() + "\"}";
    }
}
