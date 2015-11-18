package edu.sjsu.cmpe275.project.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.model
 * Author: Scott
 * Created Date: 11/16/15 8:45 PM
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
@Embeddable
public class Name {
    @Column(name = "FNAME")
    private String fname;

    @Column(name = "MIDNAME")
    private String midname;

    @Column(name = "LNAME")
    private String lname;

    public Name() {
    }

    public Name(String fname, String midname, String lname) {
        this.fname = fname;
        this.midname = midname;
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Override
    public String toString() {
        return "Name{" +
                "fname='" + fname + '\'' +
                ", midname='" + midname + '\'' +
                ", lname='" + lname + '\'' +
                '}';
    }
}
