package com.pin91.hrm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pin91.hrm.exception.JojoHRMException;
import com.pin91.hrm.persistant.domain.Employee;
import com.pin91.hrm.persistant.domain.OrgBandConfig;
import com.pin91.hrm.persistant.domain.Shifts;
import com.pin91.hrm.persistant.domain.User;
import com.pin91.hrm.persistant.repository.EmployeeRepository;
import com.pin91.hrm.persistant.repository.OrgBandConfigRepository;
import com.pin91.hrm.persistant.repository.ShiftsRepository;
import com.pin91.hrm.persistant.repository.UserRepository;
import com.pin91.hrm.transferobject.EmployeeTO;
import com.pin91.hrm.utils.JojoErrorCode;
import com.pin91.hrm.utils.JojoHrmUtils;
import com.pin91.hrm.utils.UserStatus;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	@Qualifier("mapper")
	private Mapper mapper;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private OrgBandConfigRepository orgBandConfigRepository;
	@Autowired
	private ShiftsRepository shiftRepository;

	@Transactional
	@Override
	public Long saveEmployee(EmployeeTO employeeTO) throws JojoHRMException {

		// Validate Mobile Number if does not exist save the user
		User user = userRepository.validateUser(employeeTO.getContactNo());
		// If user exist send error message
		if (user != null) {
			throw new JojoHRMException("User Already Exist");
		}
		// Save in user table
		user = new User();
		user.setUserName(employeeTO.getContactNo());
		user.setPassword(employeeTO.getPassword());
		user.setRole(employeeTO.getRole());
		user.setStatus(UserStatus.ACTIVE.name());
		user.setCreatedDate(JojoHrmUtils.currentDate());
		user.setCreatedBy(employeeTO.getCreatedBy());
		userRepository.save(user);

		// Update in employee table
		Employee employee = mapper.map(employeeTO, Employee.class);
		employee.setUserId(user.getUserId());
		employee.setStatus(UserStatus.ACTIVE.name());
		employeeRepository.save(employee);
		// Save Request
		return user.getUserId();
	}

	@Override
	public EmployeeTO validateUser(String username, String password) throws JojoHRMException {

		// Validate Mobile Number if does not exist save the user
		User user = userRepository.getUser(username, password);
		if (user == null) {
			throw new JojoHRMException(JojoErrorCode.INVALID_USER, "User does not exist");
		}
		Employee employee = employeeRepository.getEmployeeByUserId(user.getUserId());
		return mapper.map(employee, EmployeeTO.class);
	}

	@Override
	public EmployeeTO getEmployeeByEmployeeId(Long empId) {

		Employee employee = employeeRepository.getEmployeeByUserId(empId);
		return mapper.map(employee, EmployeeTO.class);
	}

	@Override
	public List<EmployeeTO> getEmployeeByCity(Integer city) {

		List<Employee> employeeList = employeeRepository.getEmployeeByCity(city, UserStatus.ACTIVE.name());
		List<EmployeeTO> empList = new ArrayList<>();
		for (Employee employee : employeeList) {
			OrgBandConfig bandConfig = orgBandConfigRepository.getConfigById(employee.getBand());
			EmployeeTO employeeTO = mapper.map(employee, EmployeeTO.class);
			employeeTO.setBandName(bandConfig.getBandName());
			Shifts shifts = shiftRepository.findByShiftId(employee.getShiftId());
			employeeTO.setShiftName(shifts.getShiftName());
			if (employee.getManagerId() != null) {
				Employee manager = employeeRepository.getEmployeeByEmpId(employee.getManagerId());
				employeeTO.setManagerName(manager.getName());
			}
			empList.add(employeeTO);
		}
		return empList;
	}

	@Override
	public List<EmployeeTO> getEmployeeByState(String state) {

		List<Employee> employeeList = employeeRepository.getEmployeeByState(state, UserStatus.ACTIVE.name());
		return employeeList.stream().map(employee -> mapper.map(employee, EmployeeTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<EmployeeTO> getManager() {

		List<Employee> employeeList = employeeRepository.getManager(true, UserStatus.ACTIVE.name());
		return employeeList.stream().map(employee -> mapper.map(employee, EmployeeTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public boolean updateEmployee(Long employeeId) {

		Employee employee = employeeRepository.getEmployeeByEmpId(employeeId);
		employee.setStatus(UserStatus.INACTIVE.name());
		employeeRepository.save(employee);
		User user = userRepository.findByUserId(employee.getUserId());
		user.setStatus(UserStatus.INACTIVE.name());
		userRepository.save(user);
		return true;
	}

	@Override
	public boolean updatePassword(String username, String password) {

		User user = userRepository.validateUser(username);
		if (user == null) {
			throw new JojoHRMException(JojoErrorCode.INVALID_USER, "User does not exist");
		}
		user.setPassword(password);
		userRepository.save(user);
		return false;
	}

}
