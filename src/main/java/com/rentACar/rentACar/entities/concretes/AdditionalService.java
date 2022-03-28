package com.rentACar.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "additional_services")
public class AdditionalService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "additional_service_id")
	private int additionalServiceId;
	
	@Column(name = "additional_service_name")
	private String additionalServiceName;
	
	@Column(name = "additional_service_daily_price")
	private double additionalServiceDailyPrice;
	
	//TODO: SOR BUNU.
	@OneToMany(mappedBy = "additionalService",cascade = CascadeType.ALL)
	private List<OrderedAdditionalService> orderedAdditonalServices;
	
}
