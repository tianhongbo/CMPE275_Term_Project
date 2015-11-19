package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Reservation;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.dao
 * Author: Scott
 * Created Date: 11/17/15 11:50 PM
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
public class ReservationDaoImpl implements ReservationDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Reservation add(Reservation reservation) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(reservation);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            reservation = null;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return reservation;
    }

    @Override
    public List<Reservation> getAll() {
        Session session = null;
        Transaction transaction = null;
        List<Reservation> reservations = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Reservation");
            reservations = query.list();
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
        return reservations;
    }

    @Override
    public Reservation get(Long id) {
        Session session = null;
        Transaction transaction = null;
        Reservation reservation = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            reservation = session.get(Reservation.class, id);
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
        return reservation;
    }

    @Override
    public Reservation cancel(Long id) {
        Session session = null;
        Transaction transaction = null;
        Reservation reservation = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            reservation = session.get(Reservation.class, id);
            if (reservation != null) {
                session.delete(reservation);
            }
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
        return reservation;
    }

    @Override
    public Reservation update(Reservation reservation) {
        Session session = null;
        Transaction transaction = null;
        Reservation oldReservation = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            oldReservation = session.get(Reservation.class, reservation.getId());
            //TODO
            //lot of stuff to be done here
            oldReservation.setBillingAddress(reservation.getBillingAddress());
            oldReservation.setName(reservation.getName());
            oldReservation.setDlNo(reservation.getDlNo());
            oldReservation.setEmail(reservation.getEmail());
            oldReservation.setStatus(reservation.getStatus());
            session.update(oldReservation);
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
        return oldReservation;
    }
}
