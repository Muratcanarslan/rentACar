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
@Table(name = "car_crash_informations")
public class CarCrashInformation {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name = "car_crash_information_id")
	private int carCrashInformationId;
	
	@Column(name = "car_crahs_details")
	private String carCrashDetails;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

}
