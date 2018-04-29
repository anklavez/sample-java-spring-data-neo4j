package com.evgeniykruglov.neo4j.sample.util;

import com.evgeniykruglov.neo4j.sample.domain.Department;
import com.evgeniykruglov.neo4j.sample.domain.User;
import com.evgeniykruglov.neo4j.sample.service.dto.UserDTO;
import org.neo4j.ogm.model.Result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Evgeniy Kruglov
 */
public class Util {

    public static List<UserDTO> extractUserDTOFromSessionResult(Result result){
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
