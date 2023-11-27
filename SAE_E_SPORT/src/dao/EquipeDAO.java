package dao;

import java.util.Optional;

import modele.Equipe;

public interface EquipeDAO extends DAO<Equipe, Integer>{
	
	Optional<Equipe> getByNom (String nom) throws Exception;
	int getIdByNom(String nom) throws Exception;
    int getNextValueSequence() throws Exception;

	
}
