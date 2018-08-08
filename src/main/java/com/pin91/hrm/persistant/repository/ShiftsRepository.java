package com.pin91.hrm.persistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.Shifts;

@Repository
public interface ShiftsRepository extends JpaRepository<Shifts, Long> {

	@Query("SELECT SF FROM Shifts SF WHERE SF.shiftId = :shiftId")
	public Shifts findByShiftId(@Param("shiftId") Integer shiftId);
}
