package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.model.RoomReportData;
import edu.sjsu.cmpe275.project.service.ReportService;
import edu.sjsu.cmpe275.project.util.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Date;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.controller
 * Author: Scott
 * Created Date: 11/19/15 11:56 AM
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
@CrossOrigin
@Controller
@RequestMapping(value = "/api/v1/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/daily")
    public ResponseEntity<?> roomDailyReport(@RequestParam(value = "date", required = true) Date date) {

        RoomReportData result = null;

        Date today = new Date();
        int i = DateTool.compare(date, today);
        if (i == 0) {
            result = reportService.currentReport(date);
        } else if (i < 0) {
            result = reportService.postRport(date);
        } else {
            result = reportService.futureReprot(date);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
