package br.com.itau.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

//@NamedQueries({
//	@NamedQuery(name="getAllCustomerAccountBankTransaction", 	query="SELECT c FROM CustomerAccountBankTransaction c")
//})

@Entity
@Table(name = "tc_accountbank_transactions")
public class CustomerAccountBankTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_account_bank_transaction", length = 10, nullable = false)
	private long idAccountBankTransaction;

//	//D=Débito e C=Crédito
//	@Column(name = "tp_transfer_cash", columnDefinition = "VARCHAR(1)  CHECK (tp_transfer_cash IN ('D', 'C'))" ) 
//	private String typeTransferCash;

	@Column(name = "value_transfer_cash", precision=12, scale=2)
	private BigDecimal valueTransferCash;

	@Column(name = "dt_transfer_cash", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dtTransferCash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cust_account_bank_ori")
	private CustomerAccountBank customerAccountBankOri;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cust_account_bank_dest")
	private CustomerAccountBank customerAccountBankDest;

	@Column(name = "status", length = 2)
	private String status;

	@Column(name = "message", length = 200)
	private String message;

	public long getIdAccountBankTransaction() {
		return idAccountBankTransaction;
	}

	public void setIdAccountBankTransaction(long idAccountBankTransaction) {
		this.idAccountBankTransaction = idAccountBankTransaction;
	}

	public BigDecimal getValueTransferCash() {
		return valueTransferCash;
	}

	public void setValueTransferCash(BigDecimal valueTransferCash) {
		this.valueTransferCash = valueTransferCash;
	}

	public LocalDateTime getDtTransferCash() {
		return dtTransferCash;
	}

	public void setDtTransferCash(LocalDateTime dtTransferCash) {
		this.dtTransferCash = dtTransferCash;
	}

	public CustomerAccountBank getCustomerAccountBankOri() {
		return customerAccountBankOri;
	}

	public void setCustomerAccountBankOri(CustomerAccountBank customerAccountBankOri) {
		this.customerAccountBankOri = customerAccountBankOri;
	}

	public CustomerAccountBank getCustomerAccountBankDest() {
		return customerAccountBankDest;
	}

	public void setCustomerAccountBankDest(CustomerAccountBank customerAccountBankDest) {
		this.customerAccountBankDest = customerAccountBankDest;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CustomerAccountBankTransaction [idAccountBankTransaction=" + idAccountBankTransaction
				+ ", valueTransferCash=" + valueTransferCash + ", dtTransferCash=" + dtTransferCash
				+ ", customerAccountBankOri=" + customerAccountBankOri + ", customerAccountBankDest="
				+ customerAccountBankDest + ", status=" + status + ", message=" + message + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerAccountBankDest, customerAccountBankOri, dtTransferCash, idAccountBankTransaction,
				message, status, valueTransferCash);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerAccountBankTransaction other = (CustomerAccountBankTransaction) obj;
		return Objects.equals(customerAccountBankDest, other.customerAccountBankDest)
				&& Objects.equals(customerAccountBankOri, other.customerAccountBankOri)
				&& Objects.equals(dtTransferCash, other.dtTransferCash)
				&& idAccountBankTransaction == other.idAccountBankTransaction && Objects.equals(message, other.message)
				&& Objects.equals(status, other.status) && Objects.equals(valueTransferCash, other.valueTransferCash);
	}

}