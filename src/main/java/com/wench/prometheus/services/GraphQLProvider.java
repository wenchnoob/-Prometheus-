package com.wench.prometheus.services;

import com.wench.prometheus.data.fetchers.CalculationsByUsernameDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    private CalculationsByUsernameDataFetcher calculationsByUsernameDataFetcher;

    @Autowired
    public GraphQLProvider(CalculationsByUsernameDataFetcher calculationsByUsernameDataFetcher) {
        this.calculationsByUsernameDataFetcher = calculationsByUsernameDataFetcher;
    }


    // Not working
    @Value("classpath:resource/static/graphql/schema.graphqls")
    private Resource resource;

    @Bean
    public GraphQL graphQL() {
        return this.graphQL;
    }

    @PostConstruct
    private void loadSchema() throws IOException {
        File schemaFile = resource.getFile();
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
