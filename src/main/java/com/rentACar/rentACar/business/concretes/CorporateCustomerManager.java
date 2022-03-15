package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CorporateCustomerService;
import com.rentACar.rentACar.business.abstracts.UserService;
import com.rentACar.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.rentACar.rentACar.business.dtos.corporateCustomerDtos.GetCorporateCustomerDto;
import com.rentACar.rentACar.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.rentACar.rentACar.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerAlreadyExistsByTaxNumberException;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.userExceptions.UserAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.rentACar.rentACar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;
	private UserService userService;

	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService,
			UserService userService) {
		super();
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
		this.userService = userService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest)
			throws UserAlreadyExistsException, CorporateCustomerAlreadyExistsByTaxNumberException {

		checkIfCorporateCustomerAlreadyExistsByEmail(createCorporateCustomerRequest.getEmail());
		checkIfCorporateCustomerAlreadyExistsByTaxNumber(createCorporateCustomerRequest.getTaxNumber());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult("corporate customer added");

	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest)
			throws UserAlreadyExistsException, CorporateCustomerAlreadyExistsByTaxNumberException,
			CorporateCustomerNotFoundException {

		checkIfCorporateCustomerExists(updateCorporateCustomerRequest.getCorporateCustomerId());
		checkIfCorporateCustomerAlreadyExistsByEmail(updateCorporateCustomerRequest.getEmail());
		checkIfCorporateCustomerAlreadyExistsByTaxNumber(updateCorporateCustomerRequest.getTaxNumber());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult("corporate customer updated");
	}

	@Override
	public Result delete(int corporateCustomerId) throws CorporateCustomerNotFoundException {

		checkIfCorporateCustomerExists(corporateCustomerId);

		this.corporateCustomerDao.deleteById(corporateCustomerId);

		return new SuccessResult("corporate customer deleted");
	}

	@Override
	public DataResult<GetCorporateCustomerDto> getById(int corporateCustomerId)
			throws CorporateCustomerNotFoundException {

		checkIfCorporateCustomerExists(corporateCustomerId);

		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(corporateCustomerId);

		GetCorporateCustomerDto getCorporateCustomerDto = this.modelMapperService.forDto().map(corporateCustomer,
				GetCorporateCustomerDto.class);

		return new SuccessDataResult<GetCorporateCustomerDto>(getCorporateCustomerDto, "get corporate customer");
	}

	@Override
	public DataResult<List<CorporateCustomerListDto>> getAll() {

		List<CorporateCustomer> corporateCustomers = this.corporateCustomerDao.findAll();

		List<CorporateCustomerListDto> corporateCustomerListDtos = corporateCustomers.stream()
				.map(corporateCustomer -> this.modelMapperService.forDto().map(corporateCustomer,
						CorporateCustomerListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CorporateCustomerListDto>>(corporateCustomerListDtos, "corporate customers");

	}

	private void checkIfCorporateCustomerAlreadyExistsByEmail(String email) throws UserAlreadyExistsException {
		this.userService.checkIfUserAlreadyExistsByEmail(email);
	}

	private void checkIfCorporateCustomerAlreadyExistsByTaxNumber(String taxNumber)
			throws CorporateCustomerAlreadyExistsByTaxNumberException {
		if (this.corporateCustomerDao.existsByTaxNumber(taxNumber)) {
			throw new CorporateCustomerAlreadyExistsByTaxNumberException(
					"this corporate customer tax number already exists : " + taxNumber);
		}
	}

	private void checkIfCorporateCustomerExists(int corporateCustomerId) throws CorporateCustomerNotFoundException {
		if (!this.corporateCustomerDao.existsById(corporateCustomerId)) {
			throw new CorporateCustomerNotFoundException(
					"corporate customer not found for this id" + corporateCustomerId);
		}
	}

}