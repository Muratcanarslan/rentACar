package com.rentACar.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentACar.rentACar.entities.concretes.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{

}
