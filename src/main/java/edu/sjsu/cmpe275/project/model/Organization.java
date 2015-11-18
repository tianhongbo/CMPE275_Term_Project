package edu.sjsu.cmpe275.project.model;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Project Name: cmpe275lab2
 * Packet Name: edu.sjsu.cmpe275.project
 * Author: Scott
 * Created Date: 10/28/15 10:49 PM
 * Copyright (c) 2015, 2015 All Right Reserved, http://sjsu.edu/
 * This source is subject to the GPL2 Permissive License.
 * Please see the License.txt file for more information.
 * All other rights reserved.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 */
@Entity
@Table(name = "ORGANIZATION")

//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORG_ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "org")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Person> persons;

    public Organization() {
    }

    public Organization(String name, String description, Address address, List<Person> persons) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.persons = persons;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonIgnore
    public List<Person> getPersons() {
        return persons;
    }

    @JsonGetter(value = "persons")
    public List<Long> getPersonsId() {
        List<Long> list = new LinkedList<Long>();
        if (getPersons() == null) {
            return list;
        }
        for (Person p : getPersons()) {
            list.add(p.getId());
        }
        return list;
    }
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", persons=" + persons +
                '}';
    }
}
