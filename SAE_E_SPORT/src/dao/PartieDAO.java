package dao;

import java.sql.Date;
import java.util.Optional;

import modele.Partie;

public interface PartieDAO extends DAO<Partie, Integer> {

	Optional<Partie> getByDateHeure(Date date, String heure) throws Exception;

}
