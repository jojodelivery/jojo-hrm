package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.DailyPayment;

@Repository
public interface DailyPaymentRepository extends JpaRepository<DailyPayment, Long> {

	@Query("SELECT DP FROM DailyPayment DP WHERE DP.employeeId = :employeeId AND DP.txnMonth = :txnMonth AND DP.txnYear = :txnYear")
	public List<DailyPayment> getDailyPayment(@Param("employeeId") Long employeeId, @Param("txnMonth") Integer txnMonth,
			@Param("txnYear") Integer txnYear);
}
