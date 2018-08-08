package com.pin91.hrm.persistant.domain;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "da_bonus")
public class DABonus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "txn_date")
	private Date txnDate;
	@Column(name = "txn_month")
	private Integer txnMonth;
	@Column(name = "txn_year")
	private Integer txnYear;
}
