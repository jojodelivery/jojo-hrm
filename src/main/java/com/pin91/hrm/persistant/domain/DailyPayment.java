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
@Table(name = "daily_payment")
public class DailyPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "band_config_id")
	private Integer configId;
	@Column(name = "txn_date")
	private Date txnDate;
	@Column(name = "txn_month")
	private Integer txnMonth;
	@Column(name = "txn_year")
	private Integer txnYear;
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "station")
	private String station;
	@Column(name = "in_time")
	private String inTime;
	@Column(name = "out_time")
	private String outTime;
	@Column(name = "hours_worked")
	private String hoursWorked;
	@Column(name = "base_pay_deduction")
	private Boolean basePayDeduction;
	@Column(name = "late_indicator")
	private Boolean lateIndicator;
	@Column(name = "late_charges")
	private BigDecimal lateCharges;
	@Column(name = "no_of_delivery")
	private Integer noOfDelivery;
	@Column(name = "delivery_incentives")
	private BigDecimal deliveryIncentives;
	@Column(name = "petrol_charges")
	private BigDecimal petrolCharges;
	@Column(name = "no_of_cod_txn")
	private Integer noOfCodTxn;
	@Column(name = "no_of_mpos_txn")
	private Integer noOfMpsTxn;
	@Column(name = "mpos_charges")
	private BigDecimal mposCharges;
	@Column(name = "bonus_incentives")
	private BigDecimal bonus;

}
