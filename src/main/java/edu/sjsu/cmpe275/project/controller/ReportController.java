package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
@RequestMapping(value = "/api/v1/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/{date}")
    public ResponseEntity<?> roomDailyReport(@PathVariable("date")Date date) {

        return new ResponseEntity(reportService.currentReport(date), HttpStatus.OK);
    }
}
