package com.pin91.hrm.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.pin91.hrm.persistant.domain.BatchStatus;
import com.pin91.hrm.persistant.repository.BatchStatusRepository;
import com.pin91.hrm.service.ITimecardService;
import com.pin91.hrm.utils.JojoHrmUtils;

public class GeneratePayslipsJob implements Job {

	@Autowired
	private ITimecardService iTimecardService;

	@Autowired
	private BatchStatusRepository batchStatusRepository;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			int day = JojoHrmUtils.currentDay();
			if (day == 1) {
				int month = JojoHrmUtils.currentMonth();
				int year = JojoHrmUtils.currentYear();
				if (month == 1) {
					month = 12;
					year = year - 1;
				} else {
					month = month - 1;
				}
				// Check if the batch has aready run. If no then run it.
				BatchStatus batchStatus = batchStatusRepository.findBatchStatus(month, year);
				if (batchStatus == null) {
					iTimecardService.generatePayslips(JojoHrmUtils.currentMonth(), JojoHrmUtils.currentYear());
					batchStatus = new BatchStatus();
					batchStatus.setMonth(month);
					batchStatus.setYear(year);
					batchStatus.setDate(JojoHrmUtils.currentDate());
					batchStatusRepository.save(batchStatus);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
