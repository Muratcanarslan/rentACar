package com.rentACar.rentACar.entities.concretes;

import java.time.LocalDate;

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
@Table(name = "car_maintenances")
public class CarMaintenance {
	
	//TODO verilen tarihide ekle saçma böyle

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_maintenance_id")
	private int carMaintenanceId;

	@Column(name = "car_maintenance_description")
	private String carMaintenanceDescription;

	@Column(name = "car_maintenance_return_date")
	private LocalDate carMaintenanceReturnDate;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

}
