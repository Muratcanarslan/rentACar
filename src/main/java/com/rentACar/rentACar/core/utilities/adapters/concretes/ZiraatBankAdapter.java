package com.rentACar.rentACar.core.utilities.adapters.concretes;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.core.utilities.adapters.abstracts.BankService;
import com.rentACar.rentACar.core.utilities.exceptions.bankServiceExceptions.PaymentNotSuccessfullException;
import com.rentACar.rentACar.core.utilities.postServices.ZiraatBankPostService;

@Service
public class ZiraatBankAdapter implements BankService {

	@Override
	public void addPayment(String cardNumber, String cvv, String name, double totalPrice) throws PaymentNotSuccessfullException {

		ZiraatBankPostService ziraatBankPostService = new ZiraatBankPostService();

		boolean isSuccessful = ziraatBankPostService.addPayment(cardNumber, cvv, name, totalPrice);
		
		checkIfPaymentIsSuccessfull(isSuccessful);
		
	}
	
	private void checkIfPaymentIsSuccessfull(boolean isSuccessfull) throws PaymentNotSuccessfullException {
		if(!isSuccessfull) {
			throw new PaymentNotSuccessfullException("payment is not successfull");
		}
	}

}
