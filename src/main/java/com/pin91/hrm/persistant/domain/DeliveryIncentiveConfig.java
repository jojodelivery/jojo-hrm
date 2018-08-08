package com.pin91.hrm.persistant.domain;

import java.math.BigDecimal;

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
@Table(name = "delivery_incentives_config")
public class DeliveryIncentiveConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "band_config_id")
	private Integer configId;
	@Column(name = "min_target")
	private Integer minTarget;
	@Column(name = "max_target")
	private Integer maxTarget;
	@Column(name = "petrol_incentives")
	private BigDecimal petrolIncentives;
	@Column(name = "delivery_incentives")
	private BigDecimal deliveryIncentives;
}
