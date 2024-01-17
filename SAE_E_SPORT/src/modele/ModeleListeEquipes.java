package modele;

import java.util.List;
import java.util.stream.Collectors;

public class ModeleListeEquipes {
	List<Equipe> equipes;
	List<String> filteredEquipe;
	
	public ModeleListeEquipes() {
		this.equipes = new Equipe().getToutesLesEquipes().stream().sorted((e1,e2)->e1.getNom().compareTo(e2.getNom())).collect(Collectors.toList());
		this.filteredEquipe = null;
	}
	
	private void filterEquipes(List<String> nomEquipes, String recherche) {
		this.filteredEquipe = nomEquipes.stream()
				.filter(eq -> eq.toUpperCase().contains(recherche.toUpperCase()))
				.collect(Collectors.toList());
	}

	public List<Equipe> getEquipes(){
		return this.equipes;
	}
	
	public List<String> trierParNom(String recherche) {
		List<String> nomEquipes = this.equipes.stream()
				.sorted((x,y)-> x.getNom().compareTo(y.getNom()))
				.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
				.collect(Collectors.toList());
		
		this.filterEquipes(nomEquipes, recherche);
		return this.filteredEquipe;
	}
	
	public List<String> trierParRang(String recherche){
		List<String> nomEquipes = this.equipes.stream()
				.sorted((x,y)-> {
					if (x.getRang()>y.getRang()){
						return 1;
					}else if(x.getRang()<y.getRang()) {
						return -1;
					}else {
						return 0;
					}
				})
				.map(eq -> String.format("%-5d %-50s", eq.getRang(), eq.getNom()))
				.collect(Collectors.toList());

		this.filterEquipes(nomEquipes, recherche);
		return this.filteredEquipe;
	}
	
}
