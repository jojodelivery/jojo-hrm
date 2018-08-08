package com.pin91.hrm.persistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.DABonus;

@Repository
public interface DABonusRepository extends JpaRepository<DABonus, Long> {

	@Query("SELECT DP FROM DABonus DP WHERE DP.employeeId = :employeeId AND DP.txnMonth = :txnMonth AND DP.txnYear = :txnYear")
	public DABonus getBonus(@Param("employeeId") Long employeeId, @Param("txnMonth") Integer txnMonth,
			@Param("txnYear") Integer txnYear);
}
