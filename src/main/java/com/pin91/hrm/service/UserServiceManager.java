package com.pin91.hrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceManager {

	@Autowired
	private IUserService iUserService;
	
	public void addUser(){
		
	}
}
