package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.DeliveryIncentiveConfig;

@Repository
public interface DeliveryIncentiveRepository extends JpaRepository<DeliveryIncentiveConfig, Long> {

	@Query("SELECT DIC FROM DeliveryIncentiveConfig DIC WHERE DIC.configId = :configId")
	public List<DeliveryIncentiveConfig> getDeliveryIncentive(@Param("configId") Integer configId);
}
