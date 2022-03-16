package com.rentACar.rentACar.business.abstracts;

import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.entities.concretes.Customer;

public interface CustomerService {
	
	Customer getCustomerById(int customerId) throws CustomerNotFoundException;
	
	void checkIfCustomerExists(int customerId) throws CustomerNotFoundException;
}
