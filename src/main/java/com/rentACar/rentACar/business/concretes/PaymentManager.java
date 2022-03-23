package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.CustomerService;
import com.rentACar.rentACar.business.abstracts.InvoiceService;
import com.rentACar.rentACar.business.abstracts.PaymentService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.paymentDtos.GetPaymentDto;
import com.rentACar.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.rentACar.rentACar.business.requests.paymentRequests.CreatePaymentRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.paymentExceptions.PaymentAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.PaymentDao;
import com.rentACar.rentACar.entities.concretes.Customer;
import com.rentACar.rentACar.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	private RentedCarService rentedCarService;
	private InvoiceService invoiceService;

	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, CustomerService customerService,
			RentedCarService rentedCarService, InvoiceService invoiceService) {
		super();
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.rentedCarService = rentedCarService;
		this.invoiceService = invoiceService;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException {

		this.customerService.checkIfCustomerExists(createPaymentRequest.getCustomer_CustomerId());
		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(createPaymentRequest.getRentedCar_RentedCarId());
		this.invoiceService.checkIfInvoiceExists(createPaymentRequest.getInvoice_InvoiceId());
		checkIfPaymentAlreadyExistsForInvoiceId(createPaymentRequest.getInvoice_InvoiceId());

		Customer customer = this.customerService.getCustomerById(createPaymentRequest.getCustomer_CustomerId());

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

		payment.setCustomer(customer);

		this.paymentDao.save(payment);

		return new SuccessResult("payment added");
	}

	@Override
	public DataResult<GetPaymentDto> getById(int paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {

		List<Payment> payments = this.paymentDao.findAll();

		List<PaymentListDto> paymentListDtos = payments.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<PaymentListDto>>(paymentListDtos, "payments");

	}

	private void checkIfPaymentAlreadyExistsForInvoiceId(int invoiceId) throws PaymentAlreadyExistsException {
		if (this.paymentDao.existsByInvoice_InvoiceId(invoiceId)) {
			throw new PaymentAlreadyExistsException(BusinessMessages.PAYMENT_ALREADY_EXISTS_BY_INVOICE_ID+ invoiceId);
		}
	}

}
