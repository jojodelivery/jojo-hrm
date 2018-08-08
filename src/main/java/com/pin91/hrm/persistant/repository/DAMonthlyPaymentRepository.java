package com.pin91.hrm.persistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pin91.hrm.persistant.domain.DAMonthlyPayment;

@Repository
public interface DAMonthlyPaymentRepository extends JpaRepository<DAMonthlyPayment, Long> {

}
