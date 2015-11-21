package edu.sjsu.cmpe275.project.util;

import edu.sjsu.cmpe275.project.model.Reservation;
import edu.sjsu.cmpe275.project.model.Room;
import org.springframework.scheduling.annotation.Async;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.util
 * Author: Scott
 * Created Date: 11/20/15 6:54 PM
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
public class HtmlPageComposor {

    public static String reservationConfirmation(Reservation reservation) {
        String page = "<html><body>"
                + "<h3>Dear Customer,</h3>"
                + "<h3>Congratulation! Your room reservation has been confirmed, and the reservation ID is: " + reservation.getId() + "</h3>"
                + "<h3>If you want to cancel this reservation, please click the following link:</h3>"
                + "<a href='http://localhost:8080/api/v1/reservations/" + reservation.getId() + "/cancel'>Click here to cancel your reservation</a>"
                + "<h3>Sincerely</h3>"
                + "<h3>CMPE275 Mini Hotel Team</h3>"
                + "</body></html>";
        return page;
    }

    public static String orderReceipt(Reservation reservation) {
        //TODO: discount + total fee
        String bill = "";
        int total = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY");
        Date date = reservation.getCheckinDate();
        bill = "<table border='1' style='width:100%'>";
        bill += "<tr>";
        bill += "<td>Date</td>";
        bill += "<td>Room No</td>";
        bill += "<td>Base Price</td>";
        bill += "<td>Discount(%)</td>";
        bill += "<td>Final Fee</td>";
        bill += "</tr>";
        while (date.before(reservation.getCheckoutDate())) {

            for (Room room: reservation.getRoomList()) {
                bill += "<tr>";
                bill += "<td>" + sdf.format(date) + "</td>";
                bill += "<td>" + room.getRoomNo() + "</td>";
                bill += "<td>" + room.getBasePrice() + "</td>";
                bill += "<td>" + reservation.getDiscount() + "</td>";
                int fee = room.getBasePrice()*(100-reservation.getDiscount())/100;
                total += fee;
                bill += "<td>" + fee + "</td>";
                bill += "</tr>";
            }
            //increase one day
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            date = c.getTime();
        }
        bill += "</table>";
        bill += "<h3 align='right'>Total($): " + total + "</h3>";

        String page = "<html><body>"
                + "<h3>Dear "+ reservation.getName().getFname() + ",</h3>"
                + "<h3>Thank you for choosing us serving you! Your room(s) have been checked out.</h3>"
                + "<h3>Here is the detail of bill:</h3>"
                + bill
                + "<h3>Regards,</h3>"
                + "<h3>CMPE275 Mini Hotel Team</h3>"
                + "</body></html>";
        return page;
    }

}
