package com.rentACar.rentACar.entities.concretes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "rented_cars")
public class RentedCar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rented_car_id")
	private int rentedCarId;
	
	@Column(name = "rent_date")
	private Date rentDate;
	
	@Column(name = "return_date")
	private Date returnDate;
	
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

}
