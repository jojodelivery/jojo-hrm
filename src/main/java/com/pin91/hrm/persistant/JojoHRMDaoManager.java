package com.pin91.hrm.persistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pin91.hrm.persistant.repository.CityRepository;
import com.pin91.hrm.persistant.repository.DABonusRepository;
import com.pin91.hrm.persistant.repository.DAMonthlyPaymentRepository;
import com.pin91.hrm.persistant.repository.DailyPaymentRepository;
import com.pin91.hrm.persistant.repository.FixedIncentivesRepository;
import com.pin91.hrm.persistant.repository.LeaveRequestRepository;
import com.pin91.hrm.persistant.repository.OrgBandConfigRepository;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class JojoHRMDaoManager {

	@Autowired
	private DailyPaymentRepository dailyPaymentRepository;
	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	@Autowired
	private FixedIncentivesRepository fixedIncentivesRepository;
	@Autowired
	private OrgBandConfigRepository orgBandConfigRepository;
	@Autowired
	private DABonusRepository daBonusRepository;
	@Autowired
	private DAMonthlyPaymentRepository daMonthlyPaymentRepository;
	@Autowired
	private CityRepository cityRepository;

}
