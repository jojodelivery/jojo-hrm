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
@Table(name = "daily_performance")
public class DailyPerformance {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "txn_date")
	private Date txnDate;
	@Column(name = "txn_month")
	private Integer txnMonth;
	@Column(name = "txn_year")
	private Integer txnYear;
	@Column(name = "in_time")
	private String inTime;
	@Column(name = "out_time")
	private String outTime;
	@Column(name = "hours_worked")
	private String hoursWorked;
	@Column(name = "no_of_shipment_delivered")
	private Integer noOfShipment;
	@Column(name = "no_of_cod_delivered")
	private Integer noOfCodDelivered;
	@Column(name = "no_of_mpos_transaction")
	private Integer noOfMposTxn;
	@Column(name = "status")
	private String status;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "approved_by")
	private Long approveBy;
	@Column(name = "approved_date")
	private Date approveDate;
	@Column(name = "reject_reason")
	private String rejectReason;
}