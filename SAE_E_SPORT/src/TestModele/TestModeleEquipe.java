package TestModele;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.CreateDB;
import dao.EquipeJDBC;
import dao.JoueurJDBC;
import modele.Equipe;
import modele.Joueur;
import modele.ModeleListeEquipes;
import modele.Pays;

public class TestModeleEquipe {

	private Equipe e1;
	private Equipe e2;
	private Equipe e3;
	private Equipe e5;
	private Equipe modeleEquipe;
	private ModeleListeEquipes modeleListeEquipes;
	
	@Before
	public void setUp() throws Exception {
		
		this.modeleEquipe = new Equipe();
		this.e1 = new Equipe(EquipeJDBC.getNextValueSequence(), "My1", 1, Pays.FR);
		this.e2 = new Equipe(EquipeJDBC.getNextValueSequence(), "My2", 2, Pays.FR);
		this.e3 = new Equipe(EquipeJDBC.getNextValueSequence(), "My3", 3, Pays.FR);
		this.e5 = new Equipe(EquipeJDBC.getNextValueSequence(), "My5", 4, Pays.TW);
		prepareDatas();
		this.modeleListeEquipes = new ModeleListeEquipes();
	}

	@After
	public void tearDown() throws Exception {
		this.e1 = null;
		this.e2 = null;
		this.e3 = null;
		this.e5 = null;
		this.modeleEquipe = null;
		this.modeleListeEquipes = null;
	}

	@Test
	public void testEquipesExistante() throws Exception {
		List<Equipe> equipes = new LinkedList<>();
		equipes.add(e1);
		equipes.add(e2);
		equipes.add(e3);
		
		assertTrue(modeleEquipe.equipeExistante(e1));
		assertFalse(modeleEquipe.equipeExistante(e5));
	}
	
	@Test
	public void testTousLesEquipes() throws Exception {
		List<Equipe> equipes = new LinkedList<>();
		equipes.add(e1);
		equipes.add(e2);
		equipes.add(e3);
		
		assertEquals(modeleEquipe.getToutesLesEquipes(),equipes);
		assertEquals(modeleListeEquipes.getEquipes(), equipes);
	}
	
	@Test
	public void testTrierParNom() throws Exception {
		// test recherche sans texte
		assertEquals(this.modeleEquipe.getToutesLesEquipes().stream().sorted((x,y)-> x.getNom().compareTo(y.getNom()))
				.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
				.collect(Collectors.toList()), this.modeleListeEquipes.trierParNom(""));
		// test string n'existe pas 
		List<String> list = this.modeleListeEquipes.trierParNom("&é'(é&($^$ùm$)");
		assertTrue(list.size()==0);
		// test string specific
		list = this.modeleListeEquipes.trierParNom("MY2");
		assertTrue(list.size()==1 && list.get(0).equals(String.format("%-5d %-50s", 2, "My2")));
		//	test string commun
		list = this.modeleListeEquipes.trierParNom("MY");
		assertTrue(list.size()==this.modeleListeEquipes.getEquipes().size());
	}	
	
	@Test
	public void testTrierParRang() throws Exception {
		// test recherche sans texte
		assertEquals(this.modeleListeEquipes.trierParRang(""), this.modeleEquipe.getToutesLesEquipes().stream()
				.sorted((x,y)-> {
					int res = x.getRang()-y.getRang();
					if (res>0) return 1;
					else if (res == 0) return 0;
					else return -1;
					})
				.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
				.collect(Collectors.toList()));
		// test avec characters mais pas nombre
		List<String> list = this.modeleListeEquipes.trierParRang("&é'(é&($^$ùm$)");
		assertTrue(list.size()==0);
		// test rang specific
		list = this.modeleListeEquipes.trierParRang("2");
		assertTrue(list.size()==1 && list.get(0).equals(String.format("%-5d %-50s", 2, "My2")));
	}

	private void prepareDatas() throws Exception {
		CreateDB.main(null);
		this.e1.ajouterJoueur(new Joueur(JoueurJDBC.getNextValueSequence(), "Zeus", this.e1), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "Oner", this.e1), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "Faker", this.e1), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "Guma", this.e1), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "Keria", this.e1));
		this.e2.ajouterJoueur(new Joueur(JoueurJDBC.getNextValueSequence(), "J21", this.e2), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J22", this.e2), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J23", this.e2), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J24", this.e2), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J25", this.e2));
		this.e3.ajouterJoueur(new Joueur(JoueurJDBC.getNextValueSequence(), "J31", this.e3), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J32", this.e3), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J33", this.e3), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J34", this.e3), 
								new Joueur(JoueurJDBC.getNextValueSequence(), "J35", this.e3));
		
		this.modeleEquipe.ajouterEquipe(this.e1);
		this.modeleEquipe.ajouterEquipe(this.e2);
		this.modeleEquipe.ajouterEquipe(this.e3);
	}
	
}
