package br.com.itau.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itau.model.CustomerAccountBank;

@Repository
public interface CustomerAccountBankRepository extends JpaRepository<CustomerAccountBank, Long>{

	CustomerAccountBank findByIdCustomerAccountBank(Long id);

}
