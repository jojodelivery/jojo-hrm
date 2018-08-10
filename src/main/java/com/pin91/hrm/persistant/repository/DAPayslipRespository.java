package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.DAPayslip;

@Repository
public interface DAPayslipRespository extends JpaRepository<DAPayslip, Long> {

	@Query("SELECT DP FROM DAPayslip DP WHERE DP.employeeId = :employeeId")
	public List<DAPayslip> getDailyPayment(@Param("employeeId") Long employeeId);
}
