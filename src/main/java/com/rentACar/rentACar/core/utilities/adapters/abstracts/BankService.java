package com.rentACar.rentACar.core.utilities.adapters.abstracts;

import com.rentACar.rentACar.core.utilities.exceptions.bankServiceExceptions.PaymentNotSuccessfullException;

public interface BankService {
	void addPayment(String cardNumber,String cvv,String name,double totalPrice) throws PaymentNotSuccessfullException;
}
