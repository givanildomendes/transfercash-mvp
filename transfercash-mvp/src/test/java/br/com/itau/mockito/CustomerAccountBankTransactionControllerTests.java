package br.com.itau.mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.itau.exception.BusinessRuleException;
import br.com.itau.exception.FailedException;
import br.com.itau.junit.utils.TemplateMethod;
import br.com.itau.model.CustomerAccountBank;
import br.com.itau.model.CustomerAccountBankTransaction;
import br.com.itau.repository.CustomerAccountBankRepository;
import br.com.itau.repository.CustomerAccountBankTransactionRepository;
import br.com.itau.service.CustomerAccountBankService;
import br.com.itau.service.CustomerAccountBankTransactionService;
import br.com.itau.service.impl.CustomerAccountBankTransactionServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerAccountBankTransactionControllerTests extends TemplateMethod{
	private CustomerAccountBankTransactionService customerAccountBankTransactionService;
	
	private CustomerAccountBank accountBankOri;
	private CustomerAccountBank accountBankDest;
	private CustomerAccountBankTransaction transfer;
	
	@Mock
	private CustomerAccountBankService customerAccountBankService;

	@Mock
	private CustomerAccountBankRepository customerAccountBankRepository;

	@Mock
	private CustomerAccountBankTransactionRepository customerAccountBankTransactionRepository;

	@BeforeAll
	private static void init() {
		
	}

	@BeforeEach
	private void initForEach() {
		MockitoAnnotations.openMocks(this);
		this.customerAccountBankTransactionService = new CustomerAccountBankTransactionServiceImpl(
					customerAccountBankService,
					customerAccountBankRepository,
					customerAccountBankTransactionRepository);

		this.accountBankOri  = this.getCustomerAccountBankVO(1, "Jose",  1, "40");
		this.accountBankDest = this.getCustomerAccountBankVO(2, "Maria", 2, "40");

		this.transfer = new CustomerAccountBankTransaction();
		this.transfer.setCustomerAccountBankOri(accountBankOri);
		this.transfer.setCustomerAccountBankDest(accountBankDest);
		this.transfer.setDtTransferCash(LocalDateTime.now());
		this.transfer.setValueTransferCash( new BigDecimal("1001") );
		
		this.transfer.setIdAccountBankTransaction(1);
		this.transfer.setStatus("KO");
		this.transfer.setMessage("erro");

		try {
			Mockito.when(customerAccountBankService.getCustomerAccountBankById(1)).thenReturn(accountBankOri);
			Mockito.when(customerAccountBankService.getCustomerAccountBankById(2)).thenReturn(accountBankDest);
			Mockito.when(customerAccountBankRepository.save(accountBankOri)).thenReturn(accountBankOri);
			Mockito.when(customerAccountBankTransactionRepository.save(transfer)).thenReturn(transfer);
		} catch (FailedException | BusinessRuleException e) {
			fail("Ocorreu um problema ao mockar dados de retorno da conta do cliente." + e);
		}
	}
	
	@Test
	@Order(1) 	
	void realizarTransferenciaMaiorQueMilReais() {
		assertThrows(BusinessRuleException.class, 
				() -> customerAccountBankTransactionService.registerCustomerAccountBankTransaction(transfer));
	}

	@Test
	@Order(2) 	
	void realizarTransferenciaSaldoInsuficiente() {
		transfer.setValueTransferCash( new BigDecimal("900") );
		assertThrows(BusinessRuleException.class, 
				() -> customerAccountBankTransactionService.registerCustomerAccountBankTransaction(transfer));
	}

	@Test
	@Order(3) 	
	void realizarTransferenciaMenorOuIgualZero() {
		transfer.setValueTransferCash( new BigDecimal("-1") );
		assertThrows(BusinessRuleException.class, 
				() -> customerAccountBankTransactionService.registerCustomerAccountBankTransaction(transfer));
	}

	@Test
	@Order(4) 	
	void realizarTransferenciaDeContaOrigemInexistente() {		
		CustomerAccountBank accountBankOri  = this.getCustomerAccountBankVO(1, "Jose",  8888888, "40");
		transfer.setCustomerAccountBankOri(accountBankOri);
		transfer.setValueTransferCash( new BigDecimal("1") );
		
		assertThrows(BusinessRuleException.class, 
				() -> customerAccountBankTransactionService.registerCustomerAccountBankTransaction(transfer));
	}

	@Test
	@Order(5) 	
	void realizarTransferenciaDeContaDestinoInexistente() {		
		CustomerAccountBank accountBankDest = this.getCustomerAccountBankVO(2, "Maria", 8888888, "40");
		transfer.setCustomerAccountBankDest(accountBankDest);
		transfer.setValueTransferCash( new BigDecimal("1") );
		
		assertThrows(BusinessRuleException.class, 
				() -> customerAccountBankTransactionService.registerCustomerAccountBankTransaction(transfer));
	}

	@Test
	@Order(6) 	
	void realizarTransferenciaComSucesso() {
		transfer.setValueTransferCash( new BigDecimal("1") );
		try {
			customerAccountBankTransactionService.registerCustomerAccountBankTransaction(transfer);
			accountBankOri.setBalance( accountBankOri.getBalance().subtract(transfer.getValueTransferCash()));
			Mockito.verify(customerAccountBankRepository).save(accountBankOri);
			Mockito.verify(customerAccountBankTransactionRepository).save(transfer);
		} catch (Exception e) {
			fail("Ocorreu um problema inesperado." + e);
		}
	}

	@Test
	@Order(7) 	
	void listarTodasTransferenciasRealizadas() {
		List<CustomerAccountBankTransaction> list = new ArrayList<CustomerAccountBankTransaction>();
		list.add(transfer);
		Mockito.when(customerAccountBankTransactionRepository.findAll()).thenReturn(list);

		try {
			customerAccountBankTransactionService.getAllCustomerAccountBankTransaction();
			Mockito.verify(customerAccountBankTransactionRepository).findAll();
		} catch (FailedException e) {
			fail("Ocorreu um problema inesperado." + e);
		}
	}
}