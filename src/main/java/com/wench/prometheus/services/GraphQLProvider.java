package com.wench.prometheus.services;

import com.wench.prometheus.Prometheus;
import com.wench.prometheus.data.fetchers.CalculationsByUsernameDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    private CalculationsByUsernameDataFetcher calculationsByUsernameDataFetcher;

    @Autowired
    public GraphQLProvider(CalculationsByUsernameDataFetcher calculationsByUsernameDataFetcher) {
        this.calculationsByUsernameDataFetcher = calculationsByUsernameDataFetcher;
    }


    // Change classpath to classpath*: when deploying to heroku
    //@Value("classpath:schema.graphqls")
    //private Resource resource;

    @Bean
    public GraphQL graphQL() {
        return this.graphQL;
    }


    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    private void loadSchema() throws IOException {
        File schemaFile = resourceLoader.getResource("classpath*:static/graphql/schema.graphqls").getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring ->
                    typeWiring
                            .dataFetcher("calculationsByUsername", calculationsByUsernameDataFetcher)
                )
                .build();
    }


}
