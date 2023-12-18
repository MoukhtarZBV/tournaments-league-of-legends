package modele;

public enum Statut {

	A_VENIR("À venir"), EN_COURS("En cours"), TERMINE("Terminé"), ANNULE("Annulé"), ATTENTE_RESULTATS("Attente de résultats");
	
	private String denomination;
	
	private Statut(String denomination) {
		this.denomination = denomination;
	}
	
	public String denomination() { 
		return this.denomination;
	}
	
	public static Statut getStatus(String denomination) {
	    for (Statut status : Statut.values()) {
	   		if (status.denomination().equals(denomination)) {
	   			return status;
	   		}
	   	}
    	return null;
	}
}
