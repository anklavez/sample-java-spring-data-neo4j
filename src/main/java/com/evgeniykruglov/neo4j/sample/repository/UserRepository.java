package com.evgeniykruglov.neo4j.sample.repository;

import com.evgeniykruglov.neo4j.sample.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author Evgeniy Kruglov
 */
public interface UserRepository extends Neo4jRepository<User,Long>{
}
