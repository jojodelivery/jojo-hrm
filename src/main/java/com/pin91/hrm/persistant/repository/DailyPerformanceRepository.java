package com.pin91.hrm.persistant.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.DailyPerformance;

@Repository
public interface DailyPerformanceRepository extends JpaRepository<DailyPerformance, Long> {

	@Query("SELECT DP FROM DailyPerformance DP WHERE DP.employeeId = :employeeId AND DP.txnDate = :txnDate")
	public DailyPerformance getDailyPerformance(@Param("employeeId") Long employeeId, @Param("txnDate") Date txnDate);

	@Query("SELECT DP FROM DailyPerformance DP WHERE DP.employeeId = :employeeId AND DP.txnMonth = :txnMonth AND DP.txnYear = :txnYear")
	public List<DailyPerformance> getDailyPerformance(@Param("employeeId") Long employeeId,
			@Param("txnMonth") Integer txnMonth, @Param("txnYear") Integer txnYear);

	@Query("SELECT DP FROM DailyPerformance DP WHERE DP.employeeId = :employeeId AND DP.status = :status")
	public List<DailyPerformance> getDailyPerformance(@Param("employeeId") Long employeeId,
			@Param("status") String status);

	@Query("SELECT DP FROM DailyPerformance DP WHERE DP.id = :id")
	public DailyPerformance getDailyPerformanceById(@Param("id") Long id);
}
