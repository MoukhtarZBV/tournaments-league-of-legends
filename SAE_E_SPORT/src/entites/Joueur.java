package entites;

import java.sql.*;
import java.util.*;

public class Joueur {
	
	private int idJoueur;
	private String pseudo;
	
	public Joueur(int idJoueur, String pseudo) {
		this.idJoueur = idJoueur;
		this.pseudo = pseudo;
	}
	
	public int getIdJoueur() {
		return this.idJoueur;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Joueur) {
			Joueur j = (Joueur) o;
			if (this.idJoueur == j.getIdJoueur()) {
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
		return this.idJoueur + " - "+this.pseudo;
	}	


}
