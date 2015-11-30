package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.UserDao;
import edu.sjsu.cmpe275.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.controller
 * Author: Scott
 * Created Date: 11/29/15 10:21 PM
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
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    @Autowired
    private UserDao userDao;

    /** Handle Option
     * @return			void
     */
/*
    @RequestMapping(value = "/**",method = RequestMethod.OPTIONS)
    public String getOption(HttpServletResponse response,Model model)
    {
        response.setHeader("Access-Control-Allow-Origin","*");

        response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");

        return "";
    }
*/
    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<?> createUser(@RequestParam(value = "name", required = true) String name,
                                        @RequestParam(value = "password", required = true) String password) {

        User user = userDao.getUser(name);
        if (user == null || password == null || !password.equals(user.getPassword())) {
            return  new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        }
    }
}
