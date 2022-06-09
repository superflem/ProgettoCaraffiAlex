package pannnelipkg;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import classipkg.ArchivioSpedizioni;
import classipkg.Spedizione;
import classipkg.SpedizioneAssicurata;
import tabellepkg.ModelloTabellaUtente;
import tabellepkg.TabellaUtente;
import threadpkg.ThreadAssicurato;

/**
 * Classe per la visualizzazione delle spedizioni di un utente normale.
 * <br>
 * <img src='../../foto/visualizza.jpg' alt='pannello di visualizzazione delle spedizioni' >
 * @author Alex Caraffi
 * 
 */
public class Visualizza extends JPanel implements ActionListener
{
	
	private static final long serialVersionUID = 10L;

	private JFrame frame;
    
	private TabellaUtente tabella;
	private JButton richiedi;
	private JButton home;
	private JButton ricarica;
	
	private ArchivioSpedizioni al;
	private ArchivioSpedizioni spedizioniUtente;
	
	private String utente;
	
	private File fileSpedizioni;
	
	/**
	 * Costruttore del pannello Visualizza.
	 * Cero la tabella delle spedizioni dell'utente. Il JFrame passato come parametro corrisponde al JFrame dell'applicazione e serve per
	 * cambiare i pannelli. La variabile fileSpedizioni contiene un riferimento al file delle spedizioni e serve per sincronizzare i thread.
	 * @param f JFrame del progetto
	 * @param user utente loggato
	 * @param fileSpedizioni riferimento al file delle spedizioni
	 * 
	 */
	public Visualizza(JFrame f, String user, File fileSpedizioni) 
	{
		frame = f;
		utente = user;
		this.fileSpedizioni = fileSpedizioni;
		leggiFile(); //leggo il file e carico l'aary list
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		
		ModelloTabellaUtente mta = new ModelloTabellaUtente (spedizioniUtente);
		tabella = new TabellaUtente(mta);
		
		tabella.getColumnModel().getColumn(0).setPreferredWidth(75);  //larghezza codice
		tabella.getColumnModel().getColumn(1).setPreferredWidth(100); //larghezza destinazione
		tabella.getColumnModel().getColumn(2).setPreferredWidth(40);  //larghezza peso
		tabella.getColumnModel().getColumn(3).setPreferredWidth(130);  //larghezza data
		tabella.getColumnModel().getColumn(4).setPreferredWidth(70);  //larghezza valore assicurato
		
		
		JLabel labelCancella = new JLabel("Premi per richiedere i rimborsi");
		springLayout.putConstraint(SpringLayout.WEST, labelCancella, 30, SpringLayout.WEST, this);
		add(labelCancella);
		
		richiedi = new JButton("Rimborso");
		springLayout.putConstraint(SpringLayout.WEST, richiedi, 123, SpringLayout.EAST, labelCancella);
		springLayout.putConstraint(SpringLayout.SOUTH, richiedi, -71, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, labelCancella, 5, SpringLayout.NORTH, richiedi);
		add(richiedi);
		
		JLabel labelLogout = new JLabel("Torna alla pagina Home");
		springLayout.putConstraint(SpringLayout.NORTH, labelLogout, 28, SpringLayout.SOUTH, labelCancella);
		springLayout.putConstraint(SpringLayout.EAST, labelLogout, 0, SpringLayout.EAST, labelCancella);
		add(labelLogout);
		
		home = new JButton("Home");
		springLayout.putConstraint(SpringLayout.NORTH, home, -5, SpringLayout.NORTH, labelLogout);
		springLayout.putConstraint(SpringLayout.WEST, home, 0, SpringLayout.WEST, richiedi);
		add(home);
		
		ricarica = new JButton("Ricarica");
		springLayout.putConstraint(SpringLayout.EAST, ricarica, 0, SpringLayout.EAST, this);
		add(ricarica);

		richiedi.addActionListener(this);
		ricarica.addActionListener(this);
		home.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane(tabella);
		springLayout.putConstraint(SpringLayout.SOUTH, ricarica, -6, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 43, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, richiedi);
		
		add(scrollPane);
		
		JLabel titolo = new JLabel(utente + ", queste sono le tue spedizioni");
		springLayout.putConstraint(SpringLayout.WEST, titolo, 120, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, titolo, -6, SpringLayout.NORTH, scrollPane);
		titolo.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		add(titolo);
		
	}

	/**
	 * Gestore degli eventi.
	 * Se premo il pulsante home, torno alla home; se premo il pulsante ricarica, ricarico il pannello altrimenti chiedo un rimborso per le spedizioni assicurate che sono fallite.
	 * @param arg0 evento che fa partire il metodo
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		String actionCommand = arg0.getActionCommand();
		
		if (actionCommand.equals("Home"))  //se premo il pulsante per tornare alla home
		{
			cambiaPannello("home");
			return;
		}
		
		if (actionCommand.equals("Ricarica"))  //se premo il pulsante per ricaricare la pagina
		{
			cambiaPannello("ricarica");
			return;
		}
		
		
		//se premo il pulsante per chiedere i rimborsi
		boolean trovato = false;
		for (int i = 0; i < spedizioniUtente.size(); i++)
		{
			if (spedizioniUtente.get(i).getStato().equals("FALLITA") && spedizioniUtente.get(i) instanceof SpedizioneAssicurata)  //se la spedizione è fallita ed è assicurata      
			{
				trovato = true;
				if (al.get( spedizioniUtente.get(i).getCodice() ).setStato("RIMBORSO RICHIESTO")) //se riesco a mettere lo stato RIMBORSO RICHIESTO faccio partire il thread
				{
					
					ThreadAssicurato thread = new ThreadAssicurato (al.get( spedizioniUtente.get(i).getCodice() ).getCodice(), fileSpedizioni);  //faccio partire il thread
					
					thread.start();
					
				}
				else
					return;
			}
		}
		
		if (trovato)  //se ho fatto partire almeno un thread lo comunico altrimenti dico che non c'erano spedizioni rimborsabili
		{
			JOptionPane.showMessageDialog(null, "Rimborso richiesto correttamente!");
			scriviFile();
			cambiaPannello("ricarica");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Non hai nessuna spedizione rimborsabile", "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void leggiFile ()
	{
		al = null;
		
		
		try
		{
			ObjectInputStream ios = new ObjectInputStream (new FileInputStream (fileSpedizioni));
			al = (ArchivioSpedizioni) ios.readObject(); 
			ios.close();
		}
		catch (IOException e)
		{
			System.out.println("errore nella lettura" + e.toString());
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("classe non trovata" + e.toString());
		} 
		
		spedizioniUtente = new ArchivioSpedizioni();  //creo una copia delle spedizioni dell'utente
		for (int i = 0; i < al.size(); i++)
		{
			if (al.get(i).getUtente().equals(utente))
			{
				if (al.get(i) instanceof SpedizioneAssicurata)  //controllo che tipo di spedizione è per creaerne una uguale
				{
					SpedizioneAssicurata ut = (SpedizioneAssicurata) al.get(i);
					spedizioniUtente.add(new SpedizioneAssicurata(ut.getUtente(), ut.getDestinazione(), ut.getPeso(), ut.getDatad(), ut.getValoreAssicurato(), ut.getCodice()));     
				}
				else
				{
					Spedizione ut = al.get(i);
					spedizioniUtente.add(new Spedizione(ut.getUtente(), ut.getDestinazione(), ut.getPeso(), ut.getDatad(), ut.getCodice()));
				}
				
				spedizioniUtente.get( al.get(i).getCodice() ).setStato( al.get(i).getStato());
			}
		}
	}

	private void cambiaPannello (String dove)
	{
		frame.setVisible(false);
		frame.remove(this);
		
		if (dove.equals("home"))
			frame.getContentPane().add(new Home(utente, frame, fileSpedizioni));
		else
			frame.getContentPane().add(new Visualizza(frame, utente, fileSpedizioni));
		
		frame.setVisible(true);
	}
	
	private void scriviFile()
	{	
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (fileSpedizioni));
			oos.writeObject(al); 
			oos.close();
		}
		catch (IOException e)
		{
			System.out.println("errore nella lettura" + e.toString());
		} 
	}

}
