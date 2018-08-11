package com.pin91.hrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pin91.hrm.service.IReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private IReportService iReportService;

	@RequestMapping(value = "/monthly/{station}/{month}/{year}", method = RequestMethod.GET)
	public ResponseEntity<?> getMonthlyReport(@PathVariable("station") final String station,
			@PathVariable("month") final Integer month, @PathVariable("year") final Integer year) {

		String fileName = iReportService.getMonthlyReport(station, month, year);
		return new ResponseEntity<String>(fileName, HttpStatus.OK);
	}

	@RequestMapping(value = "/daily/{station}/{month}/{year}", method = RequestMethod.GET)
	public ResponseEntity<?> getDailyReport(@PathVariable("station") final String station,
			@PathVariable("month") final Integer month, @PathVariable("year") final Integer year) {

		String fileName = iReportService.getDailyReport(station, month, year);
		return new ResponseEntity<String>(fileName, HttpStatus.OK);
	}
}
