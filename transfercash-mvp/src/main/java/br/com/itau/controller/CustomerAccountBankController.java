package br.com.itau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itau.dto.ResponseDTO;
import br.com.itau.enums.MessageReturnEnum;
import br.com.itau.model.CustomerAccountBank;
import br.com.itau.service.CustomerAccountBankService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/transfer")
public class CustomerAccountBankController {

	@Autowired
	private CustomerAccountBankService customerAccountBankService;

	@PostMapping("/v1/createcustomeraccount")
	@Transactional(value = TxType.REQUIRED)
	public ResponseEntity<?> createRegister(@RequestBody CustomerAccountBank customerAccountBank) {
		ResponseDTO response = new ResponseDTO();
		response.setData( customerAccountBank );
		try {
			customerAccountBankService.registerAccountBank(customerAccountBank);
			response.setMessage(MessageReturnEnum.SUCESS.getMessage());
			response.setCode(MessageReturnEnum.SUCESS.getCode());
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(MessageReturnEnum.ERROR.getMessage() + " Exception=> " + e.toString() + ", getMessage()=>" + e.getMessage() );
			response.setCode(MessageReturnEnum.ERROR.getCode());
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@PostMapping("/v1/listallcustomeraccount")
	public ResponseEntity<?> getCustomers() {
		ResponseDTO response = new ResponseDTO();
		try {
			List<CustomerAccountBank> list = customerAccountBankService.getAllCustomerAccountBank();
			response.setData( list );
			response.setMessage(MessageReturnEnum.SUCESS_LIST.getMessage());
			response.setCode(MessageReturnEnum.SUCESS_LIST.getCode());
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(MessageReturnEnum.ERROR_LIST.getMessage() + " Exception=> " + e.toString() + ", getMessage()=>" + e.getMessage() );
			response.setCode(MessageReturnEnum.ERROR_LIST.getCode());
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@PostMapping("/v1/customeraccountbyid")
	public ResponseEntity<?> getCustomerAccountById(@RequestBody CustomerAccountBank customerAccountBank) {
		ResponseDTO response = new ResponseDTO();
		try {
			CustomerAccountBank list = customerAccountBankService.getCustomerAccountBankById(customerAccountBank.getIdCustomerAccountBank());
			response.setData( list );
			response.setMessage(MessageReturnEnum.SUCESS_SEARCH.getMessage());
			response.setCode(MessageReturnEnum.SUCESS_SEARCH.getCode());
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(MessageReturnEnum.ERROR_SEARCH.getMessage() + " Exception=> " + e.toString() + ", getMessage()=>" + e.getMessage() );
			response.setCode(MessageReturnEnum.ERROR_SEARCH.getCode());
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

}