package com.pin91.hrm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pin91.hrm.service.IGeneralService;
import com.pin91.hrm.transferobject.CityTO;
import com.pin91.hrm.transferobject.KeyValueResponse;
import com.pin91.hrm.transferobject.OrgBandConfigTO;
import com.pin91.hrm.transferobject.ShiftsTO;
import com.pin91.hrm.transferobject.StateTO;
import com.pin91.hrm.transferobject.StationTO;
import com.pin91.hrm.transferobject.UserRoleConfigTO;

@RestController
@RequestMapping("/general")
public class GeneralController {

	@Autowired
	private IGeneralService iGeneralService;

	@RequestMapping(value = "/state", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getState() {

		List<StateTO> list = iGeneralService.getStates();
		return new ResponseEntity<List<StateTO>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/city/{stateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCity(@PathVariable("stateId") final Integer stateId) {

		List<CityTO> cityList = iGeneralService.getCityByStateId(stateId);
		return new ResponseEntity<List<CityTO>>(cityList, HttpStatus.OK);
	}

	@RequestMapping(value = "/shifts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getShifts() {

		List<ShiftsTO> list = iGeneralService.getShift();
		return new ResponseEntity<List<ShiftsTO>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/band/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBand(@PathVariable("cityId") final Integer cityId) {
		List<OrgBandConfigTO> list = iGeneralService.getBand(cityId);
		return new ResponseEntity<List<OrgBandConfigTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/station/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStation(@PathVariable("cityId") final Integer cityId) {
		List<StationTO> list = iGeneralService.getStation(cityId);
		return new ResponseEntity<List<StationTO>>(list, HttpStatus.OK);
	}

	//UnUsed Method
	@RequestMapping(value = "/user-role", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserRole() {
		List<UserRoleConfigTO> list = iGeneralService.getUserRole();
		return new ResponseEntity<List<UserRoleConfigTO>>(list, HttpStatus.OK);
	}

	// Unimplemented method
	@RequestMapping(value = "/city", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCity(final String cityName, final Integer stateId) {
		KeyValueResponse response = new KeyValueResponse();
		response.setValue("success");
		return new ResponseEntity<KeyValueResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/state", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addState(final String stateName) {
		KeyValueResponse response = new KeyValueResponse();
		response.setValue("success");
		return new ResponseEntity<KeyValueResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/band", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addBand(final String bandName, final Integer noticePeriod,
			final Integer incrementedLeaves) {
		KeyValueResponse response = new KeyValueResponse();
		response.setValue("success");
		return new ResponseEntity<KeyValueResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/band", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateBand(final String bandName, final Integer noticePeriod,
			final Integer incrementedLeaves) {
		KeyValueResponse response = new KeyValueResponse();
		response.setValue("success");
		return new ResponseEntity<KeyValueResponse>(response, HttpStatus.OK);
	}
}
