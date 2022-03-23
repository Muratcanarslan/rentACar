package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.BrandService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.brandDtos.BrandListDto;
import com.rentACar.rentACar.business.dtos.brandDtos.GetBrandDto;
import com.rentACar.rentACar.business.requests.brandRequests.CreateBrandRequest;
import com.rentACar.rentACar.core.utilities.exceptions.brandExceptions.BrandAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.brandExceptions.BrandNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.BrandDao;
import com.rentACar.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {
		List<Brand> brands = this.brandDao.findAll();

		List<BrandListDto> result = brands.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<BrandListDto>>(result, "Brands");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BrandAlreadyExistsException {

		checkIfIsExistByBrandName(createBrandRequest.getBrandName());

		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		this.brandDao.save(brand);

		return new SuccessResult("Brand added");
	}

	@Override
	public DataResult<GetBrandDto> getById(int id) throws BrandNotFoundException {

		checkIfIsExistsByBrandId(id);

		Brand brand = this.brandDao.getById(id);

		GetBrandDto getBrandDto = this.modelMapperService.forDto().map(brand, GetBrandDto.class);

		return new SuccessDataResult<GetBrandDto>(getBrandDto, "get by id brand");

	}

	public void checkIfIsExistByBrandName(String name) throws BrandAlreadyExistsException {
		if (this.brandDao.existsByBrandName(name)) {
			throw new BrandAlreadyExistsException(BusinessMessages.BRAND_ALREADY_EXISTS);
		}
	}

	public void checkIfIsExistsByBrandId(int id) throws BrandNotFoundException {
		if (!this.brandDao.existsById(id)) {
			throw new BrandNotFoundException(BusinessMessages.BRAND_NOT_FOUND+id);
		}
	}

}
