package com.rentACar.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentACar.rentACar.core.entites.User;

@Repository
public interface UserDao extends JpaRepository<User,Integer>{
	
	boolean existsByEmail(String email);

}
