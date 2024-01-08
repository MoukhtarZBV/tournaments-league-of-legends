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
}
