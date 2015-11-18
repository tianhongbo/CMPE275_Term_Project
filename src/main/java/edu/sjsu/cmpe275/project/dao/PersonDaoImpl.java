package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Address;
import edu.sjsu.cmpe275.project.model.Organization;
import edu.sjsu.cmpe275.project.model.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Project Name: cmpe275lab2
 * Packet Name: edu.sjsu.cmpe275.project.dao
 * Author: Scott
 * Created Date: 11/5/15 3:54 PM
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
@Repository
public class PersonDaoImpl implements PersonDao {
    @Autowired
    SessionFactory sessionFactory;

    public Person create(Person person, Long orgid) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if(orgid != null) {
                Organization organization = (Organization)session.get(Organization.class, orgid);
                person.setOrg(organization);
            }
            session.save(person);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            person = null;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return person;
    }

    public Person get(long id) {
        Transaction transaction = null;
        Session session = null;
        Person person = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            person = (Person)session.get(Person.class, id);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return person;
    }

    public Person update(Person person, Long orgId) {
        Session session = null;
        Transaction transaction = null;
        Person personOld = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            personOld = (Person)session.get(Person.class, person.getId());
            if(personOld == null) {
                throw new HibernateException("Can't find person with id = " + person.getId());
            }
            if (person.getDescription() != null) {
                personOld.setDescription(person.getDescription());
            }
            if (person.getEmail() != null) {
                personOld.setEmail(person.getEmail());
            }

            if (person.getFirstname() != null) {
                personOld.setFirstname(person.getFirstname());
            }
            if (person.getLastname() != null) {
                personOld.setLastname(person.getLastname());
            }
            if (person.getAddress() != null) {
                Address address = new Address(person.getAddress().getStreet(),
                        person.getAddress().getCity(),
                        person.getAddress().getState(),
                        person.getAddress().getZip());
                personOld.setAddress(address);
            }

            if(orgId != null) {
                Organization organization = (Organization)session.get(Organization.class, orgId);
                personOld.setOrg(organization);
            }

            session.update(personOld);
            transaction.commit();

        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return personOld;
    }

    public Person delete(long id) {

        Session session = null;
        Transaction transaction = null;
        Person person = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            person = (Person)session.get(Person.class, id);
            if (person == null) {
                throw new HibernateException("Can't find the record with id = " + id);
            }

            //delete friendship
            for(Person p : person.getFriends()) {
                java.util.List<Person> f = p.getFriends();
                if(f.contains(person)) {
                    f.remove(person);
                    session.update(p);
                }
            }

            //delete this friendship
            person.setFriends(null);
            session.update(person);

            //delete this person
            session.delete(person);

            transaction.commit();

        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }

        return person;
    }
}
