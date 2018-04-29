package com.evgeniykruglov.neo4j.sample.neo4jrest.service;

import com.evgeniykruglov.neo4j.sample.domain.Department;
import com.evgeniykruglov.neo4j.sample.domain.User;
import com.evgeniykruglov.neo4j.sample.repository.DepartmentRepository;
import com.evgeniykruglov.neo4j.sample.repository.UserRepository;
import com.evgeniykruglov.neo4j.sample.service.UserService;
import com.evgeniykruglov.neo4j.sample.service.dto.UserDTO;
import com.evgeniykruglov.neo4j.sample.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.response.model.QueryResultModel;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Evgeniy Kruglov
 */
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private Session session;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration{

        @Bean
        public UserService userService(){
            return new UserServiceImpl();
        }
    }

    @Before
    public void setUp(){
        Department department = new Department("Test department");
        department.setId(3L);
        Result resultEmptySearch = prepareUserMapForEmptySearch();
        Result resultOneParameterSearch = prepareUserMapForSearchWithOneParameter();
        Result resultAllParameterSearch = prepareUserMapForSearchWithAllParameters();
        when(departmentRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(department));
        when(session.query(Mockito.contains("MATCH (user:User)-[:IN]->(department:Department) RETURN user, department"),Mockito.anyMap())).thenReturn(resultEmptySearch);
        when(session.query(Mockito.contains("MATCH (user:User)-[:IN]->(department:Department) WHERE ID(department) = 4 RETURN user, department"),Mockito.anyMap())).thenReturn(resultOneParameterSearch);
        when(session.query(Mockito.contains("MATCH (user:User)-[:IN]->(department:Department) WHERE ID(department) = 4 AND LOWER(user.lastName) " +
                "CONTAINS LOWER('FirstUser') AND LOWER(user.firstName) CONTAINS LOWER('FirstUser') RETURN user, department"),Mockito.anyMap())).thenReturn(resultAllParameterSearch);
    }

    private static Result prepareUserMapForEmptySearch(){
        Collection<Map<String, Object>> resultMap = new ArrayList<>();
        //create departments
        Department department = new Department("Test department");
        department.setId(4L);
        Department departmentSecond = new Department("Test department");
        department.setId(5L);
        //create users
        User firstUser = new User("FirstUser","FirstUser");
        firstUser.setDepartment(department);
        User secondUser = new User("SecondUser","SecondUser");
        secondUser.setDepartment(department);
        User thirdUser = new User("ThirdUser","ThirdUser");
        thirdUser.setDepartment(departmentSecond);
        //put properties into the map
        Map<String,Object> objects = new HashMap<>();
        objects.put("user",firstUser);
        objects.put("department",department);
        resultMap.add(objects);
        objects.put("user",secondUser);
        objects.put("department",department);
        resultMap.add(objects);
        objects.put("user",thirdUser);
        objects.put("department",departmentSecond);
        resultMap.add(objects);
        return new QueryResultModel(resultMap, null);
    }

    private static Result prepareUserMapForSearchWithOneParameter(){
        Collection<Map<String, Object>> resultMap = new ArrayList<>();
        //create departments
        Department department = new Department("Test department");
        department.setId(4L);
        //create users
        User firstUser = new User("FirstUser","FirstUser");
        firstUser.setDepartment(department);
        User secondUser = new User("SecondUser","SecondUser");
        secondUser.setDepartment(department);
        //put properties into the map
        Map<String,Object> objects = new HashMap<>();
        objects.put("user",firstUser);
        objects.put("department",department);
        resultMap.add(objects);
        objects.put("user",secondUser);
        objects.put("department",department);
        resultMap.add(objects);
        return new QueryResultModel(resultMap, null);
    }

    private static Result prepareUserMapForSearchWithAllParameters(){
        Collection<Map<String, Object>> resultMap = new ArrayList<>();
        //create departments
        Department department = new Department("Test department");
        department.setId(4L);
        //create users
        User firstUser = new User("FirstUser","FirstUser");
        firstUser.setDepartment(department);
        //put properties into the map
        Map<String,Object> objects = new HashMap<>();
        objects.put("user",firstUser);
        objects.put("department",department);
        resultMap.add(objects);
        return new QueryResultModel(resultMap, null);
    }

    @Test
    public void testCreateUserSuccess(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Leo");
        userDTO.setLastName("Smith");
        userDTO.setDepartmentId("1");
        userDTO = userService.createUser(userDTO);
        assertNotNull(userDTO);
        verify(departmentRepository,times(1)).findById(1L);
        verify(userRepository,times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void testCreateUserDepartmentNotFound(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Leo");
        userDTO.setLastName("Smith");
        userDTO.setDepartmentId("2");
        userDTO = userService.createUser(userDTO);
        assertNull(userDTO);
        verify(departmentRepository,times(1)).findById(2L);
        verify(userRepository,times(0)).save(Mockito.any(User.class));
    }

    @Test
    public void testFindUsersByEmptyParameters(){
        List<UserDTO> userDTOList = userService.findUsers(null,null,null);
        assertEquals(userDTOList.size(),3);
    }

    @Test
    public void testFindByOneParameter(){
        List<UserDTO> userDTOList = userService.findUsers("4",null,null);
        assertEquals(userDTOList.size(),2);
    }

    @Test
    public void testFindForAllParameters(){
        List<UserDTO> userDTOList = userService.findUsers("4","FirstUser","FirstUser");
        assertEquals(userDTOList.size(),1);
    }
}
