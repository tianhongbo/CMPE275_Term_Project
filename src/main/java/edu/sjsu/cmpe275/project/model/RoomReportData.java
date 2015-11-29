package edu.sjsu.cmpe275.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.model
 * Author: Scott
 * Created Date: 11/18/15 2:01 PM
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
public class RoomReportData {

    @JsonProperty(value = "in_service")
    private int numOfInservice;
    @JsonProperty(value = "vacant")
    private int numOfVacant;
    @JsonProperty(value = "under_reservation")
    private int numOfReserved;
    @JsonProperty(value = "available")
    private int numOfAvailable;

    public RoomReportData() {
    }

    public RoomReportData(int numOfReserved, int numOfInservice, int numOfVacant, int numOfAvailable) {
        this.numOfReserved = numOfReserved;
        this.numOfInservice = numOfInservice;
        this.numOfVacant = numOfVacant;
        this.numOfAvailable = numOfAvailable;
    }

    public int getNumOfInservice() {
        return numOfInservice;
    }

    public void setNumOfInservice(int numOfInservice) {
        this.numOfInservice = numOfInservice;
    }

    public int getNumOfVacant() {
        return numOfVacant;
    }

    public void setNumOfVacant(int numOfVacant) {
        this.numOfVacant = numOfVacant;
    }

    public int getNumOfReserved() {
        return numOfReserved;
    }

    public void setNumOfReserved(int numOfReserved) {
        this.numOfReserved = numOfReserved;
    }

    public int getNumOfAvailable() {
        return numOfAvailable;
    }

    public void setNumOfAvailable(int numOfAvailable) {
        this.numOfAvailable = numOfAvailable;
    }

    @Override
    public String toString() {
        return "RoomReportData{" +
                "numOfInservice=" + numOfInservice +
                ", numOfVacant=" + numOfVacant +
                ", numOfReserved=" + numOfReserved +
                ", numOfAvailable=" + numOfAvailable +
                '}';
    }
}
