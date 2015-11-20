package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.ReservationDao;
import edu.sjsu.cmpe275.project.dao.RoomDao;
import edu.sjsu.cmpe275.project.model.*;
import edu.sjsu.cmpe275.project.util.RESERVATION_STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.service
 * Author: Scott
 * Created Date: 11/18/15 1:58 PM
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
public class ReportService {

    @Autowired
    RoomDao roomDao;
    @Autowired
    ReservationDao reservationDao;

    public RoomReportData postRport(Date date) {

        /*
        * 1. in-service == [checkin date <= date < checkout date]
        * 2. vacant == total - in-service
        * 3. not considering some rooms were removed
         */
        RoomReportData result = new RoomReportData();
        List<Room> roomList = roomDao.getAllRoom();
        for (Room room : roomList) {
            for (Reservation r : room.getReservationList()) {
                RESERVATION_STATUS status = r.getStatus();
                if ((status == RESERVATION_STATUS.CHECKED_IN || status == RESERVATION_STATUS.CHECKED_OUT)
                        && (date.getTime() >= r.getCheckinDate().getTime() && date.getTime() < r.getCheckoutDate().getTime())) {
                    result.setNumOfInservice(result.getNumOfInservice() + 1);
                }
            }
        }
        result.setNumOfVacant(roomList.size() - result.getNumOfInservice());
        return result;
    }

    public RoomReportData futureReprot(Date date) {

        /*
        * 1. in-service = 0
        * 2. reserved = [checkin date <= date < checkout date]
        * 3. vacant = total - reserved
        * 4. not considering some rooms were removed
         */
        RoomReportData result = new RoomReportData();
        List<Room> roomList = roomDao.getAllRoom();
        for (Room room : roomList) {
            for (Reservation r : room.getReservationList()) {
                RESERVATION_STATUS status = r.getStatus();
                if ((status == RESERVATION_STATUS.RESERVED || status == RESERVATION_STATUS.CHECKED_IN)
                        && (date.getTime() >= r.getCheckinDate().getTime() && date.getTime() < r.getCheckoutDate().getTime())) {
                    result.setNumOfReserved(result.getNumOfReserved() + 1);
                }
            }
        }
        result.setNumOfVacant(roomList.size() - result.getNumOfReserved());
        return result;
    }

    public RoomReportData currentReport(Date date) {

        /*
        * 1. if checkout date == date, but not checkout yet, it counts on "in-service"
        * 2. if checkin date == date, but not check in yet, it counts on "reserved"
        * 3. numOfVacant = vacant + reserved
         */
        RoomReportData result = new RoomReportData();
        for (Room room : roomDao.getAllRoom()) {
            switch (room.getStatus()) {
                case IN_SERVICE:
                    result.setNumOfInservice(result.getNumOfInservice() + 1);
                    break;
                case VACANT:
                    result.setNumOfVacant(result.getNumOfVacant() + 1);
                    break;
                default:
                    break;
            }

            for (Reservation r : room.getReservationList()) {
                if (date.getTime() == r.getCheckinDate().getTime()
                        && r.getStatus() == RESERVATION_STATUS.RESERVED) {
                    result.setNumOfReserved(result.getNumOfReserved() + 1);
                }
            }
       }
        return result;
    }
}
