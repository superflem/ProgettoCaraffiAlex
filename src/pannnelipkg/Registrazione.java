package pannnelipkg;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import classipkg.ArchivioUtenti;
import classipkg.Utente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Classe che implementa il pannello per la registrazione di un nuovo utente.
 * <br>
 * <img src='../../foto/registrazione.jpg' alt='pannello di registrazione di un nuovo utente' >
 * @author Alex Caraffi
 */
public class Registrazione extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 6L;
	
	private JTextField username;
	private JTextField indirizzo;
	private JPasswordField password;
	private JPasswordField rpassword;
	private JButton registrati;
	private JButton indietro;
	
	private Border bdefault;
	
	private JFrame frame;

	private File fileSpedizioni;
	/**
	 * Costruttore del pannello Registrazione.
	 * Creo il pannello per efettuare la registrazione di un nuovo utente. Il JFrame passato come parametro corrisponde al JFrame dell'applicazione e serve per    
	 * cambiare i pannelli. La variabile fileSpedizioni contiene un riferimento al file delle spedizioni e serve per sincronizzare i thread.
	 * @param f JFrame del progetto
	 * @param fileSpedizioni riferimento al file spedizioni
	 * 
	 */
	
	public Registrazione(JFrame f, File fileSpedizioni) {
		super ();
		this.fileSpedizioni = fileSpedizioni;
		frame = f;
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel labelTitolo = new JLabel("NUOVO UTENTE");
		springLayout.putConstraint(SpringLayout.NORTH, labelTitolo, 36, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, labelTitolo, 246, SpringLayout.WEST, this);
		labelTitolo.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(labelTitolo);
		
		JLabel labelUsername = new JLabel("Inserisci il tuo username");
		springLayout.putConstraint(SpringLayout.NORTH, labelUsername, 110, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, labelUsername, 111, SpringLayout.WEST, this);
		add(labelUsername);
		
		JLabel labelIndirizzo = new JLabel("Inserisci il tuo indirizzo");
		springLayout.putConstraint(SpringLayout.NORTH, labelIndirizzo, 38, SpringLayout.SOUTH, labelUsername);
		add(labelIndirizzo);
		
		JLabel labelPassword = new JLabel("Inserisci la tua password");
		springLayout.putConstraint(SpringLayout.NORTH, labelPassword, 43, SpringLayout.SOUTH, labelIndirizzo);
		add(labelPassword);
		
		JLabel labelRPassword = new JLabel("Reinserisci la tua password");
		springLayout.putConstraint(SpringLayout.NORTH, labelRPassword, 39, SpringLayout.SOUTH, labelPassword);
		add(labelRPassword);
		
		username = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, username, 44, SpringLayout.SOUTH, labelTitolo);
		springLayout.putConstraint(SpringLayout.WEST, username, 345, SpringLayout.WEST, this);
		add(username);
		username.setColumns(10);
		
		bdefault = username.getBorder();  //bordo di default dei JTextField
		
		indirizzo = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, indirizzo, 345, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, labelIndirizzo, -79, SpringLayout.WEST, indirizzo);
		springLayout.putConstraint(SpringLayout.NORTH, indirizzo, -5, SpringLayout.NORTH, labelIndirizzo);
		add(indirizzo);
		indirizzo.setColumns(10);
		
		password = new JPasswordField();
		springLayout.putConstraint(SpringLayout.WEST, password, 345, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, password, -175, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.EAST, labelPassword, -79, SpringLayout.WEST, password);
		springLayout.putConstraint(SpringLayout.NORTH, password, -5, SpringLayout.NORTH, labelPassword);
		password.setEchoChar('*');
		add(password);
		
		rpassword = new JPasswordField();
		springLayout.putConstraint(SpringLayout.WEST, rpassword, 345, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, rpassword, -175, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.EAST, labelRPassword, -79, SpringLayout.WEST, rpassword);
		springLayout.putConstraint(SpringLayout.NORTH, rpassword, -5, SpringLayout.NORTH, labelRPassword);
		rpassword.setEchoChar('*');
		add(rpassword);
		
		registrati = new JButton("Registrati");
		springLayout.putConstraint(SpringLayout.WEST, registrati, 273, SpringLayout.WEST, this);
		add(registrati);
		
		indietro = new JButton("Indietro");
		springLayout.putConstraint(SpringLayout.NORTH, indietro, 373, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, indietro, 273, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, indietro, -273, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, registrati, -15, SpringLayout.NORTH, indietro);
		add(indietro);
		
		registrati.addActionListener(this);
		indietro.addActionListener(this);
	}

	
	
	/**
	 * Gestore degli eventi.
	 * Se premo il pulsante indietro, torno alla schermata di login, altrimenti registro il nuovo utente se possibile.
	 * @param arg0 evento che fa partire il metodo
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if (actionCommand.equals("Indietro"))  //se premo il pulsante indietro
		{
			cambiaPannello("Indietro", "");
			
			return;
		}
		
		//acquisico i valori della form
		String passwordIn = new String (password.getPassword());
		String usernameIn = username.getText();
		String rpasswordIn = new String (rpassword.getPassword());
		String indirizzoIn = indirizzo.getText();
		
		if (!cercaErrori(passwordIn, usernameIn, rpasswordIn, indirizzoIn))  //vedo se tutto è stato inserito correttamente
			return;
		
		
		
		leggiFile(passwordIn, usernameIn, indirizzoIn);  //leggo e scrivo il file degli utenti
	}
	
	private void cambiaPannello(String dove, String usernameIn)
	{
		frame.setVisible(false);
		frame.remove(this);
		
		if (dove.equals("Home"))
			frame.add(new Home(usernameIn, frame, fileSpedizioni));
		else
			frame.add(new Index(frame, fileSpedizioni));
		
		frame.setVisible(true);
	}

	private boolean cercaErrori(String passwordIn, String usernameIn, String rpasswordIn, String indirizzoIn)
	{
		if (passwordIn.equals("") || usernameIn.equals("") || rpasswordIn.equals("") || indirizzoIn.equals("")) //se manca un campo, viene segnalato
		{
			Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
			
			JOptionPane.showMessageDialog(null, "Inserisci i campi mancanti", "Errore", JOptionPane.ERROR_MESSAGE);
			
			//coloro i bordi mancanti di rosso, altrimenti, li metto del bordo di default
			if (passwordIn.equals(""))		
				password.setBorder(rosso);
			else
				password.setBorder(bdefault);
			
			if (usernameIn.equals(""))
				username.setBorder(rosso);
			else
				username.setBorder(bdefault);
			
			if (rpasswordIn.equals(""))		
				rpassword.setBorder(rosso);
			else
				rpassword.setBorder(bdefault);
			
			if (indirizzoIn.equals(""))
				indirizzo.setBorder(rosso);
			else
				indirizzo.setBorder(bdefault);
			return false;
		}
		
		//coloro i bordi dei campi del colore di default
		username.setBorder(bdefault);
		password.setBorder(bdefault);
		indirizzo.setBorder(bdefault);
		rpassword.setBorder(bdefault);
		
		if (usernameIn.length() == 1) // controllo che l'username abbia più di due caratteri
		{
			JOptionPane.showMessageDialog(null, "L'username deve avere almeno due caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
			Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
			
			username.setBorder(rosso);
			
			return false;
		}
		
		
		if (!passwordIn.equals(rpasswordIn))  //controllo che le due password siano uguali
		{
			JOptionPane.showMessageDialog(null, "Le due password non sono uguali", "Errore", JOptionPane.ERROR_MESSAGE);
			Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
			
			rpassword.setBorder(rosso);
			password.setBorder(rosso);
			return false;
		}
		
		return true;
	}

	private void leggiFile(String passwordIn, String usernameIn, String indirizzoIn)
	{
		File percorso = new File ("");
		int indiceProgetto = percorso.getAbsolutePath().indexOf("ProgettoCaraffiAlex");
		String percorsoRoot = percorso.getAbsolutePath().substring(0, indiceProgetto + 19);
		File file = new File (percorsoRoot + File.separator + "file" + File.separator + "utenti.dat");
		
		try
		{
			//importo gli utenti gia registrati per vedere se l'username è già usato
			ArchivioUtenti utenti;	
			
			ObjectInputStream ios = new ObjectInputStream (new FileInputStream (file));
			utenti = (ArchivioUtenti) ios.readObject();
			ios.close();
			
			
			for (int i = 0; i < utenti.size(); i++) //cerco se c'è gia un utente con lo stesso username
			{
				if (utenti.get(i).getUsername().equals(usernameIn) || usernameIn.hashCode() == 3506402)  //se scelgo come nome utente uno gia in uso
				{
					JOptionPane.showMessageDialog(null, "Username già in uso", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			utenti.add(new Utente (usernameIn, passwordIn.hashCode(), indirizzoIn));
			
			scriviFile(file, utenti);
			
			
			
		}
		catch (IOException e)
		{
			System.out.println("errore nella lettura" + e.toString());
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("classe non trovata" + e.toString());
		} 
		
		JOptionPane.showMessageDialog(null, "Utente registrato!");
		
		cambiaPannello("Home", usernameIn);
	}
	
	private void scriviFile(File file, ArchivioUtenti utenti)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (file));
			oos.writeObject(utenti);
			oos.close();
		}
		catch (IOException e)
		{
			System.out.println("errore nella scrittura" + e.toString());
		} 
	}
}
