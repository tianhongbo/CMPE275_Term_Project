package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.FriendshipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Project Name: cmpe275lab2
 * Packet Name: edu.sjsu.cmpe275.project.service
 * Author: Scott
 * Created Date: 10/29/15 5:49 PM
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
@RequestMapping("/friends")
public class ManageFriendshipController {

    @Autowired
    FriendshipDao friendshipDao;

    /** Add a friendship object
     (9) Add a friend
     Path:friends/{id1}/{id2}
     Method: PUT

     This makes the two persons with the given IDs friends with each other.
     If either person does not exist, return 404.
     If the two persons are already friends, do nothing, just return 200. Otherwise,
     Record this friendship relation. If all is successful, return HTTP code 200 and any
     informative text message in the HTTP payload.

     * @param a			Description of a
     * @param b			Description of b
     * @return			Description of c
     */

    @RequestMapping(value="/{id1}/{id2}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity createFriendship(@PathVariable("id1") long id1,
                                   @PathVariable("id2") long id2) {
        int result = friendshipDao.create(id1, id2);

        if (result == 0) {
            return new ResponseEntity(new String[]{"Create friendship successfuly"},HttpStatus.OK);
        } else {
            return new ResponseEntity(new String[]{"Can not find persons"},HttpStatus.NOT_FOUND);
        }
    }

    /** Remove a friendship object
     (10) Remove a friend
     Path:friends/{id1}/{id2}
     Method: DELETE

     This request removes the friendship relation between the two persons.
     If either person does not exist, return 404.
     If the two persons are not friends, return 404. Otherwise,
     Remove this friendship relation. Return HTTP code 200 and a meaningful text message if all is successful.

     * @param a			Description of a
     * @param b			Description of b
     * @return			Description of c
     */

    @RequestMapping(value="/{id1}/{id2}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteFriendship(@PathVariable("id1") long id1,
                                   @PathVariable("id2") long id2) {
        int result = friendshipDao.delete(id1, id2);
        if (result == 0){
            return new ResponseEntity(new String[]{"Delete friendship successfuly"}, HttpStatus.OK);
        } else {
            return new ResponseEntity(new String[]{"Can not find persons"}, HttpStatus.NOT_FOUND);
        }
    }

}
