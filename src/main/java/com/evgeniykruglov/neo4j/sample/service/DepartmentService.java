package com.evgeniykruglov.neo4j.sample.service;

import com.evgeniykruglov.neo4j.sample.service.dto.DepartmentDTO;

/**
 * @author Evgeniy Kruglov
 */
public interface DepartmentService {

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

}
