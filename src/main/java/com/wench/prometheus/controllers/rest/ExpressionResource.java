package com.wench.prometheus.controllers.rest;

import com.wench.prometheus.services.GraphQLProvider;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user/expressions")
@RestController
public class ExpressionResource {

    private GraphQLProvider graphQLProvider;

    @Autowired
    public ExpressionResource(GraphQLProvider graphQLProvider) {
        this.graphQLProvider = graphQLProvider;
    }

    @PostMapping
    public ResponseEntity<Object> getUserExpressions(@RequestBody String query) {
        ExecutionResult executionResult = graphQLProvider.graphQL().execute(query);
        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }

}
