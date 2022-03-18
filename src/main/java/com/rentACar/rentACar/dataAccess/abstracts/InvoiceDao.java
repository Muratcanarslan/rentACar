package com.rentACar.rentACar.dataAccess.abstracts;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentACar.rentACar.entities.concretes.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

	void deleteAllByRentedCar_RentedCarId(int rentedCarId);

	boolean existsByRentedCar_RentedCarId(int rentedCarId);
	
	Invoice getByRentedCar_RentedCarId(int rentedCarId);

	List<Invoice> getByRentedCar_Customer_CustomerId(int customerId);
	
	List<Invoice> getByCreationDateBetween(Date startDate,Date endDate);
	
	
}
