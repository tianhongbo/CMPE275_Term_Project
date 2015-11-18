package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project Name: cmpe275lab2
 * Packet Name: edu.sjsu.cmpe275.project.dao
 * Author: Scott
 * Created Date: 11/5/15 7:25 PM
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
public class FriendshipDaoImpl implements FriendshipDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public int create(long id1, long id2) {
        int result = 0;
        Session session = null;
        Transaction transaction = null;
        Person person1 = null, person2 = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            person1 = (Person) session.get(Person.class, id1);
            person2 = (Person) session.get(Person.class, id2);
            if(person1 == null || person2 == null) {
                throw new HibernateException("Can't find persons with id1 = " + id1 + " and id2 = " + id2);
            }
            List<Person> f = person1.getFriends();
            if (!f.contains(person2)) {
                f.add(person2);
            }
            person1.setFriends(f);
            session.update(person1);

            f = person2.getFriends();
            if (!f.contains(person1)) {
                f.add(person1);
            }
            person2.setFriends(f);
            session.update(person2);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            result = 1;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public int delete(long id1, long id2) {
        int result = 1;

        Session session = null;
        Transaction transaction = null;
        Person person1 = null, person2 = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            person1 = (Person)session.get(Person.class, id1);
            person2 = (Person)session.get(Person.class, id2);
            if (person1 == null || person2 == null) {
                throw new HibernateException("can't find person records with id1 = " + id1 + " and id2 = " + id2);
            }
            List<Person> l1 = person1.getFriends();
            List<Person> l2 = person2.getFriends();

            if (!l1.contains(person2) || !l2.contains(person1)) {
                throw new HibernateException("These two persons are not friends id1 = " + id1 + " and id2 = " + id2);
            }

            l1.remove(person2);
            l2.remove(person1);
            session.update(person1);
            session.update(person2);
            transaction.commit();
            result = 0;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }
}
