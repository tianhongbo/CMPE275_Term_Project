package edu.sjsu.cmpe275.project.model;

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
    private int numOfInservice;
    private int numOfVacant;
    private int numOfReserved;

    public RoomReportData() {
    }

    public RoomReportData(int numOfReserved, int numOfInservice, int numOfVacant) {
        this.numOfReserved = numOfReserved;
        this.numOfInservice = numOfInservice;
        this.numOfVacant = numOfVacant;
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

    @Override
    public String toString() {
        return "RoomReportData{" +
                "numOfInservice=" + numOfInservice +
                ", numOfVacant=" + numOfVacant +
                ", numOfReserved=" + numOfReserved +
                '}';
    }
}
