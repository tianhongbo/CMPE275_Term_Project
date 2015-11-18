package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.*;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.dao
 * Author: Scott
 * Created Date: 11/16/15 11:21 PM
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
public class RoomDaoImpl implements RoomDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Room> getAllRoom() {
        List<Room> rooms = new LinkedList<Room>();
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query= session.createQuery("from Room");
            rooms = query.list();
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
        return rooms;
    }

    @Override
    public Room addRoom(Room room) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            room = null;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return room;
    }

    @Override
    public Room getRoom(String roomNo) {
        Transaction transaction = null;
        Session session = null;
        Room room = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            room = (Room)session.get(Room.class, roomNo);
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
        return room;
    }

    @Override
    public Room updateRoom(Room room) {
        Session session = null;
        Transaction transaction = null;
        Room roomOld = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            roomOld = (Room)session.get(Room.class, room.getRoomNo());
            if(roomOld == null) {
                throw new HibernateException("Can't find room with id = " + room.getRoomNo());
            }
            if (room.getRoomType() != null) {
                roomOld.setRoomType(room.getRoomType());
            }
            if (room.getBasePrice() != null) {
                roomOld.setBasePrice(room.getBasePrice());
            }

            if (room.getSmoking() != null) {
                roomOld.setSmoking(room.getSmoking());
            }

            session.update(roomOld);
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
        return roomOld;
    }

    @Override
    public Room removeRoom(String roomNo) {

        Session session = null;
        Transaction transaction = null;
        Room room = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            room = (Room)session.get(Room.class, roomNo);
            if (room == null) {
                throw new HibernateException("Can't find the record with id = " + roomNo);
            }

            /*
             * A room cannot be removed from the system if it is in use
             * or has a reservation.
             */

            for(Reservation reservation : room.getReservationList()) {

                //
            }

            //delete this friendship
            room.setReservationList(null);
            session.update(room);

            //delete this room
            session.delete(room);

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

        return room;
    }
}
