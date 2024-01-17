package Application;

import java.awt.EventQueue;

import dao.ConnectionJDBC;
import dao.CreateDB;
import dao.InsertionDB;
import ihm.Ecran;
import ihm.VueIdentification;

public class Application {

	public static void main(String[] args) throws Exception {
		ConnectionJDBC.getConnection();
		Ecran.setup();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueIdentification frame = new VueIdentification();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
