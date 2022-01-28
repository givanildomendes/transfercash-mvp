package br.com.itau.service;

import java.util.List;

import br.com.itau.exception.AccountBankIdAlreadyExistsException;
import br.com.itau.exception.BusinessRuleException;
import br.com.itau.exception.CustomerIdAlreadyExistsException;
import br.com.itau.exception.FailedException;
import br.com.itau.model.CustomerAccountBank;
public interface CustomerAccountBankService {

	void registerAccountBank(CustomerAccountBank accountBank) throws AccountBankIdAlreadyExistsException, CustomerIdAlreadyExistsException, FailedException, BusinessRuleException;
	List<CustomerAccountBank> getAllCustomerAccountBank() throws FailedException;
	CustomerAccountBank getCustomerAccountBankById(long id) throws FailedException, BusinessRuleException;
	
}