package edu.sjsu.cmpe275.project.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Project Name: CMPE275_Lab
 * Packet Name: edu.sjsu.cmpe275.project.model
 * Author: Scott
 * Created Date: 11/16/15 8:43 PM
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
@Table(name = "RESERVATION")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Embedded
    private Name name;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "DL_NO", nullable = false)
    private String dlNo;

    @Embedded
    private Address billingAddress;

    @Column(name = "CHECKIN_DATE")
    private Date checkinDate;

    @Column(name = "CHECKOUT_DATE")
    private Date checkoutDate;

    @Column(name = "STATUS")
    private String status; //1(reserved), 2(canceled), 3(checked in), 4(checked out)

    @ManyToMany(mappedBy = "reservationList", fetch = FetchType.EAGER)
    private List<Room> roomList;

    public Reservation() {
    }

    public Reservation(Name name, String email, String dlNo, Address billingAddress, Date checkinDate, Date checkoutDate, String status, List<Room> roomList) {
        this.name = name;
        this.email = email;
        this.dlNo = dlNo;
        this.billingAddress = billingAddress;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.status = status;
        this.roomList = roomList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDlNo() {
        return dlNo;
    }

    public void setDlNo(String dlNo) {
        this.dlNo = dlNo;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name=" + name +
                ", email='" + email + '\'' +
                ", dlNo='" + dlNo + '\'' +
                ", billingAddress=" + billingAddress +
                ", checkinDate=" + checkinDate +
                ", checkoutDate=" + checkoutDate +
                ", status='" + status + '\'' +
                ", bookingList=" + roomList +
                '}';
    }
}
