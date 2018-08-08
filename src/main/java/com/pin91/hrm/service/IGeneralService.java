package com.pin91.hrm.service;

import java.util.List;

import com.pin91.hrm.transferobject.CityTO;
import com.pin91.hrm.transferobject.OrgBandConfigTO;
import com.pin91.hrm.transferobject.ShiftsTO;
import com.pin91.hrm.transferobject.StateTO;
import com.pin91.hrm.transferobject.StationTO;
import com.pin91.hrm.transferobject.UserRoleConfigTO;

public interface IGeneralService {

	public List<StateTO> getStates();
	public List<CityTO> getCityByStateId(Integer stateId);
	public List<UserRoleConfigTO> getUserRole();
	public List<OrgBandConfigTO> getBand(Integer cityId);
	public List<ShiftsTO> getShift();
	public List<StationTO> getStation(Integer cityId);
}
