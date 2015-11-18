package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Booking;
import edu.sjsu.cmpe275.project.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.dao
 * Author: Scott
 * Created Date: 11/17/15 9:29 PM
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
public class UserDaoImpl implements UserDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User addUser(User user) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            user = null;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return user;
    }

    @Override
    public User getUser(String name) {
        Transaction transaction = null;
        Session session = null;
        User user = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            user = (User)session.get(User.class, name);
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
        return user;
    }

    @Override
    public User updateUser(User user) {
        Session session = null;
        Transaction transaction = null;
        User userOld = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            userOld = (User)session.get(User.class, user.getName());
            if(userOld == null) {
                throw new HibernateException("Can't find user with name = " + user.getName());
            }
            if (user.getPassword() != null) {
                userOld.setPassword(user.getPassword());
            }
            if (user.getRole() != null) {
                userOld.setRole(user.getRole());
            }
            session.update(userOld);
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
        return userOld;
    }

    @Override
    public User removeUser(String name) {

        Session session = null;
        Transaction transaction = null;
        User user = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            user = (User)session.get(User.class, name);
            if (user == null) {
                throw new HibernateException("Can't find the record with name = " + name);
            }
            session.delete(user);
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
        return user;
    }
}
