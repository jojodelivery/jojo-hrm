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
@Table(name = "da_monthly_payment")
public class DAMonthlyPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "employee_code")
	private String empoyeeName;
	@Column(name = "employee_name")
	private String empoyeeCode;
	@Column(name = "city")
	private String city;
	@Column(name = "txn_month")
	private Integer txnMonth;
	@Column(name = "txn_year")
	private Integer txnYear;
	@Column(name = "no_of_working_days")
	private Integer noOfWorkingDays;
	@Column(name = "days_worked")
	private Integer daysWorked;
	@Column(name = "no_of_day_absent")
	private Integer noOfDaysAbsent;
	@Column(name = "total_shipment_delivered")
	private Integer shipmentDelivered;
	@Column(name = "cod_delivered")
	private Integer codDelivered;
	@Column(name = "no_mpos_txn")
	private Integer mposTxn;
	@Column(name = "payable_amount")
	private BigDecimal payableAmount;
	@Column(name = "base_salary")
	private BigDecimal baseSalary;
	@Column(name = "attendance_bonus")
	private BigDecimal attendanceBonus;
	@Column(name = "no_of_days_ontime")
	private Integer noOfDaysOntime;
	@Column(name = "ontime_bonus")
	private BigDecimal ontimeBonus;
	@Column(name = "absent_penalty")
	private BigDecimal absentPenalty;
	@Column(name = "no_of_days_late")
	private BigDecimal noOfDaysLate;
	@Column(name = "late_fine")
	private BigDecimal lateFine;
	@Column(name = "delivery_incentive")
	private BigDecimal deliveryIncentive;
	@Column(name = "petrol_incentive")
	private BigDecimal petrolIncentive;
	@Column(name = "mpos_incentive")
	private BigDecimal mposIncentive;
	@Column(name = "bonus")
	private BigDecimal bonus;
	@Column(name = "base_pay_deducted_days")
	private Integer basePayDeductedDays;
	@Column(name = "base_pay_deducted_charges")
	private BigDecimal basePayDeductedCharges;
}
