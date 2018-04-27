package com.evgeniykruglov.neo4j.sample.service;

import com.evgeniykruglov.neo4j.sample.service.dto.UserDTO;

/**
 * @author Evgeniy Kruglov
 */
public interface UserService {

    UserDTO createUser(UserDTO userDTO);
}
