package modele;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import dao.ParticiperJDBC;

public class ModeleHistoriquePoints {

	List<Participer> participations;
	List<Equipe> equipes;
	List<Equipe> filteredEquipe;
	
	public ModeleHistoriquePoints() {
		this.participations = new ParticiperJDBC().getAll();
		this.equipes = new Equipe().getToutesLesEquipes().stream().sorted((e1,e2)->e1.getNom().compareTo(e2.getNom())).collect(Collectors.toList());
		this.filteredEquipe = this.equipes;
	}
	
	public void filterEquipes(String nom) {
		this.filteredEquipe = this.equipes.stream().filter(e-> e.getNom().toUpperCase().contains(nom.toUpperCase())).collect(Collectors.toList());
	}
	
	public void resetEquipes() {
		this.filteredEquipe = this.equipes;
	}
	
	public List<Equipe> getFilteredEquipes(){
		return this.filteredEquipe;
	}
	
	public Map<Tournoi, Integer> pointsTournoiParEquipe (Equipe e){
		Map<Tournoi, Integer> map = this.participations.stream().filter(eq->eq.getEquipe().equals(e)).collect(Collectors.toMap(Participer::getTournoi, p->p.getNbPointsTournoiGagnes()));
		return map;
	}
	
	public List<Equipe> getEquipes(){
		return this.equipes;
	}
	
}
