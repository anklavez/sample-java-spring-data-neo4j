package com.evgeniykruglov.neo4j.sample.controller;

import com.evgeniykruglov.neo4j.sample.util.HeaderUtil;
import com.evgeniykruglov.neo4j.sample.service.UserService;
import com.evgeniykruglov.neo4j.sample.service.dto.UserDTO;
import com.evgeniykruglov.neo4j.sample.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * @author Evgeniy Kruglov
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    private static final String ENTITY_NAME = "user";

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        if (userDTO.getDepartmentId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "departmentId is null", "User should have departmentId")).body(null);
        }
        UserDTO result = userService.createUser(userDTO);
        if (result == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "departmentId is not found", "Can't find provided departmentId")).body(null);
        }
        return ResponseEntity.created(new URI("/api/user" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
                .body(result);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> findUsers(@RequestParam(required = false) String departmentId,
                                                   @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName){
        List<UserDTO> result = userService.findUsers(departmentId,firstName,lastName);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}
