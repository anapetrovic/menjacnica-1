package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;

public class GUIKontroler {
	private static MenjacnicaGUI glavniProzor;
	private static MenjacnicaInterface menjacnica;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menjacnica=new Menjacnica();
					glavniProzor=new MenjacnicaGUI();
					glavniProzor.setVisible(true);
					glavniProzor.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(glavniProzor.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(glavniProzor.getContentPane());
		prozor.setVisible(true);
	}
	public static void prikaziObrisiKursGUI(Valuta valuta) {
		if (valuta != null) {
			ObrisiKursGUI prozor = new ObrisiKursGUI(valuta);
			prozor.setLocationRelativeTo(glavniProzor.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI(Valuta valuta) {
		if (valuta != null) {
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(valuta);
			prozor.setLocationRelativeTo(glavniProzor.getContentPane());
			prozor.setVisible(true);
		}
	}
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(glavniProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(glavniProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				glavniProzor.prikaziSveValute(menjacnica.vratiKursnuListu());
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	
	
	public static void unesiKurs(JTextField textFieldNaziv, JTextField textFieldSkraceniNaziv,
			JSpinner spinnerSifra, JTextField textFieldProdajniKurs,
			JTextField textFieldKupovniKurs, JTextField textFieldSrednjiKurs) {
		
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(textFieldNaziv.getText());
			valuta.setSkraceniNaziv(textFieldSkraceniNaziv.getText());
			valuta.setSifra((Integer)(spinnerSifra.getValue()));
			valuta.setProdajni(Double.parseDouble(textFieldProdajniKurs.getText()));
			valuta.setKupovni(Double.parseDouble(textFieldKupovniKurs.getText()));
			valuta.setSrednji(Double.parseDouble(textFieldSrednjiKurs.getText()));
			
			// Dodavanje valute u kursnu listu
			menjacnica.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			glavniProzor.prikaziSveValute(menjacnica.vratiKursnuListu());
			
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziValutu(JTextField textFieldNaziv, JTextField textFieldSkraceniNaziv, 
			JTextField textFieldSifra, JTextField textFieldProdajniKurs, JTextField textFieldKupovniKurs,
			JTextField textFieldSrednjiKurs, Valuta valuta) {
		// Prikaz podataka o valuti
		textFieldNaziv.setText(valuta.getNaziv());
		textFieldSkraceniNaziv.setText(valuta.getSkraceniNaziv());
		textFieldSifra.setText(""+valuta.getSifra());
		textFieldProdajniKurs.setText(""+valuta.getProdajni());
		textFieldKupovniKurs.setText(""+valuta.getKupovni());
		textFieldSrednjiKurs.setText(""+valuta.getSrednji());				
	}

	public static void izvrsiZamenu(Valuta valuta, boolean isSelected, String iznos, JTextField konIznos){
		try{
			double konacniIznos = 
					menjacnica.izvrsiTransakciju(valuta,isSelected, Double.parseDouble(iznos));
		
			konIznos.setText(""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnica.obrisiValutu(valuta);
			
		glavniProzor.prikaziSveValute(menjacnica.vratiKursnuListu());
			glavniProzor.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
