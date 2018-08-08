package com.pin91.hrm.persistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.PenaltyConfig;

@Repository
public interface PenaltyConfigRepository extends JpaRepository<PenaltyConfig, Long> {

	@Query("SELECT PCR FROM PenaltyConfig PCR WHERE PCR.configId = :configId")
	public PenaltyConfig findByConfigId(@Param("configId") Integer configId);

}
