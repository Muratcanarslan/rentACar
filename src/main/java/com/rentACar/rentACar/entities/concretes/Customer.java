package com.rentACar.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.rentACar.rentACar.core.entites.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "customer_id",referencedColumnName = "user_id")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "customers")
public class Customer extends User{
	
	//TODO: düzenle
	@Column(name = "customer_id",insertable = false,updatable = false)
	private int customerId;
	
	@Column(name = "date_registered")
	@CreatedDate
	private LocalDate dateRegistered;
	
	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
	private List<RentedCar> rentedCars;
	
	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
	private List<Payment> payments;
}
