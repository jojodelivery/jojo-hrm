package com.pin91.hrm.service;

import java.util.List;

import com.pin91.hrm.exception.JojoHRMException;
import com.pin91.hrm.transferobject.EmployeeTO;

public interface IUserService {

	public Long saveEmployee(EmployeeTO employee) throws JojoHRMException;

	public EmployeeTO validateUser(String username, String password) throws JojoHRMException;

	public EmployeeTO getEmployeeByEmployeeId(Long empId);

	public List<EmployeeTO> getEmployeeByCity(Integer city);

	public List<EmployeeTO> getEmployeeByState(String state);

	public List<EmployeeTO> getManager();

	public boolean updateEmployee(Long employeeId);
}
