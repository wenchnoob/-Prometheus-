package com.wench.prometheus.data.fetchers;

import com.wench.prometheus.data.expression.Expression;
import com.wench.prometheus.data.expression.ExpressionRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CalculationsByUsernameDataFetcher implements DataFetcher<List<Expression>> {

    ExpressionRepository expressionRepository;

    @Autowired
    public CalculationsByUsernameDataFetcher(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }

    @Override
    public List<Expression> get(DataFetchingEnvironment environment) throws Exception {
        String username = environment.getArgument("username");
        return expressionRepository.getCalculationByUserName(username);
    }
}
