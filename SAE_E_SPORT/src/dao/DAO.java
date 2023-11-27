package dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import modele.Partie;

public interface DAO <T, T1> {
	   
    List <T> getAll() throws Exception;

    Optional <T> getById(T1 id) throws Exception;

    boolean add(T value) throws Exception;

    boolean update(T value) throws Exception;

    boolean delete(T value) throws Exception;

}