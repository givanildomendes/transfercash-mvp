package br.com.itau.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.itau.junit.utils.TemplateMethod;
import br.com.itau.model.CustomerAccountBank;
import br.com.itau.model.CustomerAccountBankTransaction;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerAccountBankTransactionControllerTests extends TemplateMethod{

	@Value("${api.context.customeraccount.transfer.list}")
	private String contextCustomerAccountTransferList;

	@Value("${api.context.customeraccount.transfer.cash}")
	private String contextCustomerAccountTransferCash;

	@Value("${api.context.general}")
	private String contextGeneral;

	@Value("${server.port}")
	private String port;
	
	@Value("${api.context.url.host}")
	private String host;
	
	@Value("${api.context.protocol.http}")
	private String http;

	private RestTemplateBuilder restTemplateBuilder;
	private TestRestTemplate testRestTemplate;

	private CustomerAccountBank accountBankOri;
	private CustomerAccountBank accountBankDest;

	private CustomerAccountBankTransaction transfer;

	@BeforeEach
	public void init() {
		this.restTemplateBuilder = new RestTemplateBuilder();
		this.testRestTemplate 	 = new TestRestTemplate(restTemplateBuilder);

		this.accountBankOri  = this.getCustomerAccountBankVO(1, "Jose",  1, "40");
		this.accountBankDest = this.getCustomerAccountBankVO(2, "Maria", 2, "40");

		this.transfer = new CustomerAccountBankTransaction();
		this.transfer.setCustomerAccountBankOri(accountBankOri);
		this.transfer.setCustomerAccountBankDest(accountBankDest);
		this.transfer.setDtTransferCash(LocalDateTime.now());
		this.transfer.setValueTransferCash( new BigDecimal("1001") );
	}
	
	@Test
	@Order(1) 	
	void realizarTransferenciaMaiorQueMilReais() {
		final String URL_TRANSFER_CASH = http+host+":"+port+contextGeneral + contextCustomerAccountTransferCash;
		transfer.setValueTransferCash( new BigDecimal("1001") );
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_TRANSFER_CASH, transfer,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	@Order(2) 	
	void realizarTransferenciaSaldoInsuficiente() {
		final String URL_TRANSFER_CASH = http+host+":"+port+contextGeneral + contextCustomerAccountTransferCash;
		transfer.setValueTransferCash( new BigDecimal("900") );
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_TRANSFER_CASH, transfer,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	@Order(3) 	
	void realizarTransferenciaMenorOuIgualZero() {
		final String URL_TRANSFER_CASH = http+host+":"+port+contextGeneral + contextCustomerAccountTransferCash;
		transfer.setValueTransferCash( new BigDecimal("-1") );
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_TRANSFER_CASH, transfer,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	@Order(4) 	
	void realizarTransferenciaDeContaOrigemInexistente() {
		final String URL_TRANSFER_CASH = http+host+":"+port+contextGeneral + contextCustomerAccountTransferCash;
		
		CustomerAccountBank accountBankOri  = this.getCustomerAccountBankVO(1, "Jose",  8888888, "40");
		transfer.setCustomerAccountBankOri(accountBankOri);
		transfer.setValueTransferCash( new BigDecimal("1") );
		
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_TRANSFER_CASH, transfer,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	@Order(5) 	
	void realizarTransferenciaDeContaDestinoInexistente() {
		final String URL_TRANSFER_CASH = http+host+":"+port+contextGeneral + contextCustomerAccountTransferCash;
		
		CustomerAccountBank accountBankDest = this.getCustomerAccountBankVO(2, "Maria", 8888888, "40");
		transfer.setCustomerAccountBankDest(accountBankDest);
		transfer.setValueTransferCash( new BigDecimal("1") );
		
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_TRANSFER_CASH, transfer,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	@Order(6) 	
	void realizarTransferenciaComSucesso() {
		final String URL_TRANSFER_CASH = http+host+":"+port+contextGeneral + contextCustomerAccountTransferCash;
		transfer.setValueTransferCash( new BigDecimal("1") );
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_TRANSFER_CASH, transfer,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.OK);
	}

	@Test
	@Order(7) 	
	void listarTodasTransferenciasRealizadas() {
		final String URL_TRANSFER_LIST = http+host+":"+port+contextGeneral + contextCustomerAccountTransferList;
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_TRANSFER_LIST, null,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.OK );
	}

}