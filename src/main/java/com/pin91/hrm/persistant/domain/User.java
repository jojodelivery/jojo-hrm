package com.pin91.hrm.persistant.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "user_name")
	private String userName; 
	@Column(name = "password")
	private String password;
	@Column(name = "last_login")
	private Date lastLogin;
	@Column(name = "role")
	private String role;
	@Column(name = "status")
	private String status;
}
