package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.ReservationDao;
import edu.sjsu.cmpe275.project.model.*;
import edu.sjsu.cmpe275.project.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.controller
 * Author: Scott
 * Created Date: 11/18/15 11:08 AM
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
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    /** Get all reservations
     * @return			void
     */

    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<?> createReservation() {

        List<Reservation> reservations = reservationService.getAll();
        if (reservations == null) {
            return  new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Object>(reservations, HttpStatus.OK);
        }
    }

    /** Create a reservation
     * Once the guest is assured of the room availability, he can make a
     reservation by providing his email, full name, driver license #, and billing
     address. Then a unique reservation ID is created for this reservation. An
     email with the reservation ID and details is sent to the guest, including a
     link for the guest to cancel the reservation.
     * @param email			email
     * @param fname			first name
     * @param midname			middle name
     * @param lname			last name
     * @return			void
     */

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<?> createReservation(@RequestParam(value = "email", required = true) String email,
                                               @RequestParam(value = "fname", required = true) String fname,
                                               @RequestParam(value = "midname", required = false) String midname,
                                               @RequestParam(value = "lname", required = true) String lname,
                                               @RequestParam(value = "dl_no", required = true) String dlNo,
                                               @RequestParam(value = "street", required = true) String street,
                                               @RequestParam(value = "city", required = true) String city,
                                               @RequestParam(value = "state", required = true) String state,
                                               @RequestParam(value = "zip", required = true) String zip,
                                               @RequestParam(value = "checkin_date", required = true) Date checkinDate,
                                               @RequestParam(value = "checkout_date", required = true) Date checkoutDate,
                                               @RequestParam(value = "discount", required = false) Integer discount,
                                               @RequestParam(value = "rooms", required = true) List<String> rooms) {


        Reservation reservation = new Reservation();
        reservation.setEmail(email);
        reservation.setDlNo(dlNo);
        reservation.setName(new Name(fname, midname, lname));
        reservation.setBillingAddress(new Address(street, city, state, zip));
        reservation.setCheckinDate(checkinDate);
        reservation.setCheckoutDate(checkoutDate);
        if (discount == null) {
            reservation.setDiscount(0);
        } else {
            reservation.setDiscount(discount);
        }

        List<Room> roomList = new LinkedList<>();
        for (String s : rooms) {
            roomList.add(new Room(s));
        }
        reservation.setRoomList(roomList);

        reservation = reservationService.confirm(reservation);
        if (reservation == null) {
            return  new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<Object>(reservation, HttpStatus.OK);
        }
    }

    /** Get a reservation
     * @param reservationNo			Reservation No
     * @return			void
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getReservationJson(@PathVariable("id") Long reservationNo) {

        Reservation reservation = reservationService.get(reservationNo);
        if(reservation == null) {
            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        }
    }


    /** Update a reservation
     * @param a			Description of a
     * @param b			Description of b
     * @return			Description of c
     */

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateReservation(@PathVariable("id") Long reservationNo,
                                               @RequestParam(value = "email", required = true) String email,
                                               @RequestParam(value = "fname", required = true) String fname,
                                               @RequestParam(value = "midname", required = false) String midname,
                                               @RequestParam(value = "lname", required = true) String lname,
                                               @RequestParam(value = "dl_no", required = true) String dlNo,
                                               @RequestParam(value = "street", required = true) String street,
                                               @RequestParam(value = "city", required = true) String city,
                                               @RequestParam(value = "state", required = true) String state,
                                               @RequestParam(value = "zip", required = true) String zip,
                                               @RequestParam(value = "discount", required = false) Integer discount,
                                               @RequestParam(value = "rooms", required = true) List<String> rooms) {

        //TODO

        Reservation reservation = new Reservation();
        reservation = reservationService.update(reservation);
        if (reservation == null){
            return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<Object>(reservation, HttpStatus.OK);
        }
    }

    /** cancel a reservation
     * @param reservationNo			Reservation No
     * @return			void
     */

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteReservation(@PathVariable("id") Long reservationNo) {

        Reservation reservation = reservationService.cancel(reservationNo);

        if (reservation == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(reservation, HttpStatus.OK);
        }
    }

    //cancel by user via link in the reservation confirmation email
    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.GET)
    public String cancelReservationByUser(@PathVariable("id") Long id, Model model) {
        Reservation reservation = reservationService.cancel(id);
        if (reservation == null) {
            return "error404";
        }else {
            model.addAttribute("reservation", reservation);
            return "cancelreservation";
        }
    }

}
