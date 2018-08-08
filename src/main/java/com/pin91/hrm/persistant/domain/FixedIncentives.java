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
@Table(name = "fixed_incentives_config")
public class FixedIncentives {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "band_config_id")
	private Integer configId;
	@Column(name = "full_attendance")
	private BigDecimal attendanceIncentive;
	@Column(name = "ontime_attendance")
	private BigDecimal ontimeIncentive;
}
