package com.rentACar.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@Column(name = "return_date")
	private LocalDate returnDate;

	@Column(name = "rent_date")
	private LocalDate rentDate;

	@Column(name = "confirmed_paid_date")
	private LocalDate confirmedPaidedDate;

	@Column(name = "rent_kilometre")
	private double rentKilometre;

	@Column(name = "return_kilometre")
	private Double returnKilometre;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "hire_city_id")
	private City hireCity;

	@ManyToOne
	@JoinColumn(name = "return_city_id")
	private City returnCity;

	@OneToMany(mappedBy = "rentedCar")
	private List<OrderedAdditionalService> orderedAdditionalServices;

	@OneToMany(mappedBy = "rentedCar",cascade = CascadeType.ALL)
	private List<Invoice> invoice;
	
	@OneToMany(mappedBy = "rentedCar",cascade = CascadeType.ALL)
	private List<Payment> payments;

}
