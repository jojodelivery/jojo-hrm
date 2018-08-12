package com.pin91.hrm.persistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.BatchStatus;

@Repository
public interface BatchStatusRepository extends JpaRepository<BatchStatus, Long> {

	@Query("SELECT BS FROM BatchStatus BS WHERE BS.month = :month AND BS.year = :year")
	public BatchStatus findBatchStatus(@Param("month") Integer month, @Param("year") Integer year);

}
