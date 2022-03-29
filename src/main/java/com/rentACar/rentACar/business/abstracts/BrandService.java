package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.brandDtos.BrandListDto;
import com.rentACar.rentACar.business.dtos.brandDtos.GetBrandDto;
import com.rentACar.rentACar.business.requests.brandRequests.CreateBrandRequest;
import com.rentACar.rentACar.business.requests.brandRequests.DeleteBrandRequest;
import com.rentACar.rentACar.business.requests.brandRequests.UpdateBrandRequest;
import com.rentACar.rentACar.core.utilities.exceptions.brandExceptions.BrandAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.brandExceptions.BrandNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface BrandService {

	DataResult<List<BrandListDto>> getAll(int pageNo,int pageSize);

	Result add(CreateBrandRequest createBrandRequest) throws BrandAlreadyExistsException;
	
	Result update(UpdateBrandRequest updateBrandRequest) throws BrandNotFoundException, BrandAlreadyExistsException;
	
	Result delete(DeleteBrandRequest deleteBrandRequest) throws BrandNotFoundException;
	
	DataResult<GetBrandDto> getById(int id) throws BrandNotFoundException;

	void checkIfIsExistsByBrandId(int id) throws BrandNotFoundException;

}
