package br.com.itau.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itau.exception.AccountBankIdAlreadyExistsException;
import br.com.itau.exception.BusinessRuleException;
import br.com.itau.exception.CustomerIdAlreadyExistsException;
import br.com.itau.exception.FailedException;
import br.com.itau.model.Customer;
import br.com.itau.model.CustomerAccountBank;
import br.com.itau.repository.CustomerAccountBankRepository;
import br.com.itau.repository.CustomerRepository;
import br.com.itau.service.CustomerAccountBankService;

@Service
public class CustomerAccountBankServiceImpl implements CustomerAccountBankService{

	private CustomerAccountBankRepository customerAccountBankRepository ;
	private CustomerRepository customerRepository ;

	@Autowired
	public CustomerAccountBankServiceImpl(	CustomerAccountBankRepository customerAccountBankRepository,
											CustomerRepository customerRepository) {
		this.customerAccountBankRepository	= customerAccountBankRepository;
		this.customerRepository				= customerRepository;
	}
	
	@Override
	@Transactional(value = TxType.REQUIRED)
	public void registerAccountBank(CustomerAccountBank customerAccountBank) throws AccountBankIdAlreadyExistsException, CustomerIdAlreadyExistsException, FailedException, BusinessRuleException  {
		try{

			if (customerAccountBank.getCustomer().getIdCustomer()<=0) {
				throw new BusinessRuleException("Id do cliente inválido.");
			}

			if (customerAccountBank.getIdCustomerAccountBank()<=0) {
				throw new BusinessRuleException("Id da conta inválido.");
			}

			Customer custFound = customerRepository.findByIdCustomer(customerAccountBank.getCustomer().getIdCustomer());
			if ( custFound != null ) {
				throw new CustomerIdAlreadyExistsException("Id do cliente já existe no cadastro.");
			}

			CustomerAccountBank accFound = customerAccountBankRepository.findByIdCustomerAccountBank(customerAccountBank.getIdCustomerAccountBank());
			if ( accFound != null ) {
				throw new AccountBankIdAlreadyExistsException("Numero da conta já existe no cadastro.");
			}
			
			customerAccountBankRepository.save(customerAccountBank);
		}catch (CustomerIdAlreadyExistsException | AccountBankIdAlreadyExistsException | BusinessRuleException  e) {
			throw e;
		}catch (Exception e) {
			throw new FailedException("Ocorreu um erro inesperado ao cadastrar o cliente.", e) ;
		} 
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public List<CustomerAccountBank> getAllCustomerAccountBank() throws FailedException {
		try {
			List<CustomerAccountBank> accounts = (List<CustomerAccountBank>) customerAccountBankRepository.findAll();
			return accounts;
		} catch (Exception e) {
			throw new FailedException("Ocorreu um erro inesperado ao listar os clientes cadastrados.", e);
		} 
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public synchronized CustomerAccountBank getCustomerAccountBankById(long id) throws FailedException, BusinessRuleException  {
		try {
			if (id<=0) {
				throw new BusinessRuleException("Informe um número válido para pesquisa.");
			}
			
			CustomerAccountBank account = customerAccountBankRepository.findByIdCustomerAccountBank(id);
			if ( account == null ) {
				throw new FailedException("Conta " +id+" não existe no cadastro.");
			}

			return account;
		} catch (Exception e) {
			throw new FailedException("Ocorreu um erro inesperado na busca da conta "+id+"."+e, e);
		}
	}
}