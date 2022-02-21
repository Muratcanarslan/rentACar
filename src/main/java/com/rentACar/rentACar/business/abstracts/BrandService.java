package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.BrandListDto;
import com.rentACar.rentACar.business.dtos.GetBrandDto;
import com.rentACar.rentACar.business.requests.CreateBrandRequest;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface BrandService {
	
	DataResult<List<BrandListDto>> getAll();
	
	Result add(CreateBrandRequest createBrandRequest);
	
	DataResult<GetBrandDto> getById(int id);
	

}
