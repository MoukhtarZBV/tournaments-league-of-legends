package dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import modele.Partie;

public interface DAO <T, T1> {
	   
    List <T> getAll();

    Optional <T> getById(T1 id);

    boolean add(T value);

    boolean update(T value);

    boolean delete(T value);


}