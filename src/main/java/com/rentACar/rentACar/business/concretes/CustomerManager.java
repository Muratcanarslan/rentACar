package com.rentACar.rentACar.business.concretes;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CustomerService;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.dataAccess.abstracts.CustomerDao;
import com.rentACar.rentACar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService {

	private CustomerDao customerDao;

	@Override
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
		checkIfCustomerExists(customerId);
		return this.customerDao.getById(customerId);
	}

	public CustomerManager(CustomerDao customerDao) {
		super();
		this.customerDao = customerDao;
	}

	@Override
	public void checkIfCustomerExists(int customerId) throws CustomerNotFoundException {
		if (!this.customerDao.existsById(customerId)) {
			throw new CustomerNotFoundException("customer not found for this id : " + customerId);
		}
	}

}
