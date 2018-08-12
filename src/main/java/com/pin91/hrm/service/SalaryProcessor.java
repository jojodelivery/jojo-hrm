package com.pin91.hrm.service;

import java.awt.Color;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.Callable;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
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
import lombok.Setter;

@Getter
@Setter
public class SalaryProcessor implements Callable<String> {

	private static final float SPACING_WIDTH = 5;
	private static Font headerFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
	private Employee employee;
	private Integer month;
	private Integer year;
	private DailyPaymentRepository dailyPaymentRepository;
	private LeaveRequestRepository leaveRequestRepository;
	private FixedIncentivesRepository fixedIncentivesRepository;
	private OrgBandConfigRepository orgBandConfigRepository;
	private DABonusRepository daBonusRepository;
	private DAMonthlyPaymentRepository daMonthlyPaymentRepository;
	private CityRepository cityRepository;
	private String payslipLocation;
	private String serverName;

	public SalaryProcessor(Employee employee, Integer month, Integer year, String payslipLocation, String serverName) {
		super();
		this.employee = employee;
		this.month = month;
		this.year = year;
		this.payslipLocation = payslipLocation;
		this.serverName = serverName;
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
		City city = cityRepository.findByCityId(employee.getCity());

		Integer defaultWorkingDay = bandConfig.getNoOfWorkingDays();
		Integer shipmentDelivered = 0;
		Integer codDelivered = 0;
		Integer mposTxn = 0;
		Integer noOfDaysOntime = 0;
		BigDecimal deliveryIncentives = new BigDecimal(0);
		BigDecimal mposIncentives = new BigDecimal(0);
		BigDecimal petrolIncentives = new BigDecimal(0);
		Integer noOfDaysLate = 0;
		BigDecimal lateCharge = new BigDecimal(0);
		Double latePercentage;
		BigDecimal ontimeIncentives = new BigDecimal(0);
		BigDecimal attendanceIncentives = new BigDecimal(0);
		BigDecimal basePayDeductedCharges = new BigDecimal(0);
		BigDecimal payableSalary = new BigDecimal(0);
		BigDecimal bonus = new BigDecimal(0);
		BigDecimal baseSalary = bandConfig.getBaseSalary();
		BigDecimal noOfDays = new BigDecimal(JojoHrmUtils.noOfDaysInMonth(month, year));
		BigDecimal perDaySalary = baseSalary.divide(noOfDays, 2, RoundingMode.CEILING);
		BigDecimal absentCharges = new BigDecimal(0);
		Integer leavesTaken = 0;
		Integer noOfDaysAbsent = 0;
		Integer basePayDeductedDays = 0;

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
			} else {
				noOfDaysOntime++;
			}
			if (dailyPayment.getBasePayDeduction()) {
				basePayDeductedDays++;
			}
		}

		for (LeaveRequest leaveRequest : leaveList) {
			if (leaveRequest.getReason().equals(LeaveType.Mandatory_Leave.name())) {
				leavesTaken++;
			} else {
				noOfDaysAbsent++;
			}
		}
		if (noOfDaysAbsent > bandConfig.getSickLeaves()) {
			Integer extraDays = noOfDaysAbsent - bandConfig.getSickLeaves();
			noOfDaysAbsent = extraDays;
			absentCharges = perDaySalary.multiply(new BigDecimal(extraDays));
			baseSalary = baseSalary.subtract(absentCharges);
		} else {
			leavesTaken = leavesTaken + noOfDaysAbsent;
			noOfDaysAbsent = 0;
		}
		if (paymentList.size() >= bandConfig.getNoOfWorkingDays()) {
			attendanceIncentives = fixedIncentives.getAttendanceIncentive();
		}
		latePercentage = ((double) noOfDaysLate / (double) paymentList.size()) * 100;
		if ((100 - bandConfig.getOntimePercentage()) > latePercentage) {
			ontimeIncentives = fixedIncentives.getOntimeIncentive();
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
		daMonthlyPayment.setMobile(employee.getContactNo());
		daMonthlyPayment.setStation(employee.getStation());
		daMonthlyPayment.setBand(bandConfig.getBandName());
		daMonthlyPayment.setBaseSalary(baseSalary);
		daMonthlyPayment.setEmployeeId(employee.getEmployeeId());
		daMonthlyPayment.setEmpoyeeCode(employee.getEmployeeCode());
		daMonthlyPayment.setEmpoyeeName(employee.getName());
		daMonthlyPayment.setCity(city.getCityName());
		daMonthlyPayment.setTxnMonth(month);
		daMonthlyPayment.setTxnYear(year);
		daMonthlyPayment.setOntimeBonus(ontimeIncentives);
		daMonthlyPayment.setNoOfWorkingDays(defaultWorkingDay);
		daMonthlyPayment.setDaysWorked(paymentList.size());
		daMonthlyPayment.setNoOfDaysAbsent(noOfDaysAbsent);
		daMonthlyPayment.setNoOfDaysLate(noOfDaysLate);
		daMonthlyPayment.setAbsentPenalty(absentCharges);
		daMonthlyPayment.setDeliveryIncentive(deliveryIncentives);
		daMonthlyPayment.setPetrolIncentive(petrolIncentives);
		daMonthlyPayment.setMposIncentive(mposIncentives);
		daMonthlyPayment.setPayableAmount(payableSalary);
		daMonthlyPayment.setNoOfDaysOntime(noOfDaysOntime);
		daMonthlyPayment.setBonus(bonus);
		daMonthlyPayment.setMposTxn(mposTxn);
		daMonthlyPayment.setAttendanceBonus(attendanceIncentives);
		daMonthlyPayment.setLeavesTaken(leavesTaken);
		daMonthlyPayment.setShipmentDelivered(shipmentDelivered);
		daMonthlyPayment.setCodDelivered(codDelivered);
		daMonthlyPayment.setBasePayDeductedDays(basePayDeductedDays);
		daMonthlyPayment.setBasePayDeductedCharges(basePayDeductedCharges);
		daMonthlyPayment.setLateFine(lateCharge);
		daMonthlyPaymentRepository.save(daMonthlyPayment);
		return generateReport(daMonthlyPayment);
	}

	private String generateReport(DAMonthlyPayment daMonthlyPayment) {
		String fileName = null;
		try {
			fileName = daMonthlyPayment.getEmpoyeeCode() + "_" + daMonthlyPayment.getEmpoyeeName() + "_"
					+ daMonthlyPayment.getTxnMonth() + "_" + daMonthlyPayment.getTxnYear() + ".pdf";
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(payslipLocation + fileName));
			Image img = null;

			img = Image.getInstance(serverName + "images/jojo-logo.png");
			img.setAlignment(Image.RIGHT);
			img.scaleAbsoluteHeight(10);
			img.scaleAbsoluteWidth(10);
			img.scalePercent(15);
			Chunk chunk = new Chunk(img, 0, 0, true);
			HeaderFooter header = new HeaderFooter(new Phrase(chunk), false);
			header.setBorder(Rectangle.NO_BORDER);
			header.setAlignment(Element.ALIGN_RIGHT);
			document.setHeader(header);

			HeaderFooter footer = new HeaderFooter(new Phrase(
					"Pin91 Technologies Pvt. Ltd, 4th Floor, No. 855 6th Main, KSRTC Layout JP Nagar - 2nd Phase, Bengaluru, Karnataka 560078"),
					false);
			footer.setBorder(Rectangle.NO_BORDER);
			footer.setAlignment(Element.ALIGN_LEFT);
			document.setFooter(footer);
			document.open();
			addMetaData(document, daMonthlyPayment.getTxnMonth(), daMonthlyPayment.getTxnYear());
			addContent(document, daMonthlyPayment);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private void addContent(Document document, DAMonthlyPayment daMonthlyPayment) throws DocumentException {

		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		preface.add(new Paragraph(
				"PAYSLIP FOR THE MONTH OF " + JojoHrmUtils.getMonth(daMonthlyPayment.getTxnMonth()).toUpperCase() + ", "
						+ daMonthlyPayment.getTxnYear(),
				headerFont));
		preface.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(preface, 1);
		document.add(preface);

		// Emp Id & Name
		daDetails(document, daMonthlyPayment, "DA Code: " + daMonthlyPayment.getEmpoyeeCode(),
				"DA Name: " + daMonthlyPayment.getEmpoyeeName());
		// Contact No & City
		daDetails(document, daMonthlyPayment, "City: " + daMonthlyPayment.getCity(),
				"Mobile: " + daMonthlyPayment.getMobile());
		// Station & Band
		daDetails(document, daMonthlyPayment, "Station: " + daMonthlyPayment.getStation(),
				"Band: " + daMonthlyPayment.getBand());
		// Default Working days & No Of Days Worked
		daDetails(document, daMonthlyPayment, "Default working days: 26",
				"Days worked: " + daMonthlyPayment.getDaysWorked());
		// No of days taken leaves & No Of Days Additional Leaves
		daDetails(document, daMonthlyPayment, "Leaves taken: " + daMonthlyPayment.getLeavesTaken(),
				"Additional Leaves : " + daMonthlyPayment.getNoOfDaysAbsent());
		preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		preface.add(new Paragraph("Payment Breakup", headerFont));
		preface.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(preface, 1);
		document.add(preface);
		// Payment Breakup
		paymentBreakupHeader(document);
		// Base Salary
		paymentBreakup(document, daMonthlyPayment, "Base Salary", "NA", "" + daMonthlyPayment.getBaseSalary());

		// Fixed Incentives-All Day Present
		paymentBreakup(document, daMonthlyPayment, "Fixed Incentives- All Day Present", "NA",
				"" + daMonthlyPayment.getAttendanceBonus());
		// Fixed Incentives
		paymentBreakup(document, daMonthlyPayment, "Fixed Incentives- on time", "NA",
				"" + daMonthlyPayment.getOntimeBonus());
		// Delivery Incentives
		paymentBreakup(document, daMonthlyPayment, "Delivery Incentives", "" + daMonthlyPayment.getShipmentDelivered(),
				"" + daMonthlyPayment.getDeliveryIncentive());
		// Petrol Incentives
		paymentBreakup(document, daMonthlyPayment, "Fuel Incentives", "" + daMonthlyPayment.getShipmentDelivered(),
				"" + daMonthlyPayment.getPetrolIncentive());
		// Mpos Incentives
		paymentBreakup(document, daMonthlyPayment, "MPOS Incentives", "" + daMonthlyPayment.getMposTxn(),
				"" + daMonthlyPayment.getMposIncentive());
		// Deduction starts
		// Late Fine
		paymentBreakup(document, daMonthlyPayment, "Late Fine", "" + daMonthlyPayment.getNoOfDaysLate(),
				"-" + daMonthlyPayment.getLateFine());
		// Absent
		paymentBreakup(document, daMonthlyPayment, "Additional Days Absent", "" + daMonthlyPayment.getNoOfDaysAbsent(),
				"-" + daMonthlyPayment.getAbsentPenalty());
		// Base Salary Deducted for non performance
		paymentBreakup(document, daMonthlyPayment, "Base salary deducted for non delivery",
				"" + daMonthlyPayment.getBasePayDeductedDays(), "-" + daMonthlyPayment.getBasePayDeductedCharges());
		paymentBreakup(document, daMonthlyPayment, "Payable Amount", "NA", "" + daMonthlyPayment.getPayableAmount());
	}

	private void paymentBreakupHeader(Document document) throws DocumentException {
		PdfPTable table = createTable(3);
		PdfPCell cell = new PdfPCell(new Phrase("Component", headerFont));
		addCellToTable(table, cell);
		cell = new PdfPCell(new Phrase("Quantity", headerFont));
		addCellToTable(table, cell);
		cell = new PdfPCell(new Phrase("Amount", headerFont));
		addCellToTable(table, cell);
		document.add(table);
	}

	private void addCellToTable(PdfPTable table, PdfPCell cell) {
		cell.setBorder(Rectangle.BOX);
		cell.setPadding(5);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
	}

	private void daDetails(Document document, DAMonthlyPayment daMonthlyPayment, String text1, String text2)
			throws DocumentException {
		PdfPTable table = createTable(2);
		PdfPCell cell = new PdfPCell(new Phrase(text1));
		addCellToTable(table, cell);
		cell = new PdfPCell(new Phrase(text2));
		addCellToTable(table, cell);
		document.add(table);
	}

	private void paymentBreakup(Document document, DAMonthlyPayment daMonthlyPayment, String text1, String text2,
			String text3) throws DocumentException {

		PdfPTable table = createTable(3);
		PdfPCell cell = new PdfPCell(new Phrase(text1));
		addCellToTable(table, cell);
		cell = new PdfPCell(new Phrase(text2));
		addCellToTable(table, cell);
		cell = new PdfPCell(new Phrase(text3));
		addCellToTable(table, cell);
		document.add(table);
	}

	private PdfPTable createTable(int noOfCols) throws DocumentException {

		PdfPTable table = new PdfPTable(noOfCols);
		table.setWidthPercentage(100f);
		return table;
	}

	private void addMetaData(Document document, Integer month, Integer year) {
		document.addTitle("Payslips for the " + month + "," + year);
		document.addSubject("Payslips");
		document.addAuthor("Pin91 Technologies Pvt. Ltd");
		document.addCreator("IT Department");
	}

	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public Paragraph createHeader(String headerText) throws DocumentException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 0);

		preface.setSpacingBefore(SPACING_WIDTH);
		preface.setSpacingAfter(SPACING_WIDTH);

		PdfPTable table = createTable(1);
		PdfPCell cell = new PdfPCell(new Phrase(headerText, headerFont));
		cell.setBorder(0);
		cell.setBackgroundColor(new Color(192, 192, 192));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPadding(5);

		table.addCell(cell);

		preface.add(table);

		return preface;
	}
}
