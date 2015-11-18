package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.PersonDao;
import edu.sjsu.cmpe275.project.model.Address;
import edu.sjsu.cmpe275.project.model.Organization;
import edu.sjsu.cmpe275.project.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Project Name: cmpe275lab2
 * Packet Name: edu.sjsu.cmpe275.project.service
 * Author: Scott
 * Created Date: 10/29/15 1:07 PM
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
@RequestMapping("/person")
public class ManagePersonController {

    @Autowired
    PersonDao personDao;


    /** Create a person object
     * 1 Path: person?firstname=XX&lastname=YY& email=ZZ&description=UU&street=VV$...
     * Method: POST
     * This API creates a person object.
     * 1. For simplicity, all the person fields (firstname, lastname, email, street, city,
     *    organization, etc), except ID and friends, are passed in as query parameters. Only the
     *    firstname, lastname, and email are required. Anything else is optional.
     * 2. Friends is not allowed to be passed in as a parameter.
     * 3. The organization parameter, if present, must be the ID of an existing organization.
     * 4. The request returns the newly created person object in JSON in its HTTP payload,
     *    including all attributes. (Please note this differs from generally recommended practice
     *    of only returning the ID.)
     * 5. If the request is invalid, e.g., missing required parameters, the HTTP status code
     *    should be 400; otherwise 200.
     * @param a			Description of a
     * @param b			Description of b
     * @return			Description of c
     */

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<?> createPerson(@RequestParam(value = "firstname", required = true) String firstname,
                               @RequestParam(value = "lastname", required = true) String lastname,
                               @RequestParam(value = "email", required = true) String email,
                               @RequestParam(value = "description", required = false) String description,
                               @RequestParam(value = "street", required = false) String street,
                               @RequestParam(value = "city", required = false) String city,
                               @RequestParam(value = "state", required = false) String state,
                               @RequestParam(value = "zip", required = false) String zip,
                               @RequestParam(value = "orgid", required = false) Long orgid) {

        Organization organization = null;

        Person person = new Person();
        person.setEmail(email);
        person.setFirstname(firstname);
        person.setLastname(lastname);
        person.setAddress(new Address(street,city,state,zip));
        person.setDescription(description);

        person = personDao.create(person, orgid);
        if (person == null) {
            return  new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(person, HttpStatus.OK);
    }

    /** Get a person object
     2 Path: person/ {id}?format={json | xml | html}
     Method: GET
     This returns a full person object with the given ID in the given format in its HTTP payload.
     ● All existing fields, including the optional organization and list of friends should be
     returned.
     ○ The payload should contain the full organization object, if present.

     The list of friends can be either (a) list of person IDs, or (b) list of “ shallow ”
     person objects that do not have their friends list populated. If you take option
     (b), you want to use techniques like lazy loading to avoid serializing the whole
     social network starting from the requested person in the returned payload.
     ● If the person of the given user ID does not exist, the HTTP return code should be 404;
     otherwise, 200.
     ● The format parameter is optional, and the value is case insensitive. If missing, JSON is
     assumed.

     * @param a			Description of a
     * @param b			Description of b
     * @return			Description of c
     */

   // @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = "format=json", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)

    @ResponseBody
    public ResponseEntity<?> getPersonJson(@PathVariable("id") long id) {
        Person person = null;
        person = personDao.get(id);

        if(person == null) {
            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
    }



   @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = "format=xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody    public ResponseEntity<?> getPersonXml(@PathVariable("id") long id) {
        Person person = null;
        person = personDao.get(id);

        if(person == null) {
            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
    }




   @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = "format=html", produces = MediaType.TEXT_HTML_VALUE)
    public String getPersonHtml(@PathVariable("id") long id, Model model) {
        Person person = null;
        person = personDao.get(id);
        if (person == null) {
            return "error404";
        }else {
            model.addAttribute("person", person);
            return "person";
        }
    }
    /** Update a person object
     3 Path: person /{id} ?firstname=XX&lastname=YY& email=ZZ&description=UU&street=VV$...
     Method: POST
     This API updates a person object.
     ● For simplicity, all the person fields (firstname, lastname, email, street, city,
     organization, etc), except friends, should be passed in as query parameters. Required
     fields like email must be present. The object constructed from the parameters will
     completely replace the existing object in the server, except that it does not change the
     person’s list of friends.
     ● Similar to the get method, the request returns the updated person object, including all
     attributes (first name, last name, email, friends , organization, etc), in JSON. If the
     person ID does not exist, 404 should be returned. If required parameters are missing,
     return 400 instead. Otherwise, return 200.

     * @param a			Description of a
     * @param b			Description of b
     * @return			Description of c
     */

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updatePerson(@PathVariable("id") long id,
                               @RequestParam(value = "firstname", required = false) String firstname,
                               @RequestParam(value = "lastname", required = false) String lastname,
                               @RequestParam(value = "email", required = true) String email,
                               @RequestParam(value = "description", required = false) String description,
                               @RequestParam(value = "street", required = false) String street,
                               @RequestParam(value = "city", required = false) String city,
                               @RequestParam(value = "state", required = false) String state,
                               @RequestParam(value = "zip", required = false) String zip,
                               @RequestParam(value = "orgid", required = false) Long orgid) {

        Person person = new Person();
        person.setId(id);
        person.setDescription(description);
        if (street != null || city != null || state != null || zip != null) {
            person.setAddress(new Address(street,city,state,zip));
        }
        person.setLastname(lastname);
        person.setFirstname(firstname);
        person.setEmail(email);

        person = personDao.update(person, orgid);

        if (person == null){
            return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Object>(person, HttpStatus.OK);
        }
    }

    /** Delete a person object
     (4) Delete a person
     URL: h ttp://person/{ id}
     Method: DELETE
     This deletes the person object with the given ID.
     ● If the person with the given ID does not exist, return 404.
     ● Otherwise, delete the person and remove any reference of this person from your
     persistence of friendship relations, and return HTTP status code 200 and the deleted
     person in JSON.

     * @param a			Description of a
     * @param b			Description of b
     * @return			Description of c
     */

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePerson(@PathVariable("id") long id) {

        Person person = personDao.delete(id);

        if (person == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(person, HttpStatus.OK);
        }
    }

}
