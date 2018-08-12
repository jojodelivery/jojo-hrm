package com.pin91.hrm.service;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pin91.hrm.persistant.domain.DAMonthlyPayment;
import com.pin91.hrm.persistant.domain.DailyPayment;
import com.pin91.hrm.persistant.domain.Employee;
import com.pin91.hrm.persistant.repository.DAMonthlyPaymentRepository;
import com.pin91.hrm.persistant.repository.DailyPaymentRepository;
import com.pin91.hrm.persistant.repository.EmployeeRepository;
import com.pin91.hrm.utils.JojoHrmUtils;

@Service
public class ReportServiceImpl implements IReportService {

	@Autowired
	@Qualifier("mapper")
	private Mapper mapper;

	@Autowired
	private DAMonthlyPaymentRepository daMonthlyPaymentRepository;
	@Autowired
	private DailyPaymentRepository dailyPaymentRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	@Value("${report.location}")
	private String reportLocation;

	@Override
	public String getMonthlyReport(String station, Integer month, Integer year) {

		String fileName = "Monthly_" + JojoHrmUtils.getMonth(month) + "_" + year + "_" + System.currentTimeMillis()
				+ ".xlsx";
		try {
			List<DAMonthlyPayment> paymentList = daMonthlyPaymentRepository.getMnthlyPayment(station, month, year);

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(station + "_" + JojoHrmUtils.getMonth(month) + "_" + year);
			int rowIndex = 1;
			addHeader(sheet);
			for (DAMonthlyPayment daMonthlyPayment : paymentList) {
				Row row = sheet.createRow(rowIndex++);
				createCell(row, 0, daMonthlyPayment.getEmpoyeeCode());
				createCell(row, 1, daMonthlyPayment.getEmpoyeeName());
				createCell(row, 2, daMonthlyPayment.getMobile());
				createCell(row, 3, daMonthlyPayment.getStation());
				createCell(row, 4, daMonthlyPayment.getBand());
				createCell(row, 5, daMonthlyPayment.getCity());
				createCell(row, 6, JojoHrmUtils.getMonth(daMonthlyPayment.getTxnMonth()));
				createCell(row, 7, "" + daMonthlyPayment.getTxnYear());
				createCell(row, 8, "" + daMonthlyPayment.getNoOfWorkingDays());
				createCell(row, 9, "" + daMonthlyPayment.getDaysWorked());
				createCell(row, 10, "" + daMonthlyPayment.getLeavesTaken());
				createCell(row, 11, "" + daMonthlyPayment.getNoOfDaysAbsent());
				createCell(row, 12, "" + daMonthlyPayment.getShipmentDelivered());
				createCell(row, 13, "" + daMonthlyPayment.getCodDelivered());
				createCell(row, 14, "" + daMonthlyPayment.getMposTxn());
				createCell(row, 15, "" + daMonthlyPayment.getPayableAmount());
				createCell(row, 16, "" + daMonthlyPayment.getBaseSalary());
				createCell(row, 17, "" + daMonthlyPayment.getAttendanceBonus());
				createCell(row, 18, "" + daMonthlyPayment.getNoOfDaysOntime());
				createCell(row, 19, "" + daMonthlyPayment.getOntimeBonus());
				createCell(row, 20, "" + daMonthlyPayment.getAbsentPenalty());
				createCell(row, 21, "" + daMonthlyPayment.getNoOfDaysLate());
				createCell(row, 22, "" + daMonthlyPayment.getLateFine());
				createCell(row, 23, "" + daMonthlyPayment.getDeliveryIncentive());
				createCell(row, 24, "" + daMonthlyPayment.getPetrolIncentive());
				createCell(row, 25, "" + daMonthlyPayment.getMposIncentive());
				createCell(row, 26, "" + daMonthlyPayment.getBasePayDeductedDays());
				createCell(row, 27, "" + daMonthlyPayment.getBasePayDeductedCharges());

			}
			FileOutputStream fos = new FileOutputStream(reportLocation + fileName);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileName;
	}

	private void addHeader(Sheet sheet) {
		Row header = sheet.createRow(0);
		createCell(header, 0, "Employee Code");
		createCell(header, 1, "Name");
		createCell(header, 2, "Mobile");
		createCell(header, 3, "Station");
		createCell(header, 4, "Band");
		createCell(header, 5, "City");
		createCell(header, 6, "Month");
		createCell(header, 7, "Year");
		createCell(header, 8, "No of Working Days");
		createCell(header, 9, "Days Worked");
		createCell(header, 10, "Leaves Taken");
		createCell(header, 11, "Additional Leaves Taken");
		createCell(header, 12, "Total Shipment Delivered");
		createCell(header, 13, "COD Delivered");
		createCell(header, 14, "No Of MPOS TXn");
		createCell(header, 15, "Payable Amount");
		createCell(header, 16, "Base Salary");
		createCell(header, 17, "Attendance Bonus");
		createCell(header, 18, "No of Days Ontime");
		createCell(header, 19, "Ontime Bonus");
		createCell(header, 20, "Absent Penalty");
		createCell(header, 21, "No of Days Late");
		createCell(header, 22, "Late Charges");
		createCell(header, 23, "Delivery Incentives");
		createCell(header, 24, "Petrol Incentives");
		createCell(header, 25, "MPOS Incentives");
		createCell(header, 26, "No of Times Basepay Dedcuted");
		createCell(header, 27, "Basepay Deducted Charge");
	}

	private void createCell(Row row, int index, String value) {
		Cell cell = row.createCell(index);
		cell.setCellValue(value);
	}

	@Override
	public String getDailyReport(String station, Integer month, Integer year) {

		String fileName = "Daily_" + JojoHrmUtils.getMonth(month) + "_" + year + "_" + System.currentTimeMillis()
				+ ".xlsx";
		try {
			List<DailyPayment> paymentList = dailyPaymentRepository.getDailyPayment(station, month, year);
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(station + "_" + JojoHrmUtils.getMonth(month) + "_" + year);
			int rowIndex = 1;
			addDailyHeader(sheet);
			for (DailyPayment dailyPayment : paymentList) {
				Row row = sheet.createRow(rowIndex++);
				Employee employee = employeeRepository.getEmployeeByEmpId(dailyPayment.getEmployeeId());
				createCell(row, 0, employee.getEmployeeCode());
				createCell(row, 1, employee.getName());
				createCell(row, 2, employee.getContactNo());
				createCell(row, 3, employee.getStation());
				createCell(row, 4, "" + dailyPayment.getTxnDate());
				createCell(row, 5, dailyPayment.getInTime());
				createCell(row, 6, dailyPayment.getOutTime());
				createCell(row, 7, dailyPayment.getHoursWorked());
				createCell(row, 8, "" + dailyPayment.getBasePayDeduction());
				createCell(row, 9, "" + dailyPayment.getLateIndicator());
				createCell(row, 10, "" + dailyPayment.getLateCharges());
				createCell(row, 11, "" + dailyPayment.getNoOfDelivery());
				createCell(row, 12, "" + dailyPayment.getDeliveryIncentives());
				createCell(row, 13, "" + dailyPayment.getPetrolCharges());
				createCell(row, 14, "" + dailyPayment.getNoOfCodTxn());
				createCell(row, 15, "" + dailyPayment.getNoOfMpsTxn());
				createCell(row, 16, "" + dailyPayment.getMposCharges());
			}
			FileOutputStream fos = new FileOutputStream(reportLocation + fileName);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private void addDailyHeader(Sheet sheet) {
		Row header = sheet.createRow(0);
		createCell(header, 0, "Employee Code");
		createCell(header, 1, "Name");
		createCell(header, 2, "Mobile");
		createCell(header, 3, "Station");
		createCell(header, 4, "Date");
		createCell(header, 5, "In time");
		createCell(header, 6, "Out time");
		createCell(header, 7, "Hours worked");
		createCell(header, 8, "Basepay Deducted");
		createCell(header, 9, "Late");
		createCell(header, 10, "Late Charges");
		createCell(header, 11, "No of Delivery");
		createCell(header, 12, "Delivery Incentives");
		createCell(header, 13, "Petrol Incentives");
		createCell(header, 14, "No of COD TXn");
		createCell(header, 15, "No of MPOS TXn");
		createCell(header, 16, "MPOS Charges");
	}
}
