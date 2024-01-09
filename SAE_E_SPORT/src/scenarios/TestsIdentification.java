package scenarios;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.CreateDB;
import modele.Compte;
import modele.TypeCompte;

public class TestsIdentification {
	
	private Compte modele;

	@Before
	public void setUp() throws Exception {
		CreateDB.main(null);
		this.modele = new Compte();
		this.modele.ajouterCompte(new Compte("KHC4298A", "youinfo", TypeCompte.ADMINISTRATEUR));
		this.modele.ajouterCompte(new Compte("MAR5221A", "davinfo", TypeCompte.ARBITRE)); 
	}

	@After
	public void tearDown() throws Exception {
		this.modele = null;
	}

	@Test
	public void testLoginMdpCorrect() {
		assertTrue(this.modele.compteValide("KHC4298A", "youinfo"));
	}
	
	@Test
	public void testLoginMdpIncorrect() {
		assertFalse(this.modele.compteValide("KHC4298A", "you"));
		assertFalse(this.modele.compteValide("KHC", "youinfo"));
		assertFalse(this.modele.compteValide("KHC", "you"));
	}
	
	@Test
	public void testCompteAdmin() {
		assertTrue(this.modele.compteIsAdmin("KHC4298A", "you"));
		assertFalse(this.modele.compteIsAdmin("MAR5221A", "davinfo"));
	}

}
