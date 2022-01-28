package br.com.itau.junit.utils;

import java.math.BigDecimal;

import br.com.itau.model.Customer;
import br.com.itau.model.CustomerAccountBank;

public class TemplateMethod {

	protected CustomerAccountBank getCustomerAccountBankVO(long idCustomer, String name, long idAccountBank, String balance) {
		Customer customer = new Customer();
		customer.setName(name);
		customer.setIdCustomer(idCustomer);
		
		CustomerAccountBank customerAccountBank = new CustomerAccountBank();
		customerAccountBank.setCustomer(customer);
		customerAccountBank.setIdCustomerAccountBank(idAccountBank);
		customerAccountBank.setBalance(new BigDecimal(balance));
		return customerAccountBank;
	}
	
	protected CustomerAccountBank getCustomerAccountBankVO() {
		Customer customer = new Customer();
		customer.setName("Joao");
		customer.setIdCustomer(1);
		
		CustomerAccountBank customerAccountBank = new CustomerAccountBank();
		customerAccountBank.setCustomer(customer);
		customerAccountBank.setIdCustomerAccountBank(1);
		customerAccountBank.setBalance(new BigDecimal("40"));
		return customerAccountBank;
	}


}
