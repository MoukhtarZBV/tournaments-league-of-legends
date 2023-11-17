package modele;

public enum Pays {

	FRANCE("France"), TAIWAN("Taiwan");
	
	private String denomination;
	
	private Pays (String denomination) {
		this.denomination = denomination;
	}
	
	public String denomination() {
		return this.denomination;
	}
	
}
