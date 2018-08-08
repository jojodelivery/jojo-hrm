package com.pin91.hrm.transferobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgBandConfigTO {

	private Integer bandId;
	private String bandName;
	private Integer noticePeriod;
	private Integer incrementedLeaves;
	private Integer sickLeaves;
	private Integer personalLeaves;
	private Integer permittedLeaves;
}
