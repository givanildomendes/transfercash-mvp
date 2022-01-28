package br.com.itau.service;

import java.util.List;

import br.com.itau.exception.BusinessRuleException;
import br.com.itau.exception.FailedException;
import br.com.itau.model.CustomerAccountBankTransaction;
public interface CustomerAccountBankTransactionService {

	void registerCustomerAccountBankTransaction(CustomerAccountBankTransaction customerAccountBankTransaction) throws FailedException, BusinessRuleException;
	List<CustomerAccountBankTransaction> getAllCustomerAccountBankTransaction() throws FailedException;
	
}