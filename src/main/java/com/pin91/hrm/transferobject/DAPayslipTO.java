package com.pin91.hrm.transferobject;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DAPayslipTO {

	private Long employeeId;
	private String file;
	private Date txnDate;
	private Integer txnMonth;
	private Integer txnYear;
}
