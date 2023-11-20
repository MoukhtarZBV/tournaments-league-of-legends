package modele;

public enum Pays {

	FRANCE("France"), TAIWAN("Taiwan"), PORTUGAL("Portugal"), ALBANIE("Albanie"), BRESIL("Bresil"),France("FR");
	
	private String denomination;
	
	private Pays (String denomination) {
		this.denomination = denomination;
	}
	
	public String denomination() {
		return this.denomination;
	}
	
}
