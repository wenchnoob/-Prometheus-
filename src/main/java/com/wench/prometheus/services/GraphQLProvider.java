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
import org.springframework.context.ApplicationContext;
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
    //@Value("classpath:resources/static/graphql/schema.graphqls")
    //private Resource resource;

    @Value("classpath*:.")
    private Resource resource;

    @Bean
    public GraphQL graphQL() {
        return this.graphQL;
    }

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    private void loadSchema() throws IOException {
        System.out.println(applicationContext.getResource("").getFile().toString());
        //File schemaFile = resource.getFile();
        System.out.println(new File("./build/resources/").getAbsolutePath());
        System.out.println(resource.getFile().getAbsolutePath());
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse("schema {\n" +
                "    query: Query\n" +
                "}\n" +
                "\n" +
                "type Query {\n" +
                "    calculationsByUsername(username: String): [Expression]\n" +
                "}\n" +
                "\n" +
                "type Expression {\n" +
                "    id: ID!\n" +
                "    val: String\n" +
                "    solution: String\n" +
                "}");
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
