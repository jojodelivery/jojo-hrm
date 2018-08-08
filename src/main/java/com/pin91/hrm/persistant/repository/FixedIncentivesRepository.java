package com.pin91.hrm.persistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.FixedIncentives;

@Repository
public interface FixedIncentivesRepository extends JpaRepository<FixedIncentives, Long> {

	@Query("SELECT fi FROM FixedIncentives fi WHERE fi.configId = :configId")
	public FixedIncentives findIncentive(@Param("configId") Integer configId);
}
