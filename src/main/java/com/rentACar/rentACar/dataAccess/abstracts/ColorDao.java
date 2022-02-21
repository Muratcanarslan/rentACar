package com.rentACar.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentACar.rentACar.entities.concretes.Color;


@Repository
public interface ColorDao extends JpaRepository<Color, Integer>{
	
	boolean existsByColorName(String colorName);
	
}
