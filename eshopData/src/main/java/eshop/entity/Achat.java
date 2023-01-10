package eshop.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_details")
public class Achat {
	@EmbeddedId
	private AchatKey id;
	@Column(name = "quantity")
	private int quantite;

	public Achat() {

	}

	public Achat(AchatKey id, int quantite) {
		super();
		this.id = id;
		this.quantite = quantite;
	}

	public AchatKey getId() {
		return id;
	}

	public void setId(AchatKey id) {
		this.id = id;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Achat other = (Achat) obj;
		return Objects.equals(id, other.id);
	}

}
