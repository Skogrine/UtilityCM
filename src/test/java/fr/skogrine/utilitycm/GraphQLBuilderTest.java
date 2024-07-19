package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphQLBuilderTest {

    @Test
    void testGraphQLBuilder() {
        GraphQLBuilder builder = new GraphQLBuilder();
        builder.addField("user")
                .addArgument("id", "123")
                .addSubField("name")
                .addSubField("email");

        String query = builder.build();

        assertNotNull(query);
    }
}

