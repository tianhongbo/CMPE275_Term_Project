package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        String page = "<html><body>"
                + "<h3>Dear Customer,</h3>"
                + "<h3>Congratulation! Your room reservation has been confirmed, and the reservation ID is: " + reservation.getId() + "</h3>"
                + "<h3>If you want to cancel this reservation, please click the following link:</h3>"
                + "<a href='http://localhost:8080/api/v1/cancel/reservation/" + reservation.getId() + "'>Cancel Reservation</a>"
                + "<h3>Sincerely</h3>"
                + "<h3>CMPE275 Mini Hotel Team</h3>"
                + "</body></html>";
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(reservation.getEmail());
            helper.setReplyTo("cmpe275.mini.hotel@gmail.com");
            helper.setFrom("cmpe275.mini.hotel@gmail.com");
            helper.setSubject("Your hotel reservation has been confirmed");
            helper.setText(page, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return;
    }

    public void sentBill(Reservation reservation) {
        //TODO
        return;
    }
}
