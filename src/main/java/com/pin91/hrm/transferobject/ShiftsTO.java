package com.pin91.hrm.transferobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShiftsTO {

	private Integer shiftId;
	private String shiftName;
	private String startTime;
	private String endTime;
	private Long shiftTolerance;
}
