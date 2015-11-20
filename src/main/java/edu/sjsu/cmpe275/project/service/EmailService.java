package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Reservation;
import edu.sjsu.cmpe275.project.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.service
 * Author: Scott
 * Created Date: 11/19/15 9:41 AM
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
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmation(Reservation reservation) {
        String page = "<html><body>"
                + "<h3>Dear Customer,</h3>"
                + "<h3>Congratulation! Your room reservation has been confirmed, and the reservation ID is: " + reservation.getId() + "</h3>"
                + "<h3>If you want to cancel this reservation, please click the following link:</h3>"
                + "<a href='http://localhost:8080/api/v1/cancel/reservation/" + reservation.getId() + "'>Cancel Reservation</a>"
                + "<h3>Sincerely</h3>"
                + "<h3>CMPE275 Mini Hotel Team</h3>"
                + "</body></html>";
        send(reservation, page);
        return;
    }

    public void sentReceipt(Reservation reservation) {
        //TODO: discount + total fee
        String bill = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY");
        Date date = reservation.getCheckinDate();
        bill += "<table border='1' style='width:100%'>";
        bill += "<tr>";
        bill += "<td>Date</td>";
        bill += "<td>Room No</td>";
        bill += "<td>Base Price</td>";
        bill += "<td>Discount</td>";
        bill += "<td>Final Fee</td>";
        bill += "</tr>";
        while (date.before(reservation.getCheckoutDate())) {

            for (Room room: reservation.getRoomList()) {
                bill += "<tr>";
                bill += "<td>" + sdf.format(date) + "</td>";
                bill += "<td>" + room.getRoomNo() + "</td>";
                bill += "<td>" + room.getBasePrice() + "</td>";
                bill += "<td>" + room.getStatus() + "</td>";
                bill += "<td>" + room.getBasePrice() + "</td>";
                bill += "</tr>";
            }
            //increase one day
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            date = c.getTime();
        }
        bill += "</table>";

        String page = "<html><body>"
                + "<h3>Dear "+ reservation.getName().getFname() + ",</h3>"
                + "<h3>Thanks for your choosing us serving you! Your room(s) have been checked out.</h3>"
                + "<h3>Here is the detail of bill:</h3>"
                + bill
                + "<h3>Regards,</h3>"
                + "<h3>CMPE275 Mini Hotel Team</h3>"
                + "</body></html>";
        send(reservation, page);
        return;
    }

    private void send(Reservation reservation, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(reservation.getEmail());
            helper.setReplyTo("cmpe275.mini.hotel@gmail.com");
            helper.setFrom("cmpe275.mini.hotel@gmail.com");
            helper.setSubject("Your hotel reservation has been confirmed");
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return;
    }

}
