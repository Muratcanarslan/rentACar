package com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceListRequest {

	private List<Integer> additionalServiceIds;

}
