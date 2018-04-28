package com.evgeniykruglov.neo4j.sample.neo4jrest;

import com.evgeniykruglov.neo4j.sample.domain.Department;
import com.evgeniykruglov.neo4j.sample.repository.DepartmentRepository;
import com.evgeniykruglov.neo4j.sample.repository.UserRepository;
import com.evgeniykruglov.neo4j.sample.service.DepartmentService;
import com.evgeniykruglov.neo4j.sample.service.UserService;
import com.evgeniykruglov.neo4j.sample.service.dto.DepartmentDTO;
import com.evgeniykruglov.neo4j.sample.service.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class Neo4JApplicationTests {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private UserService userService;

	@Test
	public void testCreateDepartment(){
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setName("New department");
		departmentDTO = departmentService.createDepartment(departmentDTO);
		assertNotNull(departmentDTO.getId());
	}

	@Test
	public void testCreateUserSuccess(){
		//prepare data
		Department department = new Department("Engineering department");
		departmentRepository.save(department);

		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName("John");
		userDTO.setLastName("Smith");
		userDTO.setDepartmentId(String.valueOf(department.getId()));
		userDTO = userService.createUser(userDTO);
		assertNotNull(userDTO);
	}

	@Test
	public void testCreateUserDepartmentNotFound(){
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName("John");
		userDTO.setLastName("Smith");
		userDTO.setDepartmentId("1");
		userDTO = userService.createUser(userDTO);
		assertNull(userDTO);
	}

}
