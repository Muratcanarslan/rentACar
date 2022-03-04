package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.brandDtos.BrandListDto;
import com.rentACar.rentACar.business.dtos.brandDtos.GetBrandDto;
import com.rentACar.rentACar.business.requests.brandRequests.CreateBrandRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface BrandService {
	
	DataResult<List<BrandListDto>> getAll() throws BusinessException;
	
	Result add(CreateBrandRequest createBrandRequest) throws BusinessException;
	
	DataResult<GetBrandDto> getById(int id) throws BusinessException;
	
	void checkIfIsExistsByBrandId(int id) throws BusinessException;

}
