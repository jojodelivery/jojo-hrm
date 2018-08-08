package com.pin91.hrm.persistant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e FROM Employee e WHERE e.userId = :userId")
	public Employee getEmployeeByUserId(@Param("userId") Long userId);

	@Query("SELECT e FROM Employee e WHERE e.employeeId = :employeeId")
	public Employee getEmployeeByEmpId(@Param("employeeId") Long employeeId);

	@Query("SELECT e FROM Employee e WHERE e.city = :city AND e.status = :status")
	public List<Employee> getEmployeeByCity(@Param("city") Integer city, @Param("status") String status);

	@Query("SELECT e FROM Employee e WHERE e.state = :state AND e.status = :status")
	public List<Employee> getEmployeeByState(@Param("state") String state, @Param("status") String status);

	@Query("SELECT e FROM Employee e WHERE e.managerId = :managerId")
	public List<Employee> getEmployeeByManager(@Param("managerId") Long managerId);

	@Query("SELECT e FROM Employee e WHERE e.status = :status")
	public List<Employee> getActiveEmployee(@Param("status") String status);

	@Query("SELECT e FROM Employee e WHERE e.managerAcess = :managerAcess AND e.status = :status")
	public List<Employee> getManager(@Param("managerAcess") boolean managerAcess, @Param("status") String status);
}
