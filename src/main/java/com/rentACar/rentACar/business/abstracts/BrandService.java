package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.brandDtos.BrandListDto;
import com.rentACar.rentACar.business.dtos.brandDtos.GetBrandDto;
import com.rentACar.rentACar.business.requests.brandRequests.CreateBrandRequest;
import com.rentACar.rentACar.core.utilities.exceptions.brandExceptions.BrandAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.brandExceptions.BrandNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface BrandService {

	DataResult<List<BrandListDto>> getAll();

	Result add(CreateBrandRequest createBrandRequest) throws BrandAlreadyExistsException;

	DataResult<GetBrandDto> getById(int id) throws BrandNotFoundException;

	void checkIfIsExistsByBrandId(int id) throws BrandNotFoundException;

}
