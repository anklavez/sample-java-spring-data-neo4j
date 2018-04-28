package com.evgeniykruglov.neo4j.sample.repository;

import com.evgeniykruglov.neo4j.sample.domain.Department;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Evgeniy Kruglov
 */
@Repository
public interface DepartmentRepository extends Neo4jRepository<Department,Long>{
}
