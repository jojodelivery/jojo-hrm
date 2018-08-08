package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {

	@Query("SELECT SR FROM Station SR WHERE SR.cityId = :cityId")
	public List<Station> getStation(@Param("cityId") Integer cityId);

}
