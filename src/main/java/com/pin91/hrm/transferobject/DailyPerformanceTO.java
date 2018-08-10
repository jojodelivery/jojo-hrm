package com.pin91.hrm.transferobject;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyPerformanceTO {

	private Long id;
	private Long employeeId;
	private String employeeName;
	private Date txnDate;
	private String inTime;
	private String outTime;
	private String hoursWorked;
	private Integer noOfShipment;
	private Integer noOfCodDelivered;
	private Integer noOfMposTxn;
	private String status;
	private Date createdDate;
	private Long approveBy;
	private Date approveDate;
}