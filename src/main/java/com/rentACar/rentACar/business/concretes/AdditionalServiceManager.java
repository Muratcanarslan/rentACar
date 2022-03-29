package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.AdditionalServiceService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.rentACar.rentACar.business.dtos.additionalServiceDtos.GetAdditionalServiceDto;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.rentACar.rentACar.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;

	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		super();
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest)
			throws AdditionalServiceAlreadyExistsException {
		checkIfAdditionalServiceAlreadyExistsByName(createAdditionalServiceRequest.getAdditionalServiceName());

		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,
				AdditionalService.class);

		this.additionalServiceDao.save(additionalService);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest)
			throws AdditionalServiceNotFoundException {

		checkIfAdditionalServiceExists(deleteAdditionalServiceRequest.getAdditionalServiceId());

		this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getAdditionalServiceId());

		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest)
			throws AdditionalServiceNotFoundException {
		checkIfAdditionalServiceExists(updateAdditionalServiceRequest.getAdditionalServiceId());

		AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,
				AdditionalService.class);

		this.additionalServiceDao.save(additionalService);

		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);
	}

	@Override
	public DataResult<GetAdditionalServiceDto> getById(int additionalServiceId)
			throws AdditionalServiceNotFoundException {

		checkIfAdditionalServiceExists(additionalServiceId);

		AdditionalService additionalService = this.additionalServiceDao.getById(additionalServiceId);

		GetAdditionalServiceDto getAdditionalServiceDto = this.modelMapperService.forDto().map(additionalService,
				GetAdditionalServiceDto.class);

		return new SuccessDataResult<GetAdditionalServiceDto>(getAdditionalServiceDto, BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<AdditionalService> additionalServices = this.additionalServiceDao.findAll(pageable).getContent();

		List<AdditionalServiceListDto> additionalServiceListDtos = additionalServices.stream()
				.map(additionalService -> this.modelMapperService.forDto().map(additionalService,
						AdditionalServiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<AdditionalServiceListDto>>(additionalServiceListDtos,
				BusinessMessages.GET_SUCCESSFUL);
	}

	public double calculateAdditionalServicePriceByAdditionalServiceIdListAndRentDateValue(
			List<Integer> additionalServiceIds, int rentDateValue) throws AdditionalServiceNotFoundException {

		checkIfAdditionalServicesExists(additionalServiceIds);

		double additionalServicePrice = 0;

		for (int additionalId : additionalServiceIds) {
			AdditionalService additionalService = this.additionalServiceDao.getById(additionalId);
			additionalServicePrice += additionalService.getAdditionalServiceDailyPrice() * rentDateValue;
		}

		return additionalServicePrice;
	}

	public void checkIfAdditionalServicesExists(List<Integer> additionalServiceIds)
			throws AdditionalServiceNotFoundException {
		for (int additionalId : additionalServiceIds) {
			checkIfAdditionalServiceExists(additionalId);
		}
	}

	private void checkIfAdditionalServiceAlreadyExistsByName(String name)
			throws AdditionalServiceAlreadyExistsException {
		if (this.additionalServiceDao.existsByAdditionalServiceName(name)) {
			throw new AdditionalServiceAlreadyExistsException(BusinessMessages.ADDITIONAL_SERVİCE_ALREADY_EXISTS);
		}
	}

	public void checkIfAdditionalServiceExists(int id) throws AdditionalServiceNotFoundException {
		if (!this.additionalServiceDao.existsById(id)) {
			throw new AdditionalServiceNotFoundException(BusinessMessages.ADDITIONAL_SERVICE_NOT_FOUND + id);
		}
	}

}
