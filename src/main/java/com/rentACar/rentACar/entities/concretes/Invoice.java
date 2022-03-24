package com.rentACar.rentACar.entities.concretes;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceId;

	@Column(name = "creation_date")
	@UpdateTimestamp
	private Date creationDate;

	@Column(name = "total_rent_days")
	private int totalRentDays;

	@Column(name = "total_price")
	private double totalPrice;

	@ManyToOne
	@JoinColumn(name = "rented_car_id")
	private RentedCar rentedCar;
	
	//TODO burayı yap şimdilik daha önemli yerler vardı bıraktım.
	/*
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
    */
	@OneToOne(mappedBy = "invoice")
	private Payment payment;
}
