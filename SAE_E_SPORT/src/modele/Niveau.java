package modele;

public enum Niveau {
	
	LOCAL("Local"), REGIONAL("Regional"), NATIONAL("National"), INTERNATIONAL("International"), INTERNATIONAL_CLASSE("International classe");
	
	private String denomination;
	
	private Niveau (String denomination) {
		this.denomination = denomination;
	}
	
	public String denomination() {
		return this.denomination;
	}
	
	public static Niveau getNiveau(String nom) {
    	for (Niveau niveau : Niveau.values()) {
    		if (niveau.denomination().equals(nom)) {
    			return niveau;
    		}
    	}
    	return null;
    }
	
	public static float multiplicateurNiveau(Niveau niveau) {
		switch (niveau) {
		case LOCAL:
			return 1;
		case REGIONAL:
			return 1.5f;
		case NATIONAL:
			return 2;
		case INTERNATIONAL:
			return 2.25f;
		case INTERNATIONAL_CLASSE:
			return 3;
		default:
			return 1;
		}
	}
}