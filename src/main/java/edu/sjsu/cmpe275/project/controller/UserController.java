package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.UserDao;
import edu.sjsu.cmpe275.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.controller
 * Author: Scott
 * Created Date: 11/17/15 9:36 PM
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
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserDao userDao;


    /** get all users
     * @return			void
     */

    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers() {

        List<User> users = userDao.getAllUsers();

        if (users == null) {
            return  new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Object>(users, HttpStatus.OK);
        }
    }
    /** Create a user
     * @param name			Account Name
     * @param password			Password
     * @param role			role = {"admin", "agent", "guest"}
     * @return			void
     */

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestParam(value = "name", required = true) String name,
                                        @RequestParam(value = "password", required = true) String password,
                                        @RequestParam(value = "role", required = true) String role) {

        User user = new User(name, password, role);
        user = userDao.addUser(user);
        if (user == null) {
            return  new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        }
    }

    /** Get a user
     * @param name			User Name
     * @return			void
     */


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserJson(@PathVariable("id") String name) {

        User user = userDao.getUser(name);
        if(user == null) {
            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    /** Update a user
     * @return			Description of c
     */

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@PathVariable("id") String name,
                                        @RequestParam(value = "password", required = true) String password,
                                        @RequestParam(value = "role", required = true) String role) {

        User user = new User(name, password, role);
        user = userDao.updateUser(user);
        if (user == null){
            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        }
    }

    /** Delete a user object
     * @return			void
     */

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") String name) {

        User user = userDao.removeUser(name);

        if (user == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(user, HttpStatus.OK);
        }
    }
}
