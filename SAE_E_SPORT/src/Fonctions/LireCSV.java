package Fonctions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LireCSV {
	
	private List<String[]> data;
	
	public LireCSV(String chemin) {
		String csvFile = chemin;
        String line;
        this.data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                this.data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public List<String[]> getData(){
		return this.data;
	}
	/*
    public static void main(String[] args) {
    
        // Affichage des données
        for (String[] row : this.data) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println(); // Nouvelle ligne pour chaque ligne du CSV
        }

        // Utilisation des données
        if (!data.isEmpty() && data.get(0).length > 1) {
            String value = data.get(1)[4];
            System.out.println("Valeur à la deuxième colonne de la première ligne : " + value);
        }
    }
    */
}
