package com.evgeniykruglov.neo4j.sample.service.impl;

import com.evgeniykruglov.neo4j.sample.domain.Department;
import com.evgeniykruglov.neo4j.sample.repository.DepartmentRepository;
import com.evgeniykruglov.neo4j.sample.service.DepartmentService;
import com.evgeniykruglov.neo4j.sample.service.dto.DepartmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Department
 * @author Evgeniy Kruglov
 */
@Service
public class DepartmentServiceImpl implements DepartmentService{

    private static final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Create Department
     * @param departmentDTO
     * @return departmendDTO with created Id
     */
    @Transactional
    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        log.debug("Request to create Department : {}", departmentDTO);
        Department department = new Department(departmentDTO.getName());
        departmentRepository.save(department);
        departmentDTO.setId(String.valueOf(department.getId()));
        return departmentDTO;
    }
}
