package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.IndividualCustomerService;
import com.rentACar.rentACar.business.abstracts.UserService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.individualCustomerDtos.GetIndividualCustomerDto;
import com.rentACar.rentACar.business.dtos.individualCustomerDtos.IndividualCustomerListDto;
import com.rentACar.rentACar.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.rentACar.rentACar.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.rentACar.rentACar.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerAlreadyExistsByNationalIdentityException;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.userExceptions.UserAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.rentACar.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	private UserService userService;

	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService,
			UserService userService) {
		super();
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
		this.userService = userService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest)
			throws UserAlreadyExistsException, IndividualCustomerAlreadyExistsByNationalIdentityException {

		checkIfCustomerAlreadyExistsByEmail(createIndividualCustomerRequest.getEmail());
		checkIfIndividualCustomerAlreadyExistsByNationalIdentity(createIndividualCustomerRequest.getNationalIdentity());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(createIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest)
			throws IndividualCustomerNotFoundException, UserAlreadyExistsException,
			IndividualCustomerAlreadyExistsByNationalIdentityException {

		checkIfIndividualCustomerExistsById(updateIndividualCustomerRequest.getIndividualCustomerId());
		checkIfCustomerAlreadyExistsByEmail(updateIndividualCustomerRequest.getEmail());
		checkIfIndividualCustomerAlreadyExistsByNationalIdentity(updateIndividualCustomerRequest.getNationalIdentity());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest)
			throws IndividualCustomerNotFoundException {

		checkIfIndividualCustomerExistsById(deleteIndividualCustomerRequest.getIndividualCustomerId());

		this.individualCustomerDao.deleteById(deleteIndividualCustomerRequest.getIndividualCustomerId());

		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(int individualCustomerId)
			throws IndividualCustomerNotFoundException {
		checkIfIndividualCustomerExistsById(individualCustomerId);

		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(individualCustomerId);

		GetIndividualCustomerDto getIndividualCustomerDto = this.modelMapperService.forDto().map(individualCustomer,
				GetIndividualCustomerDto.class);

		return new SuccessDataResult<GetIndividualCustomerDto>(getIndividualCustomerDto,
				BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> getAll(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll(pageable).getContent();

		List<IndividualCustomerListDto> individualCustomerListDtos = individualCustomers.stream()
				.map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer,
						IndividualCustomerListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<IndividualCustomerListDto>>(individualCustomerListDtos,
				BusinessMessages.GET_SUCCESSFUL);

	}

	private void checkIfCustomerAlreadyExistsByEmail(String email) throws UserAlreadyExistsException {
		this.userService.checkIfUserAlreadyExistsByEmail(email);
	}

	private void checkIfIndividualCustomerAlreadyExistsByNationalIdentity(String nationalIdentity)
			throws IndividualCustomerAlreadyExistsByNationalIdentityException {
		if (this.individualCustomerDao.existsByNationalIdentity(nationalIdentity)) {
			throw new IndividualCustomerAlreadyExistsByNationalIdentityException(
					BusinessMessages.INDIVIDUAL_CUSTOMER_EXISTS_BY_NATIONAL_IDENTITY + nationalIdentity);
		}
	}

	public void checkIfIndividualCustomerExistsById(int individualCustomerId)
			throws IndividualCustomerNotFoundException {
		if (!this.individualCustomerDao.existsById(individualCustomerId)) {
			throw new IndividualCustomerNotFoundException(
					BusinessMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND + individualCustomerId);
		}
	}
}
