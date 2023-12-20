package modele;

public enum Statut {

	A_VENIR("À venir"), EN_COURS("En cours"), TERMINE("Terminé"), ANNULE("Annulé"), ATTENTE_RESULTATS("Attente de résultats"), FINALE("Finale en cours");
	
	private String denomination;
	
	private Statut(String denomination) {
		this.denomination = denomination;
	}
	
	public String denomination() { 
		return this.denomination;
	}
	
	public static Statut getStatut(String denomination) {
	    for (Statut statut : Statut.values()) {
	   		if (statut.denomination().equals(denomination)) {
	   			return statut;
	   		}
	   	}
    	return null;
	}
}
