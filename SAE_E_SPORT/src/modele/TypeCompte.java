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
	
	public static TypeCompte getTypeCompte(String type) {
		if (type.equals("Arbitre")) {
			return TypeCompte.ARBITRE;
		} else if (type.equals("Administrateur")) {
			return TypeCompte.ADMINISTRATEUR;
		} else {
			return null;
		}
	}

}


