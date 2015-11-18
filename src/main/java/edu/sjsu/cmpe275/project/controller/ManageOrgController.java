package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.dao.OrgDao;
import edu.sjsu.cmpe275.project.model.Address;
import edu.sjsu.cmpe275.project.model.Organization;
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
 * Created Date: 10/29/15 5:41 PM
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
@RequestMapping("/org")
public class ManageOrgController {
    @Autowired
    OrgDao orgDao;

    /** (5) Create an organization object
     *
     * Method: POST
     * This API creates an organization object.
     * For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query
     * parameters. Only name is required.
     * The request returns the newly created organization object in JSON in its HTTP payload, including all attributes.
     * (Please note this differs from generally recommended practice of only returning the ID.)
     * If the request is invalid, e.g., missing required parameters, the HTTP status code should be 400; otherwise 200.
     *
     * @param name			Name of Organization
     * @param description	Brief description of organization
     * @param street	    Address of Organization
     * @param city			Address of Organization
     * @param state			Address of Organization
     * @param zip		    Address of Organization
     * @return			    Created Organization
     */

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity createOrganization(@RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "description", required = false) String description,
                                     @RequestParam(value = "street", required = false) String street,
                                     @RequestParam(value = "city", required = false) String city,
                                     @RequestParam(value = "state", required = false) String state,
                                     @RequestParam(value = "zip", required = false) String zip) {

        Organization organization = new Organization();
        organization.setAddress(new Address(street,city,state,zip));
        organization.setDescription(description);
        organization.setName(name);

        orgDao.create(organization);

        if(organization == null) {
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(organization, HttpStatus.OK);
        }
    }

    /** (6) Get a organization<br>
     Path:org/{id}?format={json | xml | html} <br>
     Method: GET <br>
     This returns a full organization object with the given ID in the given format.
     All existing fields, including the optional organization and list of friends should be returned.
     If the organization of the given user ID does not exist, the HTTP return code should be 404; otherwise, 200.
     The format parameter is optional, and the value is case insensitive. If missing, JSON is assumed.

     * @param id			Description of a
     * @return			Description of c
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getOrganizationJson(@PathVariable("id") long id) {

        Organization organization = orgDao.get(id);
        if (organization == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(organization, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = "format=xml",produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity getOrganizationXml(@PathVariable("id") long id) {

        Organization organization = orgDao.get(id);
        if (organization == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(organization, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = "format=html",produces = MediaType.TEXT_HTML_VALUE)
    public String getOrganizationHtml(@PathVariable("id") long id, Model model) {
        Organization organization = orgDao.get(id);
        if (organization == null) {
            return "error404";
        } else {
            model.addAttribute("org", organization);
            return "org";
        }
    }



    /** Function and Requirement:
     (7) Update an organization
     Path: org/{id}?name=XX description=YY street=ZZ ...
     Method: POST

     This API updates an organization object.
     For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query
     parameters. Only name is required.
     Similar to the get method, the request returns the updated organization object, including all attributes in JSON.
     If the organization ID does not exist, 404 should be returned. If required parameters are missing,
     return 400 instead. Otherwise, return 200.

     * @param id			Description of a
     * @param name 		Description of b
     * @param description			Description of a
     * @param street	    Address of Organization
     * @param city			Address of Organization
     * @param state			Address of Organization
     * @param zip		    Address of Organization
     * @return			Description of c
     */

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    public ResponseEntity updateOrganization(@PathVariable("id") long id,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "description", required = false) String description,
                                     @RequestParam(value = "street", required = false) String street,
                                     @RequestParam(value = "city", required = false) String city,
                                     @RequestParam(value = "state", required = false) String state,
                                     @RequestParam(value = "zip", required = false) String zip) {

        Organization organization = new Organization();
        organization.setId(id);
        organization.setDescription(description);
        organization.setName(name);
        if(street != null || city != null || state != null || zip != null) {
            organization.setAddress(new Address(street, city, state, zip));
        }

        organization = orgDao.update(organization);

        if (organization == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(organization, HttpStatus.OK);
        }

    }

    /** Delete an organization object
     (8) Delete an organization
     URL: http://org/{id}
     Method: DELETE

     This method deletes the organization object with the given ID.
     If there is still any person belonging to this organization, return 400.
     If the organization with the given ID does not exist, return 404.
     Return HTTP code 200 and the deleted object in JSON if the object is deleted;

     * @param id			Organization id
     * @return			Description of c
     */

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrganization(@PathVariable("id") long id) {

        Organization organization = orgDao.get(id);
        if (organization == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            organization = orgDao.delete(id);
            if (organization == null) {
                return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity(organization, HttpStatus.OK);
            }
        }
    }

}
