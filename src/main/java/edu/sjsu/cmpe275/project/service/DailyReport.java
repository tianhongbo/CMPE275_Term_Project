package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.ReservationDao;
import edu.sjsu.cmpe275.project.dao.RoomDao;
import edu.sjsu.cmpe275.project.model.RoomReportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
public class DailyReport {

    @Autowired
    RoomDao roomDao;
    @Autowired
    ReservationDao reservationDao;

    public RoomReportData postRport(Date date) {

        //TODO
        return new RoomReportData();
    }

    public RoomReportData futureReprot(Date date) {
        //TODO
        return null;
    }

    public RoomReportData currentReport(Date date) {
        //TODO
        return null;
    }
}
