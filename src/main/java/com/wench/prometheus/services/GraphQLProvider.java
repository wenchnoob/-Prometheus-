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

    @PostConstruct
    private void loadSchema() throws IOException {
        walk_file_system();
        //File schemaFile = resource.getFile();
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

    @Autowired
    private ApplicationContext applicationContext;

    private void walk_file_system() throws IOException {
        File start = applicationContext.getResource("classpath:.").getFile();
        print_dir(start);
    }

    private void print_dir(File file) {
         String[] children = file.list();
         if (children != null) Arrays.stream(children).forEach(child -> print_dir(new File(file.getAbsolutePath() + "/" + child)));
         System.out.println(file.getAbsolutePath());
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
