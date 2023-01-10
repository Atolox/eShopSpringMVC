package eshop.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@SequenceGenerator(name = "seqCommande", sequenceName = "order_number_seq", initialValue = 50, allocationSize = 1)
public class Commande {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCommande")
	@Column(name = "order_number")
	private Long numero;
	@Column(name = "order_date")
	private LocalDate date;
	@ManyToOne
	@JoinColumn(name = "order_customer_id", foreignKey = @ForeignKey(name = "order_order_customer_id_fk"))
	private Client client;
	// version 1 1 client achète x produit 1 fois
//	@ManyToMany
//	@JoinTable(
//			name="order_details",
//			joinColumns =@JoinColumn(name="order_details_order_number",foreignKey = @ForeignKey(name="order_details_order_number_fk")),
//			inverseJoinColumns = @JoinColumn(name="order_details_product_id",foreignKey = @ForeignKey(name="order_details_product_id")))
//	private Set<Produit> achats;

	@OneToMany(mappedBy = "id.commande")
	private List<Achat> achats;

	public Commande() {

	}

	public Commande(LocalDate date, Client client) {
		super();
		this.date = date;
		this.client = client;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Achat> getAchats() {
		return achats;
	}

	public void setAchats(List<Achat> achats) {
		this.achats = achats;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commande other = (Commande) obj;
		return Objects.equals(numero, other.numero);
	}

}
