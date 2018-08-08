package com.pin91.hrm.transferobject;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewEmployeeTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int iTotalRecords;
	private List<EmployeeTO> aaData;
	private int iTotalDisplayRecords;
	private int sEcho;
}
