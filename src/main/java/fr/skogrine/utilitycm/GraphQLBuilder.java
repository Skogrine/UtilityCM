package fr.skogrine.utilitycm;

import java.util.HashMap;
import java.util.Map;

/**
 * GraphQLBuilder simplifies the creation and execution of GraphQL queries and mutations.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * GraphQLBuilder builder = new GraphQLBuilder();
 * builder.addField("user")
 *        .addArgument("id", "123")
 *        .addSubField("name")
 *        .addSubField("email");
 * String query = builder.build();
 * System.out.println(query); // Output: { user(id: "123") { name email } }
 * }</pre>
 */
public class GraphQLBuilder {

    private final StringBuilder query;
    private final Map<String, Object> arguments;

    /**
     * Constructs a GraphQLBuilder.
     */
    public GraphQLBuilder() {
        this.query = new StringBuilder();
        this.arguments = new HashMap<>();
    }

    /**
     * Adds a field to the GraphQL query.
     *
     * @param field the field name
     * @return this GraphQLBuilder
     */
    public GraphQLBuilder addField(String field) {
        if (query.length() > 0) {
            query.append(" ");
        }
        query.append(field);
        return this;
    }

    /**
     * Adds an argument to the last added field.
     *
     * @param name the argument name
     * @param value the argument value
     * @return this GraphQLBuilder
     */
    public GraphQLBuilder addArgument(String name, Object value) {
        arguments.put(name, value);
        return this;
    }

    /**
     * Adds a sub-field to the last added field.
     *
     * @param subField the sub-field name
     * @return this GraphQLBuilder
     */
    public GraphQLBuilder addSubField(String subField) {
        query.append("{ ").append(subField).append(" }");
        return this;
    }

    /**
     * Builds the GraphQL query string.
     *
     * @return the GraphQL query string
     */
    public String build() {
        StringBuilder builtQuery = new StringBuilder("{ ");
        builtQuery.append(query);
        if (!arguments.isEmpty()) {
            builtQuery.append("(");
            arguments.forEach((k, v) -> builtQuery.append(k).append(": \"").append(v).append("\", "));
            builtQuery.setLength(builtQuery.length() - 2); // Remove trailing comma and space
            builtQuery.append(")");
        }
        builtQuery.append(" }");
        return builtQuery.toString();
    }
}