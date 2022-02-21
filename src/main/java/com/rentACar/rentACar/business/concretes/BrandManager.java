package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.BrandService;
import com.rentACar.rentACar.business.dtos.BrandListDto;
import com.rentACar.rentACar.business.dtos.GetBrandDto;
import com.rentACar.rentACar.business.requests.CreateBrandRequest;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.ErrorDataResult;
import com.rentACar.rentACar.core.utilities.results.ErrorResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.BrandDao;
import com.rentACar.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {
		List<Brand> brands = this.brandDao.findAll();
		
		if(brands.isEmpty()) {
			return new ErrorDataResult<List<BrandListDto>>("No brand finded");
		}

		List<BrandListDto> result = brands.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<BrandListDto>>(result,"Brands");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {

		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		if (!isExistByBrandName(brand.getBrandName())) {
			this.brandDao.save(brand);
			return new SuccessResult("Brand added");
		}
		
		return new ErrorResult("Brand is exist");
	}

	
	@Override
	public DataResult<GetBrandDto> getById(int id) {
		
		if(!isExistsByBrandId(id)) {
			return new ErrorDataResult<GetBrandDto>("Brand is not exist");
		}
		
		Brand brand = this.brandDao.getById(id);
		
		GetBrandDto getBrandDto = this.modelMapperService.forDto().map(brand, GetBrandDto.class);
		
		return new SuccessDataResult<GetBrandDto>(getBrandDto,"get by id brand");
		
	}

	
	public boolean isExistByBrandName(String name) {
		return this.brandDao.existsByBrandName(name);
	}
	
	public boolean isExistsByBrandId(int id) {
		return this.brandDao.existsById(id);
	}

}
