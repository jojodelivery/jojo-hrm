package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.MposConfig;

@Repository
public interface MposConfigRepository extends JpaRepository<MposConfig, Long> {

	@Query("SELECT MC FROM MposConfig MC WHERE MC.configId = :configId")
	public List<MposConfig> getMposConfig(@Param("configId") Integer configId);

}
