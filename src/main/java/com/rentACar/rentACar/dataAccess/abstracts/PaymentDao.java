package com.rentACar.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentACar.rentACar.entities.concretes.Payment;

public interface PaymentDao extends JpaRepository<Payment, Integer> {
	
	boolean existsByInvoice_InvoiceId(int invoiceId);
	
	List<Payment> getByCustomer_CustomerId(int customerId);
}
