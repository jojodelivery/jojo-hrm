package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {

	@Query("SELECT cd FROM City cd WHERE cd.stateId = :stateId") 
	public List<City> findByStateId(@Param("stateId")Integer stateId);
	
	@Query("SELECT cd FROM City cd WHERE cd.cityId = :cityId") 
	public City findByCityId(@Param("cityId")Integer cityId);
}
