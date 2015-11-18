package edu.sjsu.cmpe275.project.model;

import javax.persistence.*;
import java.util.List;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.model
 * Author: Scott
 * Created Date: 11/16/15 7:15 PM
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
@Entity
@Table(name = "ROOM")
public class Room {
    @Id
    @Column(name = "ROOM_NO", nullable = false, unique = true)
    private String roomNo;

    @Column(name = "ROOM_TYPE", nullable = false)
    private Integer roomType; //1-two queen beds, 2-one king bed

    @Column(name = "SMOKING", nullable = false)
    private Boolean smoking;

    @Column(name = "BASE_PRICE", nullable = false)
    private Integer basePrice;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "BOOKING",
            joinColumns = {@JoinColumn(name = "ROOM_NO", referencedColumnName = "ROOM_NO")},
                    inverseJoinColumns = {@JoinColumn(name = "RESERVATION_ID",referencedColumnName = "ID")})
    private List<Reservation> reservationList;

    public Room(String roomNo, Integer roomType, Boolean smoking, Integer basePrice) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.smoking = smoking;
        this.basePrice = basePrice;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Boolean getSmoking() {
        return smoking;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public Integer getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "Room{" +
                ", roomNo='" + roomNo + '\'' +
                ", roomType='" + roomType + '\'' +
                ", smoking=" + smoking +
                ", basePrice=" + basePrice +
                ", bookingList=" + reservationList +
                '}';
    }
}
