package com.rentACar.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


import com.rentACar.rentACar.core.entites.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "customer_id",referencedColumnName = "user_id")
@Table(name = "customers")
public class Customer extends User{
	
	@Column(name = "customer_id",insertable = false,updatable = false)
	private int customerId;
	
	
	@OneToMany(mappedBy = "customer")
	private List<RentedCar> rentedCars;
}
