package modele;

public enum Status {

	A_VENIR("À venir"), EN_COURS("En cours"), TERMINE("Terminé"), ANNULE("Annulé"), ATTENTE_RESULTATS("Attente de résultats");
	
	private String denomination;
	
	private Status(String denomination) {
		this.denomination = denomination;
	}
	
	public String denomination() { 
		return this.denomination;
	}
	
	public static Status getStatus(String denomination) {
	    for (Status status : Status.values()) {
	   		if (status.denomination().equals(denomination)) {
	   			return status;
	   		}
	   	}
    	return null;
	}
}
