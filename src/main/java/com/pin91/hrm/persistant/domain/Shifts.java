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
@Table(name = "shift_details")
public class Shifts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shift_id")
	private Integer shiftId;
	@Column(name = "shift_name")
	private String shiftName;
	@Column(name = "start_time")
	private String startTime;
	@Column(name = "end_time")
	private String endTime;
	@Column(name = "shift_tolerance")
	private Long shiftTolerance;
}
