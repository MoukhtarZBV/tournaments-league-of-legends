package dao;

import java.sql.Date;
import java.util.Optional;

import modele.Equipe;
import modele.Jouer;

public interface JouerDAO extends DAO <Jouer, Integer>{
	Optional<Jouer> getByDateHeure(Date date, String heure) throws Exception;
	Optional<Jouer> getByEquipe(Equipe e) throws Exception;
}
