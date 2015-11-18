package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.ReservationDao;
import edu.sjsu.cmpe275.project.dao.RoomDao;
import edu.sjsu.cmpe275.project.model.Booking;
import edu.sjsu.cmpe275.project.model.Reservation;
import edu.sjsu.cmpe275.project.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.service
 * Author: Scott
 * Created Date: 11/17/15 11:03 PM
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
public class SearchRoomAvailability {

    @Autowired
    RoomDao roomDao;
    ReservationDao reservationDao;

    public List<Room> query(Date checkinDate, Date checkoutDate, int roomNum, Integer roomType, Boolean smoking) {
        List<Room> result = new LinkedList<Room>();

        //TODO
        List<Room> rooms = roomDao.getAllRoom();

        for(Room r : rooms){
            if(roomType != null && !roomType.equals(r.getRoomType())) {
                continue;
            }
            if(smoking != null && !smoking.equals(r.getSmoking())) {
                continue;
            }
            if(isAvailable(r, checkinDate, checkoutDate)) {
                result.add(r);
            }
        }

        if(result.size() < roomNum) {
            return null;
        }
        return result;
    }

    private boolean isAvailable(Room room, Date checkinDate, Date checkoutDate) {
        List<Reservation> reservations = room.getReservationList();
        if (reservations == null) {
            return true;
        }
        for (Reservation reservation : reservations) {
            Date inDate = reservation.getCheckinDate();
            Date outDate = reservation.getCheckoutDate();
            if ((inDate.after(checkinDate) && inDate.before(checkoutDate)) ||
            (outDate.after(checkinDate) && outDate.before(checkoutDate))){
                return false;
            }
        }
        return true;
    }
}
