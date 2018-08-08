package com.pin91.hrm.transferobject;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewLeaveRequestTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int iTotalRecords;
	private List<LeaveRequestTO> aaData;
	private int iTotalDisplayRecords;
	private int sEcho;
}
