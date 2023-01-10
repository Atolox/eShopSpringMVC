package eshop.entity;

public enum Civilite {
	M("monsieur"),MME("madame"),MLLE("mademoiselle");
	
	private String libelle;
	
	private Civilite(String libelle) {
		this.libelle=libelle;
	}
	
	public String getLibelle() {
		return libelle;
	}
}
