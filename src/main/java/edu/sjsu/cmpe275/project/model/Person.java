package edu.sjsu.cmpe275.project.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Project Name: cmpe275lab2
 * Packet Name: edu.sjsu.cmpe275.project
 * Author: Scott
 * Created Date: 10/28/15 10:48 PM
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
@Table(name = "PERSON")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property = "internalId")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstname;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastname;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "DESCRIPTION")
    private String description;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ORG_ID")
    private Organization org;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "FRIENDSHIP",
            joinColumns = {@JoinColumn(name = "ID1", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ID2", referencedColumnName = "ID")})
    private List<Person> friends;

    // constructors, setters, getters, etc.

    public Person() {
    }

    public Person(String firstname, String lastname, String email, String description, Address address, Organization org, List<Person> friends) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.description = description;
        this.address = address;
        this.org = org;
        this.friends = friends;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }
    @JsonIgnore
    public List<Person> getFriends() {
        return friends;
    }

    @JsonGetter(value = "friends")
    public List<Long> getFriendsId() {
        List<Long> list = new LinkedList<>();
        if (getFriends() != null) {
            for (Person person : getFriends()){
                list.add(person.getId());
            }
        }
        return list;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", org=" + org +
 //               ", friends=" + friends +
                '}';
    }
}
