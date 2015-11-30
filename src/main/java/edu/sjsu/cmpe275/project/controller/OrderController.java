package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.controller
 * Author: Scott
 * Created Date: 11/19/15 10:41 PM
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
@RequestMapping(value = "/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //check in
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> checkIn(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Object>(orderService.checkIn(id), HttpStatus.OK);
    }

    //check out
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> checkOut(@PathVariable(value = "id") Long id) {
        return new ResponseEntity(orderService.checkOut(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Object>(orderService.get(id), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<Object>(orderService.getAll(), HttpStatus.OK);
    }
}
