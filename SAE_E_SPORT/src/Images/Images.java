package Images;

public enum Images {

	RECHERCHE("/Images/Search_Icon.png"), TROPHY_WIN("/Images/trophyWin.png"), TROPHY("/Images/trophy.png");
	
	private final String nom;
	
	private Images (String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
}
