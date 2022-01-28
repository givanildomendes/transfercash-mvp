package br.com.itau.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itau.model.CustomerAccountBankTransaction;

@Repository
public interface CustomerAccountBankTransactionRepository extends JpaRepository<CustomerAccountBankTransaction, Long>{

}
