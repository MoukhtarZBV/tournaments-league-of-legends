package entites;

public class Arbitre {
	
	private int idArbitre;
	private String nomArbitre;
	private String prenomArbitre;
	
	public Arbitre(int idArbitre, String nomArbitre, String prenomArbitre) {
		this.idArbitre = idArbitre;
		this.nomArbitre = nomArbitre;
		this.prenomArbitre = nomArbitre;
	}
	
	public int getidArbitre() {
		return this.idArbitre;
	}
	
	public String getnomArbitre() {
		return this.nomArbitre;
	}
	
	public String getprenomArbitre() {
		return this.prenomArbitre;
	}
	
	public void setidArbitre(int idArbitre) {
		this.idArbitre = idArbitre;
	}
	
	public void setnomArbitre(String nomArbitre) {
		this.nomArbitre = nomArbitre;
	}
	
	public void setprenomArbitre(String prenomArbitre) {
		this.prenomArbitre = prenomArbitre;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Arbitre) {
			Arbitre a = (Arbitre) o;
			if (this.idArbitre == a.getidArbitre()) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.idArbitre + " - "+this.nomArbitre + " - "+this.prenomArbitre;
	}	
}
