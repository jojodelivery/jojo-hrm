package com.pin91.hrm.service;

public interface IReportService {

	public String getMonthlyReport(String station, Integer month, Integer year);

	public String getDailyReport(String station, Integer month, Integer year);
}
