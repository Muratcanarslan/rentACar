package com.rentACar.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentACar.rentACar.entities.concretes.CreditCardInformation;

@Repository
public interface CreditCardInformationDao extends JpaRepository<CreditCardInformation, Integer>{
	
	List<CreditCardInformation> getByCustomer_CustomerId(int customerId);
	
	boolean existsByCardNumber(String cardNumber);

}
