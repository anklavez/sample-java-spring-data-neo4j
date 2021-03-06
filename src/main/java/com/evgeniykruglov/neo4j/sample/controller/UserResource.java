package com.evgeniykruglov.neo4j.sample.controller;

import com.evgeniykruglov.neo4j.sample.util.HeaderUtil;
import com.evgeniykruglov.neo4j.sample.service.UserService;
import com.evgeniykruglov.neo4j.sample.service.dto.UserDTO;
import com.evgeniykruglov.neo4j.sample.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing User
 * @author Evgeniy Kruglov
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private  UserService userService;

    private static final String ENTITY_NAME = "user";

    /**
     * POST /user: create User
     * @param userDTO object with firstName, lastName, departmentId
     * @return object with created Id
     * @throws URISyntaxException
     */
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to create User : {}", userDTO);
        if (userDTO.getDepartmentId() == null) {
            return ResponseUtil.createBadRequestResponse(ENTITY_NAME,"departmentId is null","User should have departmentId");
        }
        UserDTO result = userService.createUser(userDTO);
        if (result == null) {
            return ResponseUtil.createBadRequestResponse(ENTITY_NAME,"departmentId is not found","Can't find provided departmentId");
        }
        return ResponseEntity.created(new URI("/api/users" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
                .body(result);
    }

    /**
     * GET /users: find uders
     * @param departmentId
     * @param firstName
     * @param lastName
     * @return List of found objects
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> findUsers(@RequestParam(required = false) String departmentId,
                                                   @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName){
        log.debug("REST request to find Users with departmentId: {} , firstName: {}, lastName: {}", departmentId,firstName,lastName);
        List<UserDTO> result = userService.findUsers(departmentId,firstName,lastName);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}
