package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.ReservationDao;
import edu.sjsu.cmpe275.project.dao.RoomDao;
import edu.sjsu.cmpe275.project.util.RESERVATION_STATUS;
import edu.sjsu.cmpe275.project.model.Reservation;
import edu.sjsu.cmpe275.project.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.service
 * Author: Scott
 * Created Date: 11/18/15 2:22 PM
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
public class ReservationService {
    @Autowired
    RoomDao roomDao;
    @Autowired
    ReservationDao reservationDao;
    @Autowired
    private EmailService emailService;


    public Reservation confirm(Reservation reservation) {
        List<Room> roomList = new LinkedList<>();

        reservation.setStatus(RESERVATION_STATUS.RESERVED);
        for (Room room : reservation.getRoomList()) {
            Room newRoom = roomDao.getRoom(room.getRoomNo());
            if (newRoom != null) {
                roomList.add(newRoom);
            }
        }
        reservation.setRoomList(roomList);

        //TODO: double check the room availability
        reservation = reservationDao.add(reservation);

        //Send email to customer
        emailService.sendConfirmation(reservation);
        return reservation;
    }

    public List<Reservation> getAll() {
        return reservationDao.getAll();
    }

    public Reservation update(Reservation reservation) {
        return reservationDao.update(reservation);
    }

    public Reservation cancel(Long id) {
        return reservationDao.cancel(id);
    }

    public Reservation get(Long id) {
        return reservationDao.get(id);
    }


}
