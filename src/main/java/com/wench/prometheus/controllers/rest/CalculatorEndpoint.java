package com.wench.prometheus.controllers.rest;

import com.wench.prometheus.data.expression.Expression;
import com.wench.prometheus.data.expression.ExpressionRepository;
import com.wench.prometheus.data.user.User;
import com.wench.prometheus.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;

@RestController
@ResponseBody
public class CalculatorEndpoint {

    private ExpressionRepository expressionRepository;

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
    public CalculatorEndpoint(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }

    @GetMapping(path="/calculator/solve", produces="application/json")
    public String solve(@RequestParam String expression_input, HttpServletRequest request) {
        expression_input = expression_input.replaceAll("plusSign", "+").transform(removeWhiteSpace);
        User loggedInUser = (User)request.getSession().getAttribute("loggedInUser");
        Expression expression;
        if (loggedInUser == null || loggedInUser.getClass() != User.class)
            expression = new Expression(expression_input);
        else expression = new Expression(expression_input, loggedInUser.getUserName());
        expressionRepository.save(expression);
        return "{ \"val\": \"" + expression.solution().transform(tryToInt) + "\"}";
    }
}
