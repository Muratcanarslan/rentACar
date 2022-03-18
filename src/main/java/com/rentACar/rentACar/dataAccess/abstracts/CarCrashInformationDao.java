package com.rentACar.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentACar.rentACar.entities.concretes.CarCrashInformation;

public interface CarCrashInformationDao extends JpaRepository<CarCrashInformation, Integer> {
	
	List<CarCrashInformation> getByCar_CarId(int carId);

}
