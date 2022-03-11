package com.rentACar.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentACar.rentACar.entities.concretes.RentDetails;

@Repository
public interface RentDetailsDao extends JpaRepository<RentDetails, Integer>{

}
