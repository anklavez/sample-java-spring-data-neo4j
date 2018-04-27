package com.evgeniykruglov.neo4j.sample.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author Evgeniy Kruglov
 */
@NodeEntity
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
