package com.rentACar.rentACar.entities.concretes;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_card_information")
public class CreditCardInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "credit_card_information_id")
	private int creditCardInformationId;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "cvv")
	private String CVV;

	@Column(name = "card_expire_date")
	private String cardExpireDate;

	@Column(name = "owner_name")
	private String ownerName;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

}
