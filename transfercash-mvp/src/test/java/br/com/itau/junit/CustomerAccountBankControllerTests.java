package br.com.itau.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerAccountBankControllerTests extends TemplateMethod{

	@Value("${api.context.customeraccount}")
	private String contextCustomerAccount;

	@Value("${api.context.customeraccount.list}")
	private String contextCustomerAccountList;

	@Value("${api.context.customeraccount.by.id}")
	private String contextCustomerAccountById;

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

	private CustomerAccountBank customerAccountBank1;
	private CustomerAccountBank customerAccountBank2;

	public CustomerAccountBankControllerTests(){
		this.restTemplateBuilder = new RestTemplateBuilder();
		this.testRestTemplate = new TestRestTemplate(restTemplateBuilder);

		this.customerAccountBank1 = getCustomerAccountBankVO(1, "Jose",  1, "40");
		this.customerAccountBank2 = getCustomerAccountBankVO(2, "Maria", 2, "40");
		
	}
	
	@Test
	@Order(1) 	
	void cadastrarClienteComSucesso() {
		final String URL_CUSTOMERACCOUNT = http+host+":"+port+contextGeneral + contextCustomerAccount;
		
		ResponseEntity<String> response1 = testRestTemplate.postForEntity(URL_CUSTOMERACCOUNT, customerAccountBank1,String.class);
		ResponseEntity<String> response2 = testRestTemplate.postForEntity(URL_CUSTOMERACCOUNT, customerAccountBank2,String.class);

		assertEquals( response1.getStatusCode(),  HttpStatus.OK );
		assertEquals( response2.getStatusCode(),  HttpStatus.OK );

	}

	@Test
	@Order(2) 	
	void cadastrarClienteComErroIdDoClienteJaExistente() {
		final String URL_CUSTOMERACCOUNT = http+host+":"+port+contextGeneral + contextCustomerAccount;

		customerAccountBank1.setIdCustomerAccountBank(2);
		
		ResponseEntity<String> responseNext = testRestTemplate.postForEntity(URL_CUSTOMERACCOUNT, customerAccountBank1,String.class);
		assertEquals( responseNext.getStatusCode(),  HttpStatus.INTERNAL_SERVER_ERROR );
	}

	@Test
	@Order(3) 	
	void cadastrarClienteComErroNumeroDaContaJaExistente() {
		final String URL_CUSTOMERACCOUNT = http+host+":"+port+contextGeneral + contextCustomerAccount;

		customerAccountBank1.getCustomer().setIdCustomer(2);
		
		ResponseEntity<String> responseNext = testRestTemplate.postForEntity(URL_CUSTOMERACCOUNT, customerAccountBank1,String.class);
		assertEquals( responseNext.getStatusCode(),  HttpStatus.INTERNAL_SERVER_ERROR );
	}

	@Test
	@Order(4) 	
	void listarTodosClientesComSucesso() {
		final String URL_CUSTOMERACCOUNT_LIST = http+host+":"+port+contextGeneral + contextCustomerAccountList;

		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_CUSTOMERACCOUNT_LIST, null,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.OK );
	}

	@Test
	@Order(5) 	
	void cadastrarClienteComErroOndeIdCustomerAccountBankMenorIgualZero() {
		final String URL_CUSTOMERACCOUNT = http+host+":"+port+contextGeneral + contextCustomerAccount;

		customerAccountBank1.setIdCustomerAccountBank(0);//
		
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_CUSTOMERACCOUNT, customerAccountBank1,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.INTERNAL_SERVER_ERROR );
	}

	@Test
	@Order(6) 	
	void cadastrarClienteComErroOndeIdCustomerMenorIgualZero() {
		final String URL_CUSTOMERACCOUNT = http+host+":"+port+contextGeneral + contextCustomerAccount;

		customerAccountBank1.getCustomer().setIdCustomer(0);
		
		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_CUSTOMERACCOUNT, customerAccountBank1,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.INTERNAL_SERVER_ERROR );
	}

	@Test
	@Order(7) 	
	void listarClientePeloNumeroDaContaComSucesso() {
		final String URL_CUSTOMERACCOUNT_BY_ID = http+host+":"+port+contextGeneral + contextCustomerAccountById;

		ResponseEntity<String> response = testRestTemplate.postForEntity(URL_CUSTOMERACCOUNT_BY_ID, customerAccountBank1,String.class);
		assertEquals( response.getStatusCode(),  HttpStatus.OK );
	}
	
}