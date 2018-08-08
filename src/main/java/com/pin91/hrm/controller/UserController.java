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

import com.pin91.hrm.exception.JojoHRMException;
import com.pin91.hrm.service.IUserService;
import com.pin91.hrm.transferobject.EmployeeTO;
import com.pin91.hrm.transferobject.ViewEmployeeTO;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserService iUserService;

	@RequestMapping(value = "/employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addEmployee(@RequestBody EmployeeTO employee) throws JojoHRMException {

		Long userId = iUserService.saveEmployee(employee);
		return new ResponseEntity<Long>(userId, HttpStatus.OK);
	}

	@RequestMapping(value = "/manager", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getManager() {

		List<EmployeeTO> employee = iUserService.getManager();
		return new ResponseEntity<List<EmployeeTO>>(employee, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/{empId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEmployee(@PathVariable("empId") final Long empId) {

		EmployeeTO employee = iUserService.getEmployeeByEmployeeId(empId);
		return new ResponseEntity<EmployeeTO>(employee, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/{city}", method = RequestMethod.GET)
	public ResponseEntity<?> getEmployeeByCity(@PathVariable("city") final Integer city) {

		List<EmployeeTO> employeeList = iUserService.getEmployeeByCity(city);
		ViewEmployeeTO viewEmployee = new ViewEmployeeTO();
		viewEmployee.setAaData(employeeList);
		viewEmployee.setITotalRecords(employeeList.size());
		viewEmployee.setITotalDisplayRecords(employeeList.size());
		return new ResponseEntity<ViewEmployeeTO>(viewEmployee, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/{state}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEmployeeByState(@PathVariable("state") final String state) {

		List<EmployeeTO> employee = iUserService.getEmployeeByState(state);
		return new ResponseEntity<List<EmployeeTO>>(employee, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/{empId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateEmployee(@PathVariable("empId") final Long empId) {

		boolean result = iUserService.updateEmployee(empId);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

}
