package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Reservation;

import java.util.List;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.dao
 * Author: Scott
 * Created Date: 11/17/15 11:46 PM
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
public interface ReservationDao {
    public Reservation addReservation(Reservation reservation);
    public List<Reservation> getAllReservation();
    public Reservation getReservation(Long id);
    public Reservation cancelReservation(Long id);
    public Reservation updateReservation(Reservation reservation);
}
