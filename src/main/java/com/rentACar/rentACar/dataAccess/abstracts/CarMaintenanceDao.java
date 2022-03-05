package com.rentACar.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentACar.rentACar.entities.concretes.CarMaintenance;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer>{
	
	boolean existsByCarMaintenanceId(int id);
	
	CarMaintenance getByCar_CarIdAndCarMaintenanceReturnDateIsNull(int id);
	
}
