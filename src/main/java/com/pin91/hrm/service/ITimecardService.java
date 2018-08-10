package com.pin91.hrm.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.pin91.hrm.transferobject.DAPayslipTO;
import com.pin91.hrm.transferobject.DailyPerformanceTO;
import com.pin91.hrm.transferobject.LeaveRequestTO;

public interface ITimecardService {

	public Long applyLeave(LeaveRequestTO request);

	public List<LeaveRequestTO> viewLeaves(Long employeeId);

	public boolean updateLeave(Long requestId, Long managerId, String status);

	public List<LeaveRequestTO> viewLeaveByManager(Long managerId);

	public Long saveTimecard(DailyPerformanceTO dailyPerformance);

	public List<DailyPerformanceTO> getViewTimecard(Long employeeId, Integer month, Integer year);

	public List<DailyPerformanceTO> getPendingTimecard(Long managerId, String status);

	public boolean updateTimecard(Long requestId, Long managerId, String status,String rejectReason);
	
	public void generatePayslips(Integer month, Integer year) throws InterruptedException, ExecutionException;
	
	public List<DAPayslipTO> viewPayslips(Long employeeId);
}
