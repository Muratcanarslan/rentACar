package com.rentACar.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentACar.rentACar.entities.concretes.Payment;

public interface PaymentDao extends JpaRepository<Payment, Integer> {
	
	boolean existsByInvoice_InvoiceId(int invoiceId);
}
