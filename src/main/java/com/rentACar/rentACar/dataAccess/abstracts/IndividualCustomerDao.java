package com.rentACar.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentACar.rentACar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer>{
	
	boolean existsByNationalIdentity(String nationalIdentity);
}
