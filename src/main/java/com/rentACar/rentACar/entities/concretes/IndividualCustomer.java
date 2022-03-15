package com.rentACar.rentACar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "individual_customer_id",referencedColumnName = "customer_id")
@EqualsAndHashCode(callSuper = false)
@Table(name = "individualCustomers")
public class IndividualCustomer extends Customer {
	
	@Column(name = "individual_customer_id",insertable = false,updatable = false)
	private int individualCustomerId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "national_idenditity")
	private String nationalIdentity;
}
