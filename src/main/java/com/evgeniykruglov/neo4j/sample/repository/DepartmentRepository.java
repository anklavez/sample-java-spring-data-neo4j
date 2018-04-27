package com.evgeniykruglov.neo4j.sample.repository;

import com.evgeniykruglov.neo4j.sample.domain.Department;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author Evgeniy Kruglov
 */
public interface DepartmentRepository extends Neo4jRepository<Department,Long>{
}
