package com.pin91.hrm.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
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

	@Value("${report.location}")
	private String reportLocation;

	@Value("${payslip.location}")
	private String payslipLocation;

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

	@RequestMapping(path = "/download/{fileName}/{fileType}", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @PathVariable("fileName") final String fileName,
			@PathVariable("fileType") final String fileType) throws IOException {

		File file = null;

		if (fileType.equals("payslip")) {
			file = new File(payslipLocation + fileName);
		} else {
			file = new File(reportLocation + fileName);
		}
		if (!file.exists()) {
			String errorMessage = "Sorry. The file you are looking for does not exist";
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		// Copy bytes from source to destination(outputstream in this example), closes
		// both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

}
