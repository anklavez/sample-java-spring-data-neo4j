package com.evgeniykruglov.neo4j.sample.service.impl;

import com.evgeniykruglov.neo4j.sample.domain.Department;
import com.evgeniykruglov.neo4j.sample.domain.User;
import com.evgeniykruglov.neo4j.sample.repository.DepartmentRepository;
import com.evgeniykruglov.neo4j.sample.repository.UserRepository;
import com.evgeniykruglov.neo4j.sample.service.UserService;
import com.evgeniykruglov.neo4j.sample.service.dto.UserDTO;
import com.evgeniykruglov.neo4j.sample.util.SimpleCypherQueryBuilder;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Evgeniy Kruglov
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private Session session;

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Optional<Department> department = departmentRepository.findById(Long.valueOf(userDTO.getDepartmentId()));
        if (department.isPresent()) {
            User user = new User(userDTO.getFirstName(), userDTO.getLastName());
            user.setDepartment(department.get());
            userRepository.save(user);
            userDTO.setId(String.valueOf(user.getId()));
            return userDTO;
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> findUsers(String departmentId, String firstName, String lastName) {
        Map<String, String> parameters = new HashMap<>();
        if (departmentId != null) parameters.put("ID(department) = %s",departmentId);
        if (firstName != null) parameters.put("LOWER(user.firstName) CONTAINS LOWER('%s')",firstName);
        if (lastName != null) parameters.put("LOWER(user.lastName) CONTAINS LOWER('%s')",lastName);
        String cypherQuery = SimpleCypherQueryBuilder.buildQuery("MATCH (user:User)-[:IN]->(department:Department)",parameters," RETURN user, department");
        Result result = session.query(cypherQuery, Collections.emptyMap());
        Iterator<Map<String, Object>> it = result.iterator();
        List<UserDTO> userDTOS = new ArrayList<>();
        while (it.hasNext()){
            Map<String, Object> object = it.next();
            UserDTO userDTO = new UserDTO();
            for(String key:object.keySet()){
                if(key.equalsIgnoreCase("user")){
                    User user = (User) object.get(key);
                    userDTO.setFirstName(user.getFirstName());
                    userDTO.setLastName(user.getLastName());
                    userDTO.setId(String.valueOf(user.getId()));
                }else if(key.equalsIgnoreCase("department")){
                    Department department = (Department) object.get(key);
                    userDTO.setDepartmentId(String.valueOf(department.getId()));
                }
            }
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }


}
