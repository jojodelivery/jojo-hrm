package com.pin91.hrm.persistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.userName = :userName")
	public User validateUser(@Param("userName") String userName);

	@Query("SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password")
	public User getUser(@Param("userName") String userName, @Param("password") String password);
	
	@Query("SELECT u FROM User u WHERE u.userId = :userId")
	public User findByUserId(@Param("userId") Long userId);
	
	
}
