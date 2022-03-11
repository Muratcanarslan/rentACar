package com.rentACar.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;import com.rentACar.rentACar.entities.concretes.OrderedAdditionalService;

public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService, Integer>{
	
	List<OrderedAdditionalService> getByRentedCar_RentedCarId(int rentedCarId);
	void deleteAllByRentedCar_RentedCarId(int rentedCarId);
}
