package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.DAMonthlyPayment;

@Repository
public interface DAMonthlyPaymentRepository extends JpaRepository<DAMonthlyPayment, Long> {

	@Query("SELECT DP FROM DAMonthlyPayment DP WHERE DP.station = :station AND DP.txnMonth = :txnMonth AND DP.txnYear = :txnYear")
	public List<DAMonthlyPayment> getMnthlyPayment(@Param("station") String station,
			@Param("txnMonth") Integer txnMonth, @Param("txnYear") Integer txnYear);
}
