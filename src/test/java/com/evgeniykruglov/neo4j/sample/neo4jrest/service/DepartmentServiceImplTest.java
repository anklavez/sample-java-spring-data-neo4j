package com.evgeniykruglov.neo4j.sample.neo4jrest.service;

import com.evgeniykruglov.neo4j.sample.domain.Department;
import com.evgeniykruglov.neo4j.sample.repository.DepartmentRepository;
import com.evgeniykruglov.neo4j.sample.service.DepartmentService;
import com.evgeniykruglov.neo4j.sample.service.dto.DepartmentDTO;
import com.evgeniykruglov.neo4j.sample.service.impl.DepartmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Evgeniy Kruglov
 */
@RunWith(SpringRunner.class)
public class DepartmentServiceImplTest {

    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    @TestConfiguration
    static class DepartmentServiceImplTestContextConfiguration{

        @Bean
        public DepartmentService departmentService(){
            return new DepartmentServiceImpl();
        }
    }

    @Test
    public void setUp(){
        Department department = new Department("Test department");
        Department savedDepartment = new Department("Test department");
        savedDepartment.setId(1L);
        when(departmentRepository.save(Mockito.any())).thenReturn(department);
    }

    @Test
    public void createDepartment(){
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("Test department");
        departmentDTO = departmentService.createDepartment(departmentDTO);
        assertNotNull(departmentDTO);
    }
}
