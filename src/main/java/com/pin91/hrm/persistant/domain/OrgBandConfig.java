package com.pin91.hrm.persistant.domain;

import java.math.BigDecimal;

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
@Table(name = "org_band_config")
public class OrgBandConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "band_id")
	private Integer bandId;
	@Column(name = "band_name")
	private String bandName;
	@Column(name = "city_id")
	private Integer cityId;
	@Column(name = "base_salary")
	private BigDecimal baseSalary;
	@Column(name = "sick_leaves")
	private Integer sickLeaves;
	@Column(name = "personal_leaves")
	private Integer personalLeaves;
	@Column(name = "max_permitted_leaves")
	private Integer permittedLeaves;
	@Column(name = "mandatory_leaves")
	private Integer mandatoryLeaves;
	@Column(name = "no_of_working_days")
	private Integer noOfWorkingDays;
	@Column(name = "ontime_percentage")
	private Integer ontimePercentage;
}
