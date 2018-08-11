package com.pin91.hrm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pin91.hrm.exception.JojoHRMException;
import com.pin91.hrm.persistant.domain.DAPayslip;
import com.pin91.hrm.persistant.domain.DailyPayment;
import com.pin91.hrm.persistant.domain.DailyPerformance;
import com.pin91.hrm.persistant.domain.DeliveryIncentiveConfig;
import com.pin91.hrm.persistant.domain.Employee;
import com.pin91.hrm.persistant.domain.LeaveRequest;
import com.pin91.hrm.persistant.domain.MposConfig;
import com.pin91.hrm.persistant.domain.PenaltyConfig;
import com.pin91.hrm.persistant.domain.Shifts;
import com.pin91.hrm.persistant.repository.CityRepository;
import com.pin91.hrm.persistant.repository.DABonusRepository;
import com.pin91.hrm.persistant.repository.DAMonthlyPaymentRepository;
import com.pin91.hrm.persistant.repository.DAPayslipRespository;
import com.pin91.hrm.persistant.repository.DailyPaymentRepository;
import com.pin91.hrm.persistant.repository.DailyPerformanceRepository;
import com.pin91.hrm.persistant.repository.DeliveryIncentiveRepository;
import com.pin91.hrm.persistant.repository.EmployeeRepository;
import com.pin91.hrm.persistant.repository.FixedIncentivesRepository;
import com.pin91.hrm.persistant.repository.LeaveRequestRepository;
import com.pin91.hrm.persistant.repository.MposConfigRepository;
import com.pin91.hrm.persistant.repository.OrgBandConfigRepository;
import com.pin91.hrm.persistant.repository.PenaltyConfigRepository;
import com.pin91.hrm.persistant.repository.ShiftsRepository;
import com.pin91.hrm.transferobject.DAPayslipTO;
import com.pin91.hrm.transferobject.DailyPerformanceTO;
import com.pin91.hrm.transferobject.LeaveRequestTO;
import com.pin91.hrm.utils.JojoErrorCode;
import com.pin91.hrm.utils.JojoHrmUtils;
import com.pin91.hrm.utils.LeaveStatus;
import com.pin91.hrm.utils.TimecardStatus;
import com.pin91.hrm.utils.UserStatus;

@Service
public class TimecardServiceImpl implements ITimecardService {

	@Autowired
	@Qualifier("mapper")
	private Mapper mapper;

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DailyPerformanceRepository dailyPerformanceRepository;
	@Autowired
	private ShiftsRepository shiftsRepository;
	@Autowired
	private PenaltyConfigRepository penaltyConfigRepository;
	@Autowired
	private DeliveryIncentiveRepository deliveryIncentiveRepository;
	@Autowired
	private DailyPaymentRepository dailyPaymentRepository;
	@Autowired
	private MposConfigRepository mposConfigRepository;
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
	@Autowired
	private DAPayslipRespository daPayslipRespository;

	@Value("${payslip.location}")
	private String payslipLocation;
	@Value("${server.name}")
	private String serverName;

	ExecutorService executor = Executors.newFixedThreadPool(10);

	@Override
	public Long applyLeave(LeaveRequestTO request) {
		request.setStatus(LeaveStatus.REQUESTED.toString());
		request.setCreatedDate(JojoHrmUtils.currentDate());
		LeaveRequest leaveRequest = mapper.map(request, LeaveRequest.class);
		leaveRequest.setTxnMonth(JojoHrmUtils.currentMonth());
		leaveRequest.setTxnYear(JojoHrmUtils.currentYear());
		leaveRequestRepository.save(leaveRequest);
		return leaveRequest.getId();
	}

	@Override
	public List<LeaveRequestTO> viewLeaves(Long employeeId) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(0, 15);
		List<LeaveRequest> leaveList = leaveRequestRepository.getLeaveDetails(employeeId, pageable);
		return leaveList.stream().map(leaveRequest -> mapper.map(leaveRequest, LeaveRequestTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public boolean updateLeave(Long requestId, Long managerId, String status) {

		LeaveRequest leaveRequest = leaveRequestRepository.getLeaveDetail(requestId);
		leaveRequest.setStatus(status);
		if (status.equals(LeaveStatus.APPROVED.toString())) {
			leaveRequest.setApproveBy(managerId);
			leaveRequest.setApproveDate(JojoHrmUtils.currentDate());
		}
		leaveRequestRepository.save(leaveRequest);
		return true;

	}

	@SuppressWarnings("deprecation")
	@Override
	public List<LeaveRequestTO> viewLeaveByManager(Long managerId) {
		// Find Associates related to manager
		List<Employee> empList = employeeRepository.getEmployeeByManager(managerId);
		Pageable pageable = new PageRequest(0, 15);
		// List<LeaveRequestTO> leaveDetails;
		// Find their leave details
		List<LeaveRequestTO> leaveDetails = new ArrayList<>();
		for (Employee employee : empList) {
			List<LeaveRequest> leaves = leaveRequestRepository.getLeavesByStatus(employee.getEmployeeId(),
					LeaveStatus.REQUESTED.toString(), pageable);
			List<LeaveRequestTO> leaveList = leaves.stream()
					.map(leaveRequest -> mapper.map(leaveRequest, LeaveRequestTO.class)).collect(Collectors.toList());
			leaveDetails.addAll(leaveList);
		}
		return leaveDetails;
	}

	@Override
	public Long saveTimecard(DailyPerformanceTO dailyPerformance) {

		// Check if Timecard Exist for that Day
		DailyPerformance performance = dailyPerformanceRepository.getDailyPerformance(dailyPerformance.getEmployeeId(),
				dailyPerformance.getTxnDate());
		if (performance != null) {
			throw new JojoHRMException(JojoErrorCode.TIMECARD_EXIST,
					"Transaction already exist for the day: " + dailyPerformance.getTxnDate());
		}
		//TODO
		// Check if Leave is applied for that day
		LeaveRequest leaveRequest = leaveRequestRepository.leaveDetailsByDate(dailyPerformance.getEmployeeId(),
				dailyPerformance.getTxnDate(), LeaveStatus.APPROVED.name());
		if (leaveRequest != null) {
			throw new JojoHRMException(JojoErrorCode.LEAVE_EXIST, "DA is on leave the day: "
					+ dailyPerformance.getTxnDate() + ".Please cancel the leave first to save timecard.");
		}
		// Save if it is ok
		DailyPerformance daPerformance = mapper.map(dailyPerformance, DailyPerformance.class);
		daPerformance.setStatus(TimecardStatus.REQUESTED.toString());
		daPerformance.setCreatedDate(JojoHrmUtils.currentDate());
		daPerformance.setNoOfCodDelivered(dailyPerformance.getNoOfCodDelivered());
		daPerformance.setTxnMonth(JojoHrmUtils.currentMonth());
		daPerformance.setTxnYear(JojoHrmUtils.currentYear());
		dailyPerformanceRepository.save(daPerformance);
		return daPerformance.getId();
	}

	@Override
	public List<DailyPerformanceTO> getViewTimecard(Long employeeId, Integer month, Integer year) {

		List<DailyPerformance> performanceList = dailyPerformanceRepository.getDailyPerformance(employeeId, month,
				year);
		return performanceList.stream().map(performance -> mapper.map(performance, DailyPerformanceTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<DailyPerformanceTO> getPendingTimecard(Long managerId, String status) {

		List<DailyPerformanceTO> performanceList = new ArrayList<>();
		// Find Associates related to manager
		List<Employee> empList = employeeRepository.getEmployeeByManager(managerId);
		for (Employee employee : empList) {
			List<DailyPerformance> performances = dailyPerformanceRepository
					.getDailyPerformance(employee.getEmployeeId(), status);
			for (DailyPerformance performance : performances) {
				DailyPerformanceTO performanceTO = mapper.map(performance, DailyPerformanceTO.class);
				Employee da = employeeRepository.getEmployeeByEmpId(performanceTO.getEmployeeId());
				performanceTO.setEmployeeName(da.getName());
				performanceList.add(performanceTO);
			}
		}
		return performanceList;
	}

	@Override
	public boolean updateTimecard(Long requestId, Long managerId, String status, String rejectReason) {

		DailyPerformance dailyPerformance = dailyPerformanceRepository.getDailyPerformanceById(requestId);
		dailyPerformance.setStatus(status);
		if (status.equals(TimecardStatus.REJECTED.toString())) {
			dailyPerformance.setRejectReason(rejectReason);
		}
		if (status.equals(TimecardStatus.APPROVED.toString())) {
			dailyPerformance.setApproveBy(managerId);
			dailyPerformance.setApproveDate(JojoHrmUtils.currentDate());
			Employee employee = employeeRepository.getEmployeeByEmpId(dailyPerformance.getEmployeeId());
			// Update Daily Payment Table
			// Identify Shift Details & Calculate Penalty
			Shifts shift = shiftsRepository.findByShiftId(employee.getShiftId());
			boolean isLate = JojoHrmUtils.isLate(shift.getStartTime(), dailyPerformance.getInTime(),
					shift.getShiftTolerance());
			// Find Penalty Amount & Min Delivery unit
			PenaltyConfig penaltyConfig = penaltyConfigRepository.findByConfigId(employee.getBand());
			List<DeliveryIncentiveConfig> incentiveConfigs = deliveryIncentiveRepository
					.getDeliveryIncentive(employee.getBand());
			// Find MPOS Config
			List<MposConfig> mposConfigList = mposConfigRepository.getMposConfig(employee.getBand());
			// Save data in DailyPayment Table
			DailyPayment dailyPayment = new DailyPayment();
			dailyPayment.setConfigId(employee.getBand());
			dailyPayment.setTxnDate(dailyPerformance.getTxnDate());
			dailyPayment.setTxnMonth(JojoHrmUtils.currentMonth());
			dailyPayment.setTxnYear(JojoHrmUtils.currentYear());
			dailyPayment.setEmployeeId(employee.getEmployeeId());
			dailyPayment.setInTime(dailyPerformance.getInTime());
			dailyPayment.setOutTime(dailyPerformance.getOutTime());
			dailyPayment.setHoursWorked(dailyPerformance.getHoursWorked());
			dailyPayment.setStation(employee.getStation());
			dailyPayment.setDeliveryIncentives(new BigDecimal(0));
			dailyPayment.setPetrolCharges(new BigDecimal(0));
			dailyPayment.setMposCharges(new BigDecimal(0));
			dailyPayment.setLateCharges(new BigDecimal(0));
			dailyPayment.setLateIndicator(isLate);
			if (isLate) {
				dailyPayment.setLateCharges(penaltyConfig.getLateFine());
			}
			int noOfShipment = dailyPerformance.getNoOfShipment();
			dailyPayment.setNoOfDelivery(noOfShipment);
			dailyPayment.setBasePayDeduction(true);
			boolean isMPOSPaymentElligible = false;
			// Calculate Daily Incentives
			for (DeliveryIncentiveConfig deliveryConfig : incentiveConfigs) {
				if ((noOfShipment >= deliveryConfig.getMinTarget() && deliveryConfig.getMaxTarget() != null
						&& noOfShipment <= deliveryConfig.getMaxTarget())
						|| (noOfShipment >= deliveryConfig.getMinTarget() && deliveryConfig.getMaxTarget() == null)) {

					BigDecimal petrolCharges = deliveryConfig.getPetrolIncentives()
							.multiply(new BigDecimal(noOfShipment));
					dailyPayment.setPetrolCharges(petrolCharges);
					BigDecimal deliveryCharges = deliveryConfig.getDeliveryIncentives()
							.multiply(new BigDecimal(noOfShipment));
					dailyPayment.setDeliveryIncentives(deliveryCharges);
					dailyPayment.setBasePayDeduction(false);
					isMPOSPaymentElligible = true;
				}
			}

			// Calculate MPOS
			dailyPayment.setNoOfCodTxn(dailyPerformance.getNoOfCodDelivered());
			dailyPayment.setNoOfMpsTxn(dailyPerformance.getNoOfMposTxn());
			if (dailyPerformance.getNoOfCodDelivered() > 0 && dailyPerformance.getNoOfMposTxn() > 0
					&& isMPOSPaymentElligible) {

				double mposPercentage = ((double) dailyPerformance.getNoOfMposTxn()
						/ (double) dailyPerformance.getNoOfCodDelivered()) * 100;
				BigDecimal mposValue = new BigDecimal(0);
				for (MposConfig mposConfig : mposConfigList) {
					if (mposPercentage >= mposConfig.getMposPercentage()) {
						mposValue = mposConfig.getAmount();
					}
				}
				dailyPayment.setMposCharges(mposValue.multiply(new BigDecimal(dailyPerformance.getNoOfMposTxn())));
			}
			dailyPaymentRepository.save(dailyPayment);
		}
		dailyPerformanceRepository.save(dailyPerformance);
		return false;
	}

	@Override
	public void generatePayslips(Integer month, Integer year) throws InterruptedException, ExecutionException {

		Map<Long, String> payslipList = new HashMap<>();
		// Find Active Employee Details
		List<Employee> employeeList = employeeRepository.getActiveEmployee(UserStatus.ACTIVE.name());
		for (Employee employee : employeeList) {
			if (!employee.getRole().equals("ADMIN")) {
				SalaryProcessor processor = new SalaryProcessor(employee, month, year, payslipLocation, serverName);
				processor.setCityRepository(cityRepository);
				processor.setDaBonusRepository(daBonusRepository);
				processor.setDailyPaymentRepository(dailyPaymentRepository);
				processor.setDaMonthlyPaymentRepository(daMonthlyPaymentRepository);
				processor.setFixedIncentivesRepository(fixedIncentivesRepository);
				processor.setLeaveRequestRepository(leaveRequestRepository);
				processor.setOrgBandConfigRepository(orgBandConfigRepository);
				Future<String> result = executor.submit(processor);
				payslipList.put(employee.getEmployeeId(), result.get());
			}
		}
		// Save Data in DAPaysip Table
		for (Entry<Long, String> entry : payslipList.entrySet()) {
			DAPayslip daPayslip = new DAPayslip();
			daPayslip.setEmployeeId(entry.getKey());
			daPayslip.setFile(entry.getValue());
			daPayslip.setTxnMonth(month);
			daPayslip.setTxnYear(year);
			daPayslip.setTxnDate(JojoHrmUtils.currentDate());
			daPayslipRespository.save(daPayslip);
		}
	}

	@Override
	public List<DAPayslipTO> viewPayslips(Long employeeId) {
		List<DAPayslip> payslipsList = daPayslipRespository.getDailyPayment(employeeId);
		return payslipsList.stream().map(payslip -> mapper.map(payslip, DAPayslipTO.class))
				.collect(Collectors.toList());
	}

}
