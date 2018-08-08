package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.OrgBandConfig;

@Repository
public interface OrgBandConfigRepository extends JpaRepository<OrgBandConfig, Integer> {

	@Query("SELECT OBC FROM OrgBandConfig OBC WHERE OBC.bandId = :bandId")
	public OrgBandConfig getConfigById(@Param("bandId") Integer bandId);
	
	@Query("SELECT OBC FROM OrgBandConfig OBC WHERE OBC.cityId = :cityId")
	public List<OrgBandConfig> getConfigByCityId(@Param("cityId") Integer cityId);

}
