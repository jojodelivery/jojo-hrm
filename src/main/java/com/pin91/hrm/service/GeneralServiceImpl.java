package com.pin91.hrm.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pin91.hrm.persistant.domain.City;
import com.pin91.hrm.persistant.domain.OrgBandConfig;
import com.pin91.hrm.persistant.domain.Shifts;
import com.pin91.hrm.persistant.domain.State;
import com.pin91.hrm.persistant.domain.Station;
import com.pin91.hrm.persistant.domain.UserRoleConfig;
import com.pin91.hrm.persistant.repository.CityRepository;
import com.pin91.hrm.persistant.repository.OrgBandConfigRepository;
import com.pin91.hrm.persistant.repository.ShiftsRepository;
import com.pin91.hrm.persistant.repository.StateRepository;
import com.pin91.hrm.persistant.repository.StationRepository;
import com.pin91.hrm.persistant.repository.UserRoleConfigRepository;
import com.pin91.hrm.transferobject.CityTO;
import com.pin91.hrm.transferobject.OrgBandConfigTO;
import com.pin91.hrm.transferobject.ShiftsTO;
import com.pin91.hrm.transferobject.StateTO;
import com.pin91.hrm.transferobject.StationTO;
import com.pin91.hrm.transferobject.UserRoleConfigTO;

@Service
public class GeneralServiceImpl implements IGeneralService {

	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository citiesRepository;
	@Autowired
	private UserRoleConfigRepository userRoleConfigRepository;
	@Autowired
	private OrgBandConfigRepository orgBandConfigRepository;
	@Autowired
	private ShiftsRepository shiftsRepository;
	@Autowired
	private StationRepository stationRepository;

	@Autowired
	@Qualifier("mapper")
	private Mapper mapper;

	@Override
	@Transactional
	public List<StateTO> getStates() {

		List<State> stateList = stateRepository.findAll();
		return stateList.stream().map(state -> mapper.map(state, StateTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<CityTO> getCityByStateId(Integer stateId) {

		List<City> cityList = citiesRepository.findByStateId(stateId);
		return cityList.stream().map(city -> mapper.map(city, CityTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<UserRoleConfigTO> getUserRole() {
		List<UserRoleConfig> userRoleList = userRoleConfigRepository.findAll();
		return userRoleList.stream().map(userRole -> mapper.map(userRole, UserRoleConfigTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<OrgBandConfigTO> getBand(Integer cityId) {
		List<OrgBandConfig> orgBandList = orgBandConfigRepository.getConfigByCityId(cityId);
		return orgBandList.stream().map(orgBand -> mapper.map(orgBand, OrgBandConfigTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ShiftsTO> getShift() {
		List<Shifts> shiftList = shiftsRepository.findAll();
		return shiftList.stream().map(shift -> mapper.map(shift, ShiftsTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<StationTO> getStation(Integer cityId) {
		List<Station> stationist = stationRepository.getStation(cityId);
		return stationist.stream().map(station -> mapper.map(station, StationTO.class)).collect(Collectors.toList());
	}

}
