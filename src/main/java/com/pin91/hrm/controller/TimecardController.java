package com.pin91.hrm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pin91.hrm.service.ITimecardService;
import com.pin91.hrm.transferobject.DailyPerformanceTO;
import com.pin91.hrm.transferobject.KeyValueResponse;
import com.pin91.hrm.transferobject.LeaveRequestTO;
import com.pin91.hrm.transferobject.ViewLeaveRequestTO;
import com.pin91.hrm.transferobject.ViewTimecardTO;
import com.pin91.hrm.utils.LeaveStatus;

@RestController
@RequestMapping("/timecard")
public class TimecardController {

	@Autowired
	private ITimecardService iTimecardService;

	@RequestMapping(value = "/leave", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> applyLeave(@RequestBody LeaveRequestTO leaveRequest) {

		Long requestId = iTimecardService.applyLeave(leaveRequest);
		return new ResponseEntity<Long>(requestId, HttpStatus.OK);
	}

	@RequestMapping(value = "/leave/{employeeId}", method = RequestMethod.GET)
	public ResponseEntity<?> viewLeave(@PathVariable("employeeId") final Long employeeId) {

		List<LeaveRequestTO> leaveList = iTimecardService.viewLeaves(employeeId);
		ViewLeaveRequestTO viewLeave = new ViewLeaveRequestTO();
		viewLeave.setAaData(leaveList);
		viewLeave.setITotalRecords(15);
		viewLeave.setITotalDisplayRecords(15);
		return new ResponseEntity<ViewLeaveRequestTO>(viewLeave, HttpStatus.OK);
	}

	@RequestMapping(value = "/leave/{requestId}/{managerId}/{status}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateLeave(@PathVariable("requestId") final Long requestId,
			@PathVariable("managerId") final Long managerId, @PathVariable("status") final String status) {

		Boolean result = iTimecardService.updateLeave(requestId, managerId, status);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);

	}

	@RequestMapping(value = "/leave/{requestId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cancelLeave(@PathVariable("requestId") final Long requestId) {

		Boolean result = iTimecardService.updateLeave(requestId, null, LeaveStatus.CANCELLED.toString());
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);

	}

	@RequestMapping(value = "/manager-view-leave/{managerId}", method = RequestMethod.GET)
	public ResponseEntity<?> viewManagerLeave(@PathVariable("managerId") final Long managerId) {

		List<LeaveRequestTO> leaveList = iTimecardService.viewLeaveByManager(managerId);
		ViewLeaveRequestTO viewLeave = new ViewLeaveRequestTO();
		viewLeave.setAaData(leaveList);
		viewLeave.setITotalRecords(15);
		viewLeave.setITotalDisplayRecords(15);
		return new ResponseEntity<ViewLeaveRequestTO>(viewLeave, HttpStatus.OK);
	}

	@RequestMapping(value = "/timecard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveTimecard(@RequestBody DailyPerformanceTO dailyPerformance) {

		Long requestId = iTimecardService.saveTimecard(dailyPerformance);
		return new ResponseEntity<Long>(requestId, HttpStatus.OK);
	}

	@RequestMapping(value = "/timecard/{managerId}/{status}", method = RequestMethod.GET)
	public ResponseEntity<?> managerPendingTimecard(@PathVariable("managerId") final Long managerId,
			@PathVariable("status") final String status) {

		ViewTimecardTO viewTimecard = new ViewTimecardTO();
		List<DailyPerformanceTO> performanceList = iTimecardService.getPendingTimecard(managerId, status);
		viewTimecard.setAaData(performanceList);
		viewTimecard.setITotalRecords(performanceList.size());
		viewTimecard.setITotalDisplayRecords(performanceList.size());
		return new ResponseEntity<ViewTimecardTO>(viewTimecard, HttpStatus.OK);

	}

	@RequestMapping(value = "/timecard/{employeeId}/{month}/{year}", method = RequestMethod.GET)
	public ResponseEntity<?> getTimecard(@PathVariable("employeeId") final Long employeeId,
			@PathVariable("month") final Integer month, @PathVariable("year") final Integer year) {

		ViewTimecardTO viewTimecard = new ViewTimecardTO();
		List<DailyPerformanceTO> performanceList = iTimecardService.getViewTimecard(employeeId, month, year);
		viewTimecard.setAaData(performanceList);
		viewTimecard.setITotalRecords(performanceList.size());
		viewTimecard.setITotalDisplayRecords(performanceList.size());
		return new ResponseEntity<ViewTimecardTO>(viewTimecard, HttpStatus.OK);
	}

	@RequestMapping(value = "/approve-timecard/{requestId}/{managerId}/{status}/{rejectReason}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTimecard(@PathVariable("employeeId") final Long employeeId,
			@PathVariable("requestId") final long requestId, @PathVariable("managerId") final Long managerId,
			@PathVariable("status") final String status, @PathVariable("rejectReason") final String rejectReason) {

		boolean result = iTimecardService.updateTimecard(requestId, managerId, status, rejectReason);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/generatePayslips", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> generatePayslips(final Integer employeeId) {
		boolean result = false;
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	// Unimplemented methods

	@RequestMapping(value = "/associate-da-worksheet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWorksheet(final Integer employeeId) {
		KeyValueResponse response = new KeyValueResponse();
		response.setValue("success");
		return new ResponseEntity<KeyValueResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/payslips/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPayslip(@PathVariable("employeeId") final Integer id) {
		KeyValueResponse response = new KeyValueResponse();
		response.setValue("success");
		return new ResponseEntity<KeyValueResponse>(response, HttpStatus.OK);
	}

}
