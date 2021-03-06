package com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceListRequest {

	@NotNull
	private List<Integer> additionalServiceIds;

}
