package br.com.itau.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//
//@NamedQueries({
//	@NamedQuery(name="getCustomerById", 	query="SELECT c FROM Customer c WHERE c.idCustomer=:value"),
//	@NamedQuery(name="getAllCustomer", 		query="SELECT c FROM Customer c ")
//})

@Entity
@Table(name = "tc_customer")
public class Customer {
	@Id
	@Column(name = "id_customer", length = 10, nullable = false, unique = true)
	private long idCustomer;
	
	@Column(name = "name", length = 50)
	private String name;

	public long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(long idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getName() {
		return name;
	}

	public void setName(String nomeCliente) {
		this.name = nomeCliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCustomer, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return idCustomer == other.idCustomer && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Customer [idCustomer=" + idCustomer + ", name=" + name + "]";
	}

}