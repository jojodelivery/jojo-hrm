package com.pin91.hrm.persistant.domain;

import java.util.Date;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Audit {

    @Column(name = "created_by")
	private Long createdBy;
    @Column(name = "created_date")
	private Date createdDate;
}
