package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.RoomDao;
import edu.sjsu.cmpe275.project.util.ROOM_STATUS;
import edu.sjsu.cmpe275.project.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.controller
 * Author: Scott
 * Created Date: 11/16/15 11:44 PM
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
@CrossOrigin
@Controller
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    RoomDao roomDao;

    /** Get all rooms
     * @return	List<Room></Room>
     */

    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<?> createRoom() {

        List<Room> rooms = roomDao.getAllRoom();
        if (rooms == null) {
            return  new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Object>(rooms, HttpStatus.OK);
        }
    }

    /** Create a room
     * @param roomNo			Room No
     * @param roomType			Room Type
     * @param smoking			smoking, non-smoking
     * @param basePrice			Base Price
     * @return			void
     */

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<?> createRoom(@RequestParam(value = "room_no", required = true) String roomNo,
                                          @RequestParam(value = "room_type", required = true) Integer roomType,
                                          @RequestParam(value = "smoking", required = true) Boolean smoking,
                                          @RequestParam(value = "base_price", required = false) Integer basePrice) {

        Room room = new Room(roomNo, roomType, smoking, basePrice);
        room.setStatus(ROOM_STATUS.VACANT);
        room = roomDao.addRoom(room);
        if (room == null) {
            return  new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Object>(room, HttpStatus.OK);
        }
    }

    /** Get a room
     * @param roomNo			Room No
     * @return			void
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRoomJson(@PathVariable("id") String roomNo) {

        Room room = roomDao.getRoom(roomNo);
        if(room == null) {
            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(room, HttpStatus.OK);
        }
    }


    /** Update a room
     * @param a			Description of a
     * @param b			Description of b
     * @return			Description of c
     */

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateRoom(@PathVariable("id") String roomNo,
                                        @RequestParam(value = "room_type", required = true) Integer roomType,
                                        @RequestParam(value = "smoking", required = true) Boolean smoking,
                                        @RequestParam(value = "base_price", required = false) Integer basePrice) {

        Room room = new Room(roomNo, roomType, smoking, basePrice);
        room = roomDao.updateRoom(room);
        if (room == null){
            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Object>(room, HttpStatus.OK);
        }
    }

    /** Delete a room object
     * @param roomNo			Room No
     * @return			void
     */

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRoom(@PathVariable("id") String roomNo) {

        Room room = roomDao.removeRoom(roomNo);

        if (room == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(room, HttpStatus.OK);
        }
    }
}
