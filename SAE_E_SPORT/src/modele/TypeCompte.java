package modele;

public enum TypeCompte {
	ARBITRE("Arbitre"), ADMINISTRATEUR("Administrateur");

	private String denomination;
	
	private TypeCompte(String denomination) {
		this.denomination = denomination;
	}
	
	public String denomination() {
		return this.denomination;
	}

}


