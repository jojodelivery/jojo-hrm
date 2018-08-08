package com.pin91.hrm.persistant.domain;

import java.util.Date;

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
@Table(name = "leave_request")
public class LeaveRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "txn_month")
	private Integer txnMonth;
	@Column(name = "txn_year")
	private Integer txnYear;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column(name = "no_of_days")
	private Integer noOfDays;
	@Column(name = "reason")
	private String reason;
	@Column(name = "status")
	private String status;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "approved_by")
	private Long approveBy;
	@Column(name = "approved_date")
	private Date approveDate;
}
