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
}