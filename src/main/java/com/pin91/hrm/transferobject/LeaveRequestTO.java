package com.pin91.hrm.transferobject;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRequestTO {

	private Long id;
	private Long employeeId;
	private Date startDate;
	private Date endDate;
	private Integer noOfDays;
	private String reason;
	private String status;
	private Date createdDate;
	private Long approveBy;
	private Date approveDate;
}
