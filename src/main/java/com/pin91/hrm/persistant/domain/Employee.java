package com.pin91.hrm.persistant.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "employee_code")
	private String employeeCode;
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "name")
	private String name;
	@Column(name = "contact_no")
	private String contactNo;
	@Column(name = "email")
	private String email;
	@Column(name = "band")
	private Integer band;
	@Column(name = "role")
	private String role;
	@Column(name = "state")
	private String state;
	@Column(name = "city")
	private Integer city;
	@Column(name = "manager_id")
	private Long managerId;
	@Column(name = "shift")
	private Integer shiftId;
	@Column(name = "status")
	private String status;
	@Column(name = "manager_access")
	private boolean managerAcess;
	@Column(name = "station")
	private String station;
}
