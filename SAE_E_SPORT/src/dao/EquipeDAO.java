package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Equipe;

public interface EquipeDAO extends DAO<Equipe, Integer>{
	
	Optional<Equipe> getByNom (String nom) throws Exception;
	int getIdByNom(String nom) throws Exception;
	
}
