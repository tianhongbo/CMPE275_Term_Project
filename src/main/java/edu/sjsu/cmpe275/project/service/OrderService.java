package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.ReservationDao;
import edu.sjsu.cmpe275.project.dao.RoomDao;
import edu.sjsu.cmpe275.project.model.Reservation;
import edu.sjsu.cmpe275.project.model.Room;
import edu.sjsu.cmpe275.project.util.RESERVATION_STATUS;
import edu.sjsu.cmpe275.project.util.ROOM_STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.service
 * Author: Scott
 * Created Date: 11/19/15 10:42 PM
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
@Service
public class OrderService {
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private EmailService emailService;

    public Reservation get(Long id) {
        Reservation r = reservationDao.get(id);
        if (r == null) {
            return null;
        }
        if (r.getStatus() == RESERVATION_STATUS.CHECKED_IN
                || r.getStatus() == RESERVATION_STATUS.CHECKED_OUT) {
            return r;
        }
        return null;
    }

    public List<Reservation> getAll() {
        List<Reservation> result = new LinkedList<>();
        for(Reservation r : reservationDao.getAll()){
            if (r.getStatus() == RESERVATION_STATUS.CHECKED_IN
                    || r.getStatus() == RESERVATION_STATUS.CHECKED_OUT) {
                result.add(r);
            }
        }
        return result;
    }
    public Reservation checkIn(Long id) {
        Reservation reservation = reservationDao.get(id);
        if (reservation == null) {
            return null;
        }
        //update reservation status
        reservation.setStatus(RESERVATION_STATUS.CHECKED_IN);

        //update room status
        for (Room room : reservation.getRoomList()) {
            room.setStatus(ROOM_STATUS.IN_SERVICE);
            roomDao.updateRoom(room);
        }
        reservationDao.update(reservation);

        return reservation;
    }

    public Reservation checkOut(Long id) {
        Reservation reservation = reservationDao.get(id);
        if (reservation == null) {
            return null;
        }
        //update reservation status
        reservation.setStatus(RESERVATION_STATUS.CHECKED_OUT);

        //update room status
        for (Room room : reservation.getRoomList()) {
            room.setStatus(ROOM_STATUS.VACANT);
            roomDao.updateRoom(room);
        }
        reservationDao.update(reservation);

        emailService.sentReceipt(reservation);
        return reservation;
    }
}
