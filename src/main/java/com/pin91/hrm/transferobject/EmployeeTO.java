package com.pin91.hrm.transferobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeTO {

	private Long employeeId;
	private String employeeCode;
	private Long userId;
	private String name;
	private String contactNo;
	private String email;
	private Integer band;
	private String bandName;
	private String role;
	private String state;
	private Integer city;
	private Long managerId;
	private String password;
	private boolean managerAcess;
	private Long createdBy;
	private Integer shiftId;
	private String shiftName;
	private String station;
	private String managerName;
}
