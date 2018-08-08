package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

	@Query("SELECT LR FROM LeaveRequest LR WHERE LR.employeeId = :employeeId")
	public List<LeaveRequest> getLeaveDetails(@Param("employeeId") Long employeeId, Pageable pageable);

	@Query("SELECT LR FROM LeaveRequest LR WHERE LR.id = :id")
	public LeaveRequest getLeaveDetail(@Param("id") Long id);

	@Query("SELECT LR FROM LeaveRequest LR WHERE LR.employeeId = :employeeId AND LR.status = :status")
	public List<LeaveRequest> getLeavesByStatus(@Param("employeeId") Long employeeId, @Param("status") String status,
			Pageable pageable);

	@Query("SELECT LR FROM LeaveRequest LR WHERE LR.employeeId = :employeeId AND LR.txnMonth = :txnMonth AND LR.txnYear = :txnYear AND LR.status = :status")
	public List<LeaveRequest> getLeaveDetails(@Param("employeeId") Long employeeId, @Param("txnMonth") Integer txnMonth,
			@Param("txnYear") Integer txnYear, @Param("status") String status);
}
