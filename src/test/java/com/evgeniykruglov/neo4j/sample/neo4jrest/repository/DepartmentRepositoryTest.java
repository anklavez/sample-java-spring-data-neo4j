package com.evgeniykruglov.neo4j.sample.neo4jrest.repository;

import com.evgeniykruglov.neo4j.sample.domain.Department;
import com.evgeniykruglov.neo4j.sample.repository.DepartmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * @author Evgeniy Kruglov
 */
@RunWith(SpringRunner.class)
@DataNeo4jTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Before
    public void setUp(){
        Department department = new Department("Test department");
        department.setId(1l);
        departmentRepository.save(department);
    }

    @Test
    public void testFindById(){
        Optional<Department> department = departmentRepository.findById(1l);
        assertEquals(department.isPresent(),true);
        Department result = department.get();
        Long id = 1L;
        assertEquals(result.getId(),id);
    }

    @Test
    public void testSave(){
        Department department = new Department("New department");
        department = departmentRepository.save(department);
        assertNotNull(department);
        assertEquals(department.getName(),"New department");
        assertNotNull(department.getId());
    }
}
