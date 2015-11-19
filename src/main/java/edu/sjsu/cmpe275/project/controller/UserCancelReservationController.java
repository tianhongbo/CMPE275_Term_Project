package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.model.Person;
import edu.sjsu.cmpe275.project.model.Reservation;
import edu.sjsu.cmpe275.project.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.controller
 * Author: Scott
 * Created Date: 11/19/15 11:10 AM
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
@RequestMapping("/api/v1/cancel")
public class UserCancelReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(value = "/reservation/{id}", method = RequestMethod.GET)
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
