package pannnelipkg;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import classipkg.ArchivioSpedizioni;
//import classipkg.Spedizione;
import classipkg.SpedizioneAssicurata;
import tabellepkg.ModelloTabellaAdmin;
import tabellepkg.TabellaAdmin;
//import threadpkg.ThreadIniziale;

//import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JScrollBar;
import java.awt.Font;

/**
 * Classe per la gestione del pannello dell'admin.
 * <br>
 * <img src="../../foto/admin.jpg" alt="pannello di controllo dell'utente" >
 * @author Alex Caraffi
 */
public class Admin extends JPanel implements ActionListener
{	
	private static final long serialVersionUID = 10L;

	private JFrame frame;
    
	private TabellaAdmin tabella;
	private JButton elimina;
	private JButton logout;
	private JButton ricarica;
	
	private ArchivioSpedizioni al;
	private File fileSpedizioni;
	
	/**
	 * Costruttore del pannello Admin.
	 * Il JFrame passato come parametro corrisponde al JFrame dell'applicazione e serve per
	 * cambiare i pannelli. La variabile fileSpedizioni contiene un riferimento al file delle spedizioni e serve per sincronizzare i thread.
	 * 
	 * @param f JFrame del progetto
	 * @param fileSpedizioni riferimento al file spedizioni
	 */
	
	public Admin(JFrame f, File fileSpedizioni) 
	{
		frame = f;
		this.fileSpedizioni = fileSpedizioni;
		leggiFile(); //leggo il file e carico l'array list
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		
		ModelloTabellaAdmin mta = new ModelloTabellaAdmin (al);
		tabella = new TabellaAdmin(mta);
		
		tabella.getColumnModel().getColumn(0).setPreferredWidth(75);  //larghezza codice
		tabella.getColumnModel().getColumn(1).setPreferredWidth(45);  //larghezza utente mittente
		tabella.getColumnModel().getColumn(2).setPreferredWidth(100); //larghezza destinazione
		tabella.getColumnModel().getColumn(3).setPreferredWidth(40);  //larghezza peso
		tabella.getColumnModel().getColumn(4).setPreferredWidth(130);  //larghezza data
		tabella.getColumnModel().getColumn(5).setPreferredWidth(60);  //larghezza valore assicurato
		
		
		JLabel labelCancella = new JLabel("Premi per eliminare le spedizioni concluse");
		springLayout.putConstraint(SpringLayout.WEST, labelCancella, 30, SpringLayout.WEST, this);
		add(labelCancella);
		
		elimina = new JButton("Elimina");
		springLayout.putConstraint(SpringLayout.WEST, elimina, 123, SpringLayout.EAST, labelCancella);
		springLayout.putConstraint(SpringLayout.SOUTH, elimina, -71, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, labelCancella, 5, SpringLayout.NORTH, elimina);
		add(elimina);
		
		JLabel labelLogout = new JLabel("Effettua il Log Out");
		springLayout.putConstraint(SpringLayout.NORTH, labelLogout, 28, SpringLayout.SOUTH, labelCancella);
		springLayout.putConstraint(SpringLayout.EAST, labelLogout, 0, SpringLayout.EAST, labelCancella);
		add(labelLogout);
		
		logout = new JButton("Log Out");
		springLayout.putConstraint(SpringLayout.NORTH, logout, -5, SpringLayout.NORTH, labelLogout);
		springLayout.putConstraint(SpringLayout.WEST, logout, 0, SpringLayout.WEST, elimina);
		add(logout);
		
		ricarica = new JButton("Ricarica");
		springLayout.putConstraint(SpringLayout.EAST, ricarica, 0, SpringLayout.EAST, this);
		add(ricarica);

		elimina.addActionListener(this);
		ricarica.addActionListener(this);
		logout.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane(tabella);
		springLayout.putConstraint(SpringLayout.SOUTH, ricarica, -6, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 43, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, elimina);
		
		add(scrollPane);
		
		JLabel titolo = new JLabel("Benvenuto Amministratore");
		springLayout.putConstraint(SpringLayout.WEST, titolo, 182, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, titolo, -6, SpringLayout.NORTH, scrollPane);
		titolo.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		add(titolo);
		
	}

	
	/**
	 * Gestore degli eventi.
	 * Se premo il pulsante di ricarica, ricarico il pannello; se premo il pulsante di logout, torno al pannello Index altrimenti elimino dal
	 * database le spedizioni con stato "RICEVUTA", "FALLITA" (solo se non assicurata) e "RIMBORSO EROGATO".
	 * @param arg0 evento che fa partire il metodo.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		String actionCommand = arg0.getActionCommand();
		
		if (actionCommand.equals("Log Out"))  //se premo il pulsante di log out
		{
			cambiaPagina("logout");
			return;
		}
		
		if (actionCommand.equals("Ricarica"))  //se premo il pulsante di ricarica
		{
			cambiaPagina("ricarica");
			return;
		}
		
		//se premo il pulsante elimina
		boolean trovato = false;
		
		for (int i = 0; i < al.size(); i++)
		{
			if (al.get(i).getStato().equals("RICEVUTA") || al.get(i).getStato().equals("RIMBORSO EROGATO"))  //se la spedizione è arrivata o è arrivato il rimborso la elimino dal database    
			{	
				trovato = true;
				al.remove(i);
				i--;
			}
			
			if (al.get(i).getStato().equals("FALLITA") && !(al.get(i) instanceof SpedizioneAssicurata)) //se la sepdizione è falita e non è assicurata
			{
				trovato = true;
				al.remove(i);
				i--;
			}
		}
		
		if (trovato)
		{
			JOptionPane.showMessageDialog(null, "Ho eliminato le spedizioni concluse");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Non ci sono spedizioni da eliminare", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//scrivo nel database
		scriviFile();
		
		cambiaPagina("ricarica");
	}
	
	
	
	
	
	
	
	
	
	private void cambiaPagina(String dove)
	{
		frame.setVisible(false);
		frame.remove(this);
		
		if (dove.equals("logout"))
			frame.getContentPane().add(new Index(frame, fileSpedizioni));
		else
			frame.getContentPane().add(new Admin(frame, fileSpedizioni));
		frame.setVisible(true);
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
