package com.evgeniykruglov.neo4j.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * @author Evgeniy Kruglov
 */
@SpringBootApplication
@EnableNeo4jRepositories("com.evgeniykruglov.neo4j.sample.repository")
public class Neo4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(Neo4jApplication.class, args);
	}
}
