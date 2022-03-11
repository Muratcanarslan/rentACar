package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.AdditionalServiceService;
import com.rentACar.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.rentACar.rentACar.business.dtos.additionalServiceDtos.GetAdditionalServiceDto;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.rentACar.rentACar.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService{
	
	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;

	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		super();
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		checkIfAdditionalServiceAlreadyExistsByName(createAdditionalServiceRequest.getAdditionalServiceName());
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
		
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult("additional service added");
	}

	@Override
	public Result delete(int additionalServiceId) throws BusinessException {
		checkIfAdditionalServiceExists(additionalServiceId);
		
		this.additionalServiceDao.deleteById(additionalServiceId);
		
		return new SuccessResult("additional service deleted");
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
		checkIfAdditionalServiceExists(updateAdditionalServiceRequest.getAdditionalServiceId());
		
		AdditionalService additionalService = this.modelMapperService.forRequest()
				.map(updateAdditionalServiceRequest,AdditionalService.class);
		
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult("additional service updated");
	}

	@Override
	public DataResult<GetAdditionalServiceDto> getById(int additionalServiceId) throws BusinessException {
		checkIfAdditionalServiceExists(additionalServiceId);
		
		AdditionalService additionalService = this.additionalServiceDao.getById(additionalServiceId);
		
		GetAdditionalServiceDto getAdditionalServiceDto = this.modelMapperService.forDto().map(additionalService, GetAdditionalServiceDto.class);
		
		return new SuccessDataResult<GetAdditionalServiceDto>(getAdditionalServiceDto, "get by id");
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		List<AdditionalService> additionalServices = this.additionalServiceDao.findAll();
		
		List<AdditionalServiceListDto> additionalServiceListDtos = additionalServices.stream().map(
				additionalService -> this.modelMapperService.forDto().map(additionalService, AdditionalServiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<AdditionalServiceListDto>>(additionalServiceListDtos, "get all");
	}
	
	public void checkIfAdditionalServiceExists(int id) throws BusinessException {
		if(!this.additionalServiceDao.existsById(id)) {
			throw new BusinessException("Additional Service Not Found");
		}
	}
	
	public double calculateAdditionalServicePriceByAdditionalServiceIdListAndRentDateValue(List<Integer> additionalServiceIds,int rentDateValue) throws BusinessException {
		
		checkIfAdditionalServicesExists(additionalServiceIds);
		
		double additionalServicePrice = 0;
		
		for(int additionalId : additionalServiceIds) {
			AdditionalService additionalService = this.additionalServiceDao.getById(additionalId);
			additionalServicePrice += additionalService.getAdditionalServiceDailyPrice() * rentDateValue;
		}
		
		return additionalServicePrice;
	}
	
	public void checkIfAdditionalServicesExists(List<Integer> additionalServiceIds) throws BusinessException {
		for(int additionalId : additionalServiceIds) {
			checkIfAdditionalServiceExists(additionalId);
		}
	}
	
	private void checkIfAdditionalServiceAlreadyExistsByName(String name) throws BusinessException {
		if(this.additionalServiceDao.existsByAdditionalServiceName(name)) {
			throw new BusinessException("Additional Service Already Exists");
		}
	}
 

}
