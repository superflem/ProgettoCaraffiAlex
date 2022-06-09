package pannnelipkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import classipkg.ArchivioSpedizioni;
import classipkg.Spedizione;
import classipkg.SpedizioneAssicurata;
import threadpkg.ThreadIniziale;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

/**
 * Classe per inserire una nuova spedizione.
 * <br>
 * <img src="../../foto/nuovo.jpg" alt="pannello per l'inserimento di una nuova spedizione" >
 * @author Alex Caraffi
 * 
 */
public class Nuovo extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 8L;
	
	private String utente;
	private JFrame frame;
	private JTextField destinazioneField;
	private JTextField pesoField;
	private JTextField assicuratoField;
	
	private JButton spedisci;
	private JButton indietro;
	
	private ButtonGroup gruppo;
	private JRadioButton normaleDot;
	private JRadioButton assicurataDot;
	
	private Border dborder;
	private File fileSpedizioni;

	/**
	 * Costruttore del pannello Nuovo.
	 * Creo il pannello per efettuare una nuova spedizione. Il JFrame passato come parametro corrisponde al JFrame dell'applicazione e serve per    
	 * cambiare i pannelli. La stringa passata come parametro corrisponde all'utente loggato. 
	 * La variabile fileSpedizioni contiene un riferimento al file delle spedizioni e serve per sincronizzare i thread.
	 * @param utente utente loggato
	 * @param f JFrame del progetto
	 * @param fileSpedizioni riferimento al file spedizioni
	 * 
	 */
	public Nuovo(String utente, JFrame f, File fileSpedizioni) 
	{
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		this.utente = utente;
		this.fileSpedizioni = fileSpedizioni;
		frame = f;
		
		JLabel titolo = new JLabel(utente + ", inserisci i dati della nuova spedizione");
		springLayout.putConstraint(SpringLayout.NORTH, titolo, 30, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, titolo, 118, SpringLayout.WEST, this);
		titolo.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		add(titolo);
		
		JLabel labelDestinazione = new JLabel("Inserisci la destinazione");
		springLayout.putConstraint(SpringLayout.NORTH, labelDestinazione, 33, SpringLayout.SOUTH, titolo);
		add(labelDestinazione);
		
		JLabel labelPeso = new JLabel("Inserisci il peso (in kg)");
		springLayout.putConstraint(SpringLayout.SOUTH, labelPeso, -274, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, labelPeso, 0, SpringLayout.EAST, labelDestinazione);
		add(labelPeso);
		
		JLabel labelAssicurato = new JLabel("Inserisci il valore assicurato");
		springLayout.putConstraint(SpringLayout.EAST, labelAssicurato, 0, SpringLayout.EAST, labelDestinazione);
		add(labelAssicurato);
		
		destinazioneField = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, destinazioneField, 361, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, labelDestinazione, -91, SpringLayout.WEST, destinazioneField);
		springLayout.putConstraint(SpringLayout.NORTH, destinazioneField, -5, SpringLayout.NORTH, labelDestinazione);
		add(destinazioneField);
		destinazioneField.setColumns(20);
		
		normaleDot = new JRadioButton("Spedizione Normale");
		springLayout.putConstraint(SpringLayout.NORTH, labelAssicurato, 43, SpringLayout.SOUTH, normaleDot);
		springLayout.putConstraint(SpringLayout.NORTH, normaleDot, 45, SpringLayout.SOUTH, labelPeso);
		springLayout.putConstraint(SpringLayout.EAST, normaleDot, 0, SpringLayout.EAST, labelDestinazione);
		normaleDot.setSelected(true);
		add(normaleDot);
		
		assicurataDot = new JRadioButton("Spedizione Assicurata");
		springLayout.putConstraint(SpringLayout.NORTH, assicurataDot, 0, SpringLayout.NORTH, normaleDot);
		springLayout.putConstraint(SpringLayout.WEST, assicurataDot, 0, SpringLayout.WEST, destinazioneField);
		add(assicurataDot);
		
		spedisci = new JButton("Spedisci");
		springLayout.putConstraint(SpringLayout.NORTH, spedisci, 83, SpringLayout.SOUTH, assicurataDot);
		springLayout.putConstraint(SpringLayout.WEST, spedisci, 277, SpringLayout.WEST, this);
		add(spedisci);
		
		indietro = new JButton("Indietro");
		springLayout.putConstraint(SpringLayout.WEST, indietro, 278, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, indietro, -30, SpringLayout.SOUTH, this);
		add(indietro);
		
		pesoField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, pesoField, -5, SpringLayout.NORTH, labelPeso);
		add(pesoField);
		pesoField.setColumns(10);
		
		assicuratoField = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, pesoField, 0, SpringLayout.EAST, assicuratoField);
		springLayout.putConstraint(SpringLayout.NORTH, assicuratoField, -5, SpringLayout.NORTH, labelAssicurato);
		springLayout.putConstraint(SpringLayout.WEST, assicuratoField, 0, SpringLayout.WEST, destinazioneField);
		add(assicuratoField);
		assicuratoField.setEditable(false);
		assicuratoField.setColumns(10);

		gruppo = new ButtonGroup();
		gruppo.add(normaleDot);
		gruppo.add(assicurataDot);
		
		indietro.addActionListener(this);
		spedisci.addActionListener(this);
		
		assicurataDot.addActionListener(this);
		normaleDot.addActionListener(this);
		
		dborder = assicuratoField.getBorder();
	}

	
	/**
	 * Gestore degli eventi.
	 * Se premo il pulsante indietro vado alla home dell'utente, altrimenti spedisco il pacco.
	 * @param arg0 evento che fa partire il metodo.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if (actionCommand.equals("Indietro"))  //torno alla home
		{
			frame.setVisible(false);
			frame.remove(this);
			
			frame.getContentPane().add(new Home(utente, frame, fileSpedizioni));
			frame.setVisible(true);
			return;
		}
		
		if (actionCommand.equals("Spedizione Normale"))  //se seleziono la spedizione normale
		{
			assicuratoField.setEditable(false);
			assicuratoField.setText("");
			return;
		}
		
		if (actionCommand.equals("Spedizione Assicurata"))  //se seleziono la spedizione assicurata
		{
			assicuratoField.setEditable(true);
			return;
		}
		
		
		// se inserisco una nuova spedizione, controllo che non ci siano errori
		if (!cercaErrori())
			return;
		
		//controllo che il peso sia accettabile
		double peso = 0;
		try
		{
			peso = Double.parseDouble(pesoField.getText());
		}
		catch (NumberFormatException e)
		{
			Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
			
			JOptionPane.showMessageDialog(null, "Peso non accettabile, scrivi un numero (se decimale usa il punto)", "Errore", JOptionPane.ERROR_MESSAGE);
			
			pesoField.setBorder(rosso);
			return;
		}
		if (peso <= 0) //se il peso è negativo o nullo, il valore non è accettabile
		{
			Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
		
			JOptionPane.showMessageDialog(null, "Peso non accettabile, scrivi un numero positivo", "Errore", JOptionPane.ERROR_MESSAGE);
		
			pesoField.setBorder(rosso);
			return;
			
		}
		
		//controllo che il valore assicurato sia accettabile, se è selezionata la spedizione assicurata
		double assicurato = 0;
		if (assicurataDot.isSelected())
		{
			try
			{
				assicurato = Double.parseDouble(assicuratoField.getText());
			}
			catch (NumberFormatException e)
			{
				Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
				
				JOptionPane.showMessageDialog(null, "Valore assicurato non accettabile, scrivi un numero (se decimale usa il punto)", "Errore", JOptionPane.ERROR_MESSAGE);
				
				assicuratoField.setBorder(rosso);
				return;
			}
			assicurato = arrotonda(assicurato);  //faccio in modo che il valore assicurato abbia solo 2 cifre decimali
			if (assicurato <= 0) //se il valore assicurato è negativo o nullo, il valore non è accettabile
			{
				Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
			
				JOptionPane.showMessageDialog(null, "Valore assicurato non accettabile, scrivi un numero positivo", "Errore", JOptionPane.ERROR_MESSAGE);
			
				assicuratoField.setBorder(rosso);
				return;
				
			}
			
			
		}
				
		//leggo la spedizione
		leggiFile(peso, assicurato);
		
		
		
	}
	
	
	private void leggiFile(double peso, double assicurato)
	{
		try
		{
			ArchivioSpedizioni al;
			ObjectInputStream ios = new ObjectInputStream (new FileInputStream (fileSpedizioni));
			al = (ArchivioSpedizioni) ios.readObject(); 
			ios.close();
			
			Spedizione nuova;
			if (normaleDot.isSelected())
				nuova = new Spedizione (utente, destinazioneField.getText(), peso, new Date());
			else
				nuova = new SpedizioneAssicurata (utente, destinazioneField.getText(), peso, new Date(), assicurato);
			
			al.add(nuova);
			//scrivo la spedizione
			
			scriviFile(al, nuova);
			
		}
		catch (IOException e)
		{
			System.out.println("errore nella lettura" + e.toString());
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("classe non trovata" + e.toString());
		} 
	}
	
	private void scriviFile(ArchivioSpedizioni al, Spedizione nuova)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (fileSpedizioni));
			oos.writeObject(al);
			oos.close();
			
			JOptionPane.showMessageDialog(null, "Spedizione registrata con successo!");
			
			ThreadIniziale thread = new ThreadIniziale (nuova.getCodice(), fileSpedizioni);  //faccio partire il thread
			
			thread.start();

			
		}
		catch (IOException e)
		{
			System.out.println("errore nella scrittura" + e.toString());
		} 
	}
	
	private boolean cercaErrori()
	{
		//controllo che tutti i campi siano pieni
		if (!assicurataDot.isSelected()) // quando la spedizione è normale
		{
			if (pesoField.getText().equals("") || destinazioneField.getText().equals(""))
			{
				Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
				
				JOptionPane.showMessageDialog(null, "Inserisci tutti i campi", "Errore", JOptionPane.ERROR_MESSAGE);
				
				if (pesoField.getText().equals(""))
					pesoField.setBorder(rosso);
				else
					pesoField.setBorder(dborder);
				
				if (destinazioneField.getText().equals(""))
					destinazioneField.setBorder(rosso);
				else
					destinazioneField.setBorder(dborder);
				
				
				if (!assicurataDot.isSelected())
					assicurataDot.setBorder(dborder);
				return false;
			}
		}
		else  //quando la spedizione è assicurata
		{
			if (pesoField.getText().equals("") || assicuratoField.getText().equals("") || destinazioneField.getText().equals(""))
			{
				Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
				
				JOptionPane.showMessageDialog(null, "Inserisci tutti i campi", "Errore", JOptionPane.ERROR_MESSAGE);
				
				if (pesoField.getText().equals(""))
					pesoField.setBorder(rosso);
				else
					pesoField.setBorder(dborder);
				
				if (assicuratoField.getText().equals(""))
					assicuratoField.setBorder(rosso);
				else
					assicuratoField.setBorder(dborder);
				
				if (destinazioneField.getText().equals(""))
					destinazioneField.setBorder(rosso);
				else
					destinazioneField.setBorder(dborder);
				
				
				if (!assicurataDot.isSelected())
					assicurataDot.setBorder(dborder);
				return false;
				
			}
		}
			
		
		//coloro tutti i bordi di nero
		destinazioneField.setBorder(dborder);
		assicuratoField.setBorder(dborder);
		pesoField.setBorder(dborder);
		
		
		
		
		//controllo che la destinazione abbia almeno 2 caratteri
		if (destinazioneField.getText().length() == 1)
		{
			
			Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
			
			JOptionPane.showMessageDialog(null, "La destinazione deve avere almeno 2 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
			
			destinazioneField.setBorder(rosso);
			return false;
			
		}
		return true;
	}
	
	private double arrotonda(double assicurato)
	{
		assicurato*=100;
		int intero = (int) assicurato;
		assicurato = (double) intero / 100;
		
		return assicurato;
		
	}
}
