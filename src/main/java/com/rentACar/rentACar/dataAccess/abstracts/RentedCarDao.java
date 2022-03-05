package com.rentACar.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentACar.rentACar.entities.concretes.RentedCar;

@Repository
public interface RentedCarDao extends JpaRepository<RentedCar, Integer> {
	
	RentedCar getByCar_CarIdAndReturnDateIsNull(int carId);
}
