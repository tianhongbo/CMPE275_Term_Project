package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.RoomDao;
import edu.sjsu.cmpe275.project.model.Room;
import edu.sjsu.cmpe275.project.service.SearchRoomAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.controller
 * Author: Scott
 * Created Date: 11/17/15 10:56 PM
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
@Controller
@RequestMapping("/api/v1/query")
public class QueryController {

    @Autowired
    SearchRoomAvailability searchRoom;

    /** Search room availability
     * @param roomNum			Room No
     * @param roomType			Room Type
     * @param smoking			smoking, non-smoking
     * @param checkinDate			check in date
     * @param checkoutDate			check out date
     * @return			void
     */

    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<?> createRoom(@RequestParam(value = "checkin_date", required = true) Date checkinDate,
                                        @RequestParam(value = "checkout_date", required = true) Date checkoutDate,
                                        @RequestParam(value = "room_num", required = true) Integer roomNum,
                                        @RequestParam(value = "room_type", required = false) Integer roomType,
                                        @RequestParam(value = "smoking", required = true) Boolean smoking) {

        List<Room> rooms = searchRoom.query(checkinDate, checkoutDate, roomNum, roomType, smoking);
        if (rooms == null) {
            return  new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Object>(rooms, HttpStatus.OK);
        }


    }
}
