package com.evgeniykruglov.neo4j.sample.repository;

import com.evgeniykruglov.neo4j.sample.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Evgeniy Kruglov
 */
@Repository
public interface UserRepository extends Neo4jRepository<User,Long>{
}
