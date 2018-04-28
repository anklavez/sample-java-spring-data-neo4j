package com.evgeniykruglov.neo4j.sample.util;


import java.util.Map;

/**
 * @author Evgeniy Kruglov
 */
public class SimpleCypherQueryBuilder {

    private static final String WHERE_OPERATOR = " WHERE ";

    private static final String AND_OPERATOR = " AND ";


    /**
     * Construct Cypher simple query with parameters and condition WHERE
     * @param query initial query
     * @param parameters for injection
     * @param returnQuery
     * @return
     */
    public static String buildQuery(String query, Map<String, String> parameters, String returnQuery) {
        StringBuilder builder = new StringBuilder(query);
        if (!parameters.isEmpty()) {
            builder.append(WHERE_OPERATOR);
            boolean firstIteration = true;
            for (String key : parameters.keySet()) {
                if (!firstIteration) {
                    builder.append(AND_OPERATOR);
                }
                builder.append(String.format(key, parameters.get(key)));
                firstIteration = false;
            }
        }
        builder.append(returnQuery);
        return builder.toString();
    }
}
