package com.evgeniykruglov.neo4j.sample.neo4jrest.repository;

import com.evgeniykruglov.neo4j.sample.domain.User;
import com.evgeniykruglov.neo4j.sample.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * @author Evgeniy Kruglov
 */
@RunWith(SpringRunner.class)
@DataNeo4jTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser(){
        User user = new User("Leo","Smith");
        user = userRepository.save(user);
        assertNotNull(user.getId());
    }

}
