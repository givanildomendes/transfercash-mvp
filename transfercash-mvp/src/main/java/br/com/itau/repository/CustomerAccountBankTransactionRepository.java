package br.com.itau.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.itau.model.CustomerAccountBankTransaction;

@Repository
public interface CustomerAccountBankTransactionRepository extends JpaRepository<CustomerAccountBankTransaction, Long>{

	//Listar as transacoes ja realizadas em um periodo por uma determinada conta.
	@Query(value=""
			+ "SELECT e FROM CustomerAccountBankTransaction e JOIN e.customerAccountBankOri a "
			+ "WHERE "
			+ "a.idCustomerAccountBank = :accountBankOri "
			+ "and e.dtTransferCash between :ini and :end "
		)
	List<CustomerAccountBankTransaction> getSumTransactionsByAccountBankOriAndDay
			(	@Param("accountBankOri") long accountBankOri, 
				@Param("ini") LocalDateTime ini, 
				@Param("end") LocalDateTime end
			);

}