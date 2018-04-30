package com.evgeniykruglov.neo4j.sample.controller;

import com.evgeniykruglov.neo4j.sample.util.HeaderUtil;
import com.evgeniykruglov.neo4j.sample.service.DepartmentService;
import com.evgeniykruglov.neo4j.sample.service.dto.DepartmentDTO;
import com.evgeniykruglov.neo4j.sample.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing Department
 * @author Evgeniy Kruglov
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    private static final String ENTITY_NAME = "department";

    private final DepartmentService departmentService;

    public DepartmentResource(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    /**
     * POST /department: create Department
     * @param departmentDTO object with department name
     * @return object with created Id
     * @throws URISyntaxException
     */
    @PostMapping("/department")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to create Department : {}", departmentDTO);
        if (departmentDTO.getName() == null) {
            return ResponseUtil.createBadRequestResponse(ENTITY_NAME,"department name is null","Department should have a name");
        }
        DepartmentDTO result = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.created(new URI("/api/v1/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
                .body(result);
    }
}
