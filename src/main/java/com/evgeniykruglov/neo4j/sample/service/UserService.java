package com.evgeniykruglov.neo4j.sample.service;

import com.evgeniykruglov.neo4j.sample.service.dto.UserDTO;

import java.util.List;

/**
 * @author Evgeniy Kruglov
 */
public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> findUsers(String departmentId, String firstName, String lastName);
}

