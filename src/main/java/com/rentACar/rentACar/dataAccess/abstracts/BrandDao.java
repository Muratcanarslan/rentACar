package com.rentACar.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentACar.rentACar.entities.concretes.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer> {
	
	boolean existsByBrandName(String brandName);


}
