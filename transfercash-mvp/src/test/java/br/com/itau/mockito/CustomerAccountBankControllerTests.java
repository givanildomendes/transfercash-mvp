package br.com.itau.mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.itau.exception.AccountBankIdAlreadyExistsException;
import br.com.itau.exception.BusinessRuleException;
import br.com.itau.exception.CustomerIdAlreadyExistsException;
import br.com.itau.exception.FailedException;
import br.com.itau.junit.utils.TemplateMethod;
import br.com.itau.model.CustomerAccountBank;
import br.com.itau.model.CustomerAccountBankTransaction;
import br.com.itau.repository.CustomerAccountBankRepository;
import br.com.itau.repository.CustomerRepository;
import br.com.itau.service.CustomerAccountBankService;
import br.com.itau.service.impl.CustomerAccountBankServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerAccountBankControllerTests extends TemplateMethod{
	private CustomerAccountBankService customerAccountBankService;

	@Mock
	private CustomerAccountBankRepository customerAccountBankRepository ;

	@Mock
	private CustomerRepository customerRepository;

	private CustomerAccountBank customerAccountBank;

	@BeforeEach
	private void initForEach() {
		MockitoAnnotations.openMocks(this);
		this.customerAccountBankService = new CustomerAccountBankServiceImpl(customerAccountBankRepository,	customerRepository);

		this.customerAccountBank = getCustomerAccountBankVO();

		//Cadastrar cliente com sucesso
		Mockito.when(customerRepository.findByIdCustomer(100L)).thenReturn(null);
		Mockito.when(customerAccountBankRepository.findByIdCustomerAccountBank(100L)).thenReturn(null);

		//Cliente/conta ja existe
		Mockito.when(customerRepository.findByIdCustomer(200L)).thenReturn(customerAccountBank.getCustomer());
		Mockito.when(customerAccountBankRepository.findByIdCustomerAccountBank(200L)).thenReturn(customerAccountBank);

		Mockito.when(customerAccountBankRepository.findByIdCustomerAccountBank(customerAccountBank.getIdCustomerAccountBank()))
				.thenReturn(customerAccountBank);
		

		Mockito.when(customerRepository.findByIdCustomer(customerAccountBank.getCustomer().getIdCustomer()))
		.thenReturn(customerAccountBank.getCustomer());

		Mockito.when(customerAccountBankRepository.save(customerAccountBank)).thenReturn(customerAccountBank);
	}

	
	@Test
	@Order(1) 	
	void cadastrarClienteComSucesso() {
		try {
			customerAccountBank.getCustomer().setIdCustomer(100);
			customerAccountBank.setIdCustomerAccountBank(100);
			customerAccountBankService.registerAccountBank(customerAccountBank);
			Mockito.verify(customerAccountBankRepository).save(customerAccountBank);
		} catch (Exception e) {
			fail("Ocorreu um problema inesperado." + e);
		}
	}

	@Test
	@Order(2) 	
	void cadastrarClienteComErroIdDoClienteJaExistente() {
		customerAccountBank.getCustomer().setIdCustomer(200L);
		customerAccountBank.setIdCustomerAccountBank(100L);
		assertThrows(CustomerIdAlreadyExistsException.class, 
				() -> customerAccountBankService.registerAccountBank(customerAccountBank));
	}

	@Test
	@Order(3) 	
	void cadastrarClienteComErroNumeroDaContaJaExistente() {
		customerAccountBank.getCustomer().setIdCustomer(100L);
		customerAccountBank.setIdCustomerAccountBank(200L);
		assertThrows(AccountBankIdAlreadyExistsException.class, 
				() -> customerAccountBankService.registerAccountBank(customerAccountBank));
	}

	@Test
	@Order(4) 	
	void listarTodosClientesComSucesso() {
		List<CustomerAccountBank> list = new ArrayList<CustomerAccountBank>();
		list.add(customerAccountBank);
		Mockito.when(customerAccountBankRepository.findAll()).thenReturn(list);

		try {
			customerAccountBankService.getAllCustomerAccountBank();
			Mockito.verify(customerAccountBankRepository).findAll();
		} catch (FailedException e) {
			fail("Ocorreu um problema inesperado." + e);
		}
	}

	@Test
	@Order(5) 	
	void cadastrarClienteComErroOndeIdCustomerAccountBankMenorIgualZero() {
		customerAccountBank.getCustomer().setIdCustomer(200L);
		customerAccountBank.setIdCustomerAccountBank(0L);
		assertThrows(BusinessRuleException.class, 
				() -> customerAccountBankService.registerAccountBank(customerAccountBank));
	}

	@Test
	@Order(6) 	
	void cadastrarClienteComErroOndeIdCustomerMenorIgualZero() {
		customerAccountBank.getCustomer().setIdCustomer(0L);
		customerAccountBank.setIdCustomerAccountBank(200L);
		assertThrows(BusinessRuleException.class, 
				() -> customerAccountBankService.registerAccountBank(customerAccountBank));
	}

	@Test
	@Order(7) 	
	void listarClientePeloNumeroDaContaComSucesso() {
		Mockito.when(customerAccountBankRepository.findByIdCustomerAccountBank(1L)).thenReturn(customerAccountBank);
		customerAccountBank.setIdCustomerAccountBank(1L);
		try {
			customerAccountBankService.getAllCustomerAccountBank();
			Mockito.verify(customerAccountBankRepository).findAll();
		} catch (FailedException e) {
			fail("Ocorreu um problema inesperado." + e);
		}
	}
	
}