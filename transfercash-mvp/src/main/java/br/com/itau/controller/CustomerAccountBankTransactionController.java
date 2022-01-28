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
import br.com.itau.model.CustomerAccountBankTransaction;
import br.com.itau.service.CustomerAccountBankTransactionService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/transfer")
public class CustomerAccountBankTransactionController {

	@Autowired
	private CustomerAccountBankTransactionService customerAccountBankTransactionService;

	@PostMapping("/v1/transfercash")
	@Transactional(value = TxType.REQUIRED)
	public ResponseEntity<?> createTransferCash(@RequestBody CustomerAccountBankTransaction customerAccountBankTransaction ) {
		ResponseDTO response = new ResponseDTO();
		response.setData( customerAccountBankTransaction );
		try {
			customerAccountBankTransactionService.registerCustomerAccountBankTransaction(customerAccountBankTransaction);
			response.setMessage(MessageReturnEnum.SUCESS_TRANSFER.getMessage());
			response.setCode(MessageReturnEnum.SUCESS_TRANSFER.getCode());
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(MessageReturnEnum.ERROR_TRANSFER.getMessage() + " Exception=> " + e.toString() + ", getMessage()=>" + e.getMessage() );
			response.setCode(MessageReturnEnum.ERROR_TRANSFER.getCode());
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@PostMapping("/v1/listalltransactions")
	public ResponseEntity<?> getCustomers() {
		ResponseDTO response = new ResponseDTO();
		try {
			List<CustomerAccountBankTransaction> list = customerAccountBankTransactionService.getAllCustomerAccountBankTransaction();
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

}