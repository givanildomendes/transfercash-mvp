package br.com.itau.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@NamedQueries({
//	@NamedQuery(name="getCustomerAccountBankById", 	query="SELECT c FROM CustomerAccountBank c WHERE c.idCustomerAccountBank = :value"),
//	@NamedQuery(name="getAllCustomerAccountBank", 	query="SELECT c FROM CustomerAccountBank c")
//})

@Entity
@Table(name = "tc_account_bank")
public class CustomerAccountBank {

	@Id
	@Column(name = "id_cust_account_bank", length = 10, nullable = false, unique = true)
	private long idCustomerAccountBank;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer")
    private Customer customer;
    
	@Column(name = "balance", precision=12, scale=2)
	private BigDecimal balance;

	public long getIdCustomerAccountBank() {
		return idCustomerAccountBank;
	}

	public void setIdCustomerAccountBank(long idCustomerAccountBank) {
		this.idCustomerAccountBank = idCustomerAccountBank;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, customer, idCustomerAccountBank);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerAccountBank other = (CustomerAccountBank) obj;
		return Objects.equals(balance, other.balance) && Objects.equals(customer, other.customer)
				&& idCustomerAccountBank == other.idCustomerAccountBank;
	}

	@Override
	public String toString() {
		return "AccountBank [idAccountBank=" + idCustomerAccountBank + ", customer=" + customer + ", balance=" + balance + "]";
	}

}
