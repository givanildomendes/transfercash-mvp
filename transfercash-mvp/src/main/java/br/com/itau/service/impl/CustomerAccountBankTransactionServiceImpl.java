package br.com.itau.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itau.exception.BusinessRuleException;
import br.com.itau.exception.FailedException;
import br.com.itau.model.CustomerAccountBank;
import br.com.itau.model.CustomerAccountBankTransaction;
import br.com.itau.repository.CustomerAccountBankRepository;
import br.com.itau.repository.CustomerAccountBankTransactionRepository;
import br.com.itau.service.CustomerAccountBankService;
import br.com.itau.service.CustomerAccountBankTransactionService;

@Service
public class CustomerAccountBankTransactionServiceImpl implements CustomerAccountBankTransactionService{

	private CustomerAccountBankService 					customerAccountBankService;
	private CustomerAccountBankRepository 				customerAccountBankRepository ;
	private CustomerAccountBankTransactionRepository 	customerAccountBankTransactionRepository ;

	@Autowired
	public CustomerAccountBankTransactionServiceImpl(	CustomerAccountBankService customerAccountBankService, 
														CustomerAccountBankRepository customerAccountBankRepository,
														CustomerAccountBankTransactionRepository customerAccountBankTransactionRepository ) {
		this.customerAccountBankService = customerAccountBankService;
		this.customerAccountBankRepository = customerAccountBankRepository;
		this.customerAccountBankTransactionRepository = customerAccountBankTransactionRepository;
	}
	
	@Override
	@Transactional(value = TxType.REQUIRED)
	public synchronized void registerCustomerAccountBankTransaction(CustomerAccountBankTransaction customerAccountBankTransaction)
			throws BusinessRuleException, FailedException {

		customerAccountBankTransaction.setDtTransferCash(LocalDateTime.now());
		try {

			//Busca a conta origem e analisa se tem saldo disponivel para a operacao de transferencia
			CustomerAccountBank customerAccountBankOri;
			try {
				customerAccountBankOri = customerAccountBankService.getCustomerAccountBankById( 
						customerAccountBankTransaction.getCustomerAccountBankOri().getIdCustomerAccountBank() );

				if (customerAccountBankOri==null) {
					throw new BusinessRuleException("Conta origem inexistente.");
				}
				customerAccountBankTransaction.setCustomerAccountBankOri(customerAccountBankOri);
			} catch (Exception e) {
				customerAccountBankTransaction.setCustomerAccountBankOri(null);
				customerAccountBankTransaction.setCustomerAccountBankDest(null);
				throw new BusinessRuleException("Erro ao pesquisar conta de origem. "+e.getMessage(), e);
			}

			//Busca a conta destino para carregamento dos dados da mesma
			try {
				CustomerAccountBank customerAccountBankDest = customerAccountBankService.getCustomerAccountBankById( 
							customerAccountBankTransaction.getCustomerAccountBankDest().getIdCustomerAccountBank() );
				if (customerAccountBankDest==null) {
					throw new BusinessRuleException("Conta destino inexistente.");
				}

				customerAccountBankTransaction.setCustomerAccountBankDest(customerAccountBankDest);
			} catch (Exception e) {
				customerAccountBankTransaction.setCustomerAccountBankDest(null);
				throw new BusinessRuleException("Erro ao pesquisar conta de destino. "+e.getMessage(), e);
			}

			if (customerAccountBankTransaction.getCustomerAccountBankOri().getIdCustomerAccountBank() == 
					customerAccountBankTransaction.getCustomerAccountBankDest().getIdCustomerAccountBank()){
				throw new BusinessRuleException("Não é permitido transferência para a mesma conta. ");
			}
			
			//Nao permite transferencia <= R$ 0,00
			if ( customerAccountBankTransaction.getValueTransferCash().doubleValue() <= 0 ) {
				throw new BusinessRuleException("Operação não permitida!!! Valor deve ser maior que 0.");
			}
			
			//Nao permite transferencia acima de R$ 1.000,00
			if ( customerAccountBankTransaction.getValueTransferCash().doubleValue() > 1000 ) {
				throw new BusinessRuleException("Operação não permitida!!! Ultrapassou o limite permitido de transferência de R$ 1.000,00.");
			}

			if ( customerAccountBankTransaction.getCustomerAccountBankOri().getBalance().doubleValue() <  customerAccountBankTransaction.getValueTransferCash().doubleValue()) {
				throw new BusinessRuleException(
						"Saldo "+customerAccountBankTransaction.getCustomerAccountBankOri().getBalance()+
						" insuficiente para realizar a transferência.");
			}

			customerAccountBankTransaction.setStatus("OK");
			customerAccountBankTransaction.setMessage("Sucesso.");
			
			BigDecimal newBalance = customerAccountBankOri.getBalance().subtract(customerAccountBankTransaction.getValueTransferCash());
			customerAccountBankOri.setBalance( newBalance );

			customerAccountBankRepository.save(customerAccountBankOri);
			customerAccountBankTransactionRepository.save(customerAccountBankTransaction);
		} catch (BusinessRuleException e) {
			throw e;
		} catch (Exception e) {
			customerAccountBankTransaction.setStatus("KO");
			customerAccountBankTransaction.setMessage(e.getMessage());
			
			customerAccountBankTransactionRepository.save(customerAccountBankTransaction);
			throw new FailedException("Operação não realizada!!! Ocorreu um erro inesperado ao tentar realizar a transferência."+e, e);
		} 
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public List<CustomerAccountBankTransaction> getAllCustomerAccountBankTransaction() throws FailedException {
		try {

			List<CustomerAccountBankTransaction> accountsBankTransactions = 
					(List<CustomerAccountBankTransaction>) customerAccountBankTransactionRepository.findAll();

//			IntStream.range(0, accountsBankTransactions.size()).forEach( (index) -> {
//				accountsBankTransactions.get(index).getCustomerAccountBankOri();	
//				accountsBankTransactions.get(index).getCustomerAccountBankDest();	
//			} );
			
			return accountsBankTransactions;
		} catch (Exception e) {
			throw new FailedException("Ocorreu um erro inesperado ao listar as transações de transferência.", e);
		} 
	}

}