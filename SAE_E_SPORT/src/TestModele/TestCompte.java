package TestModele;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.CreateDB;
import modele.Compte;
import modele.TypeCompte;

public class TestCompte {

	private Compte c1;
	private Compte c2;
	private Compte modeleCompte;
	
	@Before
	public void setUp() throws Exception {
		CreateDB.main(null);
		this.modeleCompte = new Compte();
		this.c1 = new Compte("CHE1111A", "maxinfo", TypeCompte.ADMINISTRATEUR);
		this.c2 = new Compte("KHC4298A", "kohinfo", TypeCompte.ARBITRE);
		this.modeleCompte.ajouterCompte(this.c1);
		this.modeleCompte.ajouterCompte(this.c2);
	}

	@After
	public void tearDown() throws Exception {
		this.c1 = null;
		this.c2 = null;
		this.modeleCompte = null;
	}

	@Test
	public void testGenererPassword() {
		String mdp = Compte.genererPassword(12);
		assertTrue(mdp.length()==12);
	}
	
	@Test 
	public void testCompteIsAdmin() {
		// test ADMIN
		assertTrue(this.modeleCompte.compteIsAdmin(this.c1.getLogin(), this.c1.getMotDePasse()));
		// test ARBITRE (NON ADMIN)
		assertFalse(this.modeleCompte.compteIsAdmin(this.c2.getLogin(), this.c2.getMotDePasse()));
	}
	
	@Test 
	public void testCompteValide() {
		// test OK
		assertTrue(this.modeleCompte.compteValide(this.c1.getLogin(), this.c1.getMotDePasse()));
		// test mauvais mdp
		assertFalse(this.modeleCompte.compteValide(this.c1.getLogin(), "$iutinfo"));
		// test mauvais login et mdp
		assertFalse(this.modeleCompte.compteValide("PPPAOOOPA", "$iutinfo"));
	}
		
}
