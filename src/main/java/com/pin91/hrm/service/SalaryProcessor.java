package com.pin91.hrm.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.pin91.hrm.persistant.domain.City;
import com.pin91.hrm.persistant.domain.DABonus;
import com.pin91.hrm.persistant.domain.DAMonthlyPayment;
import com.pin91.hrm.persistant.domain.DailyPayment;
import com.pin91.hrm.persistant.domain.Employee;
import com.pin91.hrm.persistant.domain.FixedIncentives;
import com.pin91.hrm.persistant.domain.LeaveRequest;
import com.pin91.hrm.persistant.domain.OrgBandConfig;
import com.pin91.hrm.persistant.repository.CityRepository;
import com.pin91.hrm.persistant.repository.DABonusRepository;
import com.pin91.hrm.persistant.repository.DAMonthlyPaymentRepository;
import com.pin91.hrm.persistant.repository.DailyPaymentRepository;
import com.pin91.hrm.persistant.repository.FixedIncentivesRepository;
import com.pin91.hrm.persistant.repository.LeaveRequestRepository;
import com.pin91.hrm.persistant.repository.OrgBandConfigRepository;
import com.pin91.hrm.utils.JojoHrmUtils;
import com.pin91.hrm.utils.LeaveStatus;
import com.pin91.hrm.utils.LeaveType;

import lombok.Getter;

@Getter
public class SalaryProcessor implements Callable<String> {

	private Employee employee;
	private Integer month;
	private Integer year;
	@Autowired
	private DailyPaymentRepository dailyPaymentRepository;
	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	@Autowired
	private FixedIncentivesRepository fixedIncentivesRepository;
	@Autowired
	private OrgBandConfigRepository orgBandConfigRepository;
	@Autowired
	private DABonusRepository daBonusRepository;
	@Autowired
	private DAMonthlyPaymentRepository daMonthlyPaymentRepository;
	@Autowired
	private CityRepository cityRepository;
	
	public SalaryProcessor(Employee employee, Integer month, Integer year) {
		super();
		this.employee = employee;
		this.month = month;
		this.year = year;
	}

	@Override
	public String call() throws Exception {

		DAMonthlyPayment daMonthlyPayment = new DAMonthlyPayment();
		// Find Daily Payment
		List<DailyPayment> paymentList = dailyPaymentRepository.getDailyPayment(employee.getEmployeeId(), month, year);

		// Find Taken Leave
		List<LeaveRequest> leaveList = leaveRequestRepository.getLeaveDetails(employee.getEmployeeId(), month, year,
				LeaveStatus.APPROVED.name());
		FixedIncentives fixedIncentives = fixedIncentivesRepository.findIncentive(employee.getBand());
		OrgBandConfig bandConfig = orgBandConfigRepository.getConfigById(employee.getBand());
		City city =cityRepository.findByCityId(employee.getCity());
		
		Integer defaultWorkingDay = bandConfig.getNoOfWorkingDays();
		Integer shipmentDelivered = 0;
		Integer codDelivered = 0;
		Integer mposTxn = 0;
		BigDecimal deliveryIncentives = new BigDecimal(0);
		BigDecimal mposIncentives = new BigDecimal(0);
		BigDecimal petrolIncentives = new BigDecimal(0);
		Integer noOfDaysLate = 0;
		BigDecimal lateCharge = new BigDecimal(0);
		Double latePercentage;
		BigDecimal ontimeIncentives = new BigDecimal(0);
		BigDecimal attendanceIncentives = new BigDecimal(0);
		BigDecimal payableSalary = new BigDecimal(0);
		BigDecimal bonus = new BigDecimal(0);
		BigDecimal baseSalary = bandConfig.getBaseSalary();
		Integer noOfDays = JojoHrmUtils.noOfDaysInMonth(month, year);
		BigDecimal perDaySalary = baseSalary.divide(new BigDecimal(noOfDays));
		BigDecimal absentCharges = new BigDecimal(0);
		Integer noOfDaysAbsent = 0;
		Integer basePayDeductedDays = 0;
		BigDecimal basePayDeductedCharges = new BigDecimal(0);
		for (DailyPayment dailyPayment : paymentList) {
			shipmentDelivered = shipmentDelivered + dailyPayment.getNoOfDelivery();
			deliveryIncentives = deliveryIncentives.add(dailyPayment.getDeliveryIncentives());
			codDelivered = codDelivered + dailyPayment.getNoOfCodTxn();
			mposTxn = mposTxn + dailyPayment.getNoOfMpsTxn();
			mposIncentives = mposIncentives.add(dailyPayment.getMposCharges());
			petrolIncentives = petrolIncentives.add(dailyPayment.getPetrolCharges());
			if (dailyPayment.getLateIndicator()) {
				noOfDaysLate++;
				lateCharge = lateCharge.add(dailyPayment.getLateCharges());
			}
			if (dailyPayment.getBasePayDeduction()) {
				basePayDeductedDays++;
			}
		}

		for (LeaveRequest leaveRequest : leaveList) {
			if (!leaveRequest.getReason().equals(LeaveType.Mandatory_Leave.name())) {
				noOfDaysAbsent++;
			}
		}
		if (noOfDaysAbsent > bandConfig.getSickLeaves()) {
			Integer extraDays = noOfDaysAbsent - bandConfig.getSickLeaves();
			absentCharges = perDaySalary.multiply(new BigDecimal(extraDays));
			baseSalary = baseSalary.subtract(absentCharges);
		}
		if (paymentList.size() >= bandConfig.getNoOfWorkingDays()) {
			attendanceIncentives = fixedIncentives.getAttendanceIncentive();
			daMonthlyPayment.setAttendanceBonus(attendanceIncentives);
		}
		latePercentage = (double) ((noOfDaysLate / paymentList.size()) * 100);
		if (latePercentage > (100 - bandConfig.getOntimePercentage())) {
			ontimeIncentives = fixedIncentives.getOntimeIncentive();
			daMonthlyPayment.setOntimeBonus(ontimeIncentives);
		}
		// Get Bonus
		DABonus daBonus = daBonusRepository.getBonus(employee.getEmployeeId(), month, year);
		if (daBonus != null) {
			bonus = daBonus.getAmount();
		}
		basePayDeductedCharges = perDaySalary.multiply(new BigDecimal(basePayDeductedDays));
		payableSalary = attendanceIncentives.add(ontimeIncentives).subtract(lateCharge).add(baseSalary)
				.subtract(basePayDeductedCharges).add(petrolIncentives).add(mposIncentives).add(deliveryIncentives)
				.add(bonus);
		daMonthlyPayment.setBaseSalary(baseSalary);
		daMonthlyPayment.setEmployeeId(employee.getEmployeeId());
		daMonthlyPayment.setEmpoyeeCode(employee.getEmployeeCode());
		daMonthlyPayment.setEmpoyeeName(employee.getName());
		daMonthlyPayment.setCity(city.getCityName());
		daMonthlyPayment.setTxnMonth(month);
		daMonthlyPayment.setTxnYear(year);
		daMonthlyPayment.setNoOfWorkingDays(defaultWorkingDay - bandConfig.getMandatoryLeaves());
		daMonthlyPayment.setDaysWorked(paymentList.size());
		daMonthlyPayment.setNoOfDaysAbsent(noOfDaysAbsent);
		daMonthlyPayment.setAbsentPenalty(absentCharges);
		daMonthlyPayment.setDeliveryIncentive(deliveryIncentives);
		daMonthlyPayment.setPetrolIncentive(petrolIncentives);
		daMonthlyPayment.setMposIncentive(mposIncentives);
		daMonthlyPayment.setPayableAmount(payableSalary);
		daMonthlyPayment.setBonus(bonus);
		daMonthlyPayment.setBasePayDeductedDays(basePayDeductedDays);
		daMonthlyPayment.setBasePayDeductedCharges(basePayDeductedCharges);
		daMonthlyPaymentRepository.save(daMonthlyPayment);
		return null;
	}

}
