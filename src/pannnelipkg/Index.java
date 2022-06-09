package pannnelipkg;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import classipkg.ArchivioUtenti;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Classe per effettuare il login, la pagina iniziale del programma.
 * <br>
 * <img src='../../foto/index.jpg' alt='pannello per il login' >
 * @author Alex Caraffi
 * 
 */
public class Index extends JPanel implements ActionListener
{
	
	private static final long serialVersionUID = 5L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private JRadioButton adminDot;
	private JRadioButton utenteDot;
	
	private JButton accedi;
	private JButton registrati;
	
	private ButtonGroup gruppo;
	
	private JFrame frame;
	
	private Border bdefault; //serve per salvare il bordo di default delle JTextField 
	
	private File fileSpedizioni;

	/**
	 * Costruttore del pannello Index
	 * Creo il pannello iniziale per effettuare il login. Il JFrame passato come parametro corrisponde al JFrame dell'applicazione e serve per
	 * cambiare i pannelli. La variabile fileSpedizioni contiene un riferimento al file delle spedizioni e serve per sincronizzare i thread.
	 * @param f JFrame del progetto
	 * @param fileSpedizioni riferimento al file spedizioni
	 * 
	 */
	public Index(JFrame f, File fileSpedizioni) {
		super();
		this.fileSpedizioni = fileSpedizioni;
		frame = f;
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		usernameField = new JTextField(10);
		add(usernameField);
		
		bdefault = usernameField.getBorder();  //bordo di default dei JTextField
		
		
		JLabel username_label = new JLabel("Inserisci l'username");
		springLayout.putConstraint(SpringLayout.EAST, username_label, -38, SpringLayout.WEST, usernameField);
		JLabel label = new JLabel("Inserisci l'username");
		springLayout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, usernameField);
		springLayout.putConstraint(SpringLayout.EAST, label, -113, SpringLayout.WEST, usernameField);
		add(label);
		
		JLabel titolo = new JLabel("BENVENUTO NEL SERVIZIO POSTALE");
		springLayout.putConstraint(SpringLayout.NORTH, usernameField, 26, SpringLayout.SOUTH, titolo);
		springLayout.putConstraint(SpringLayout.EAST, usernameField, 0, SpringLayout.EAST, titolo);
		springLayout.putConstraint(SpringLayout.NORTH, titolo, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, titolo, 150, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, username_label, 23, SpringLayout.SOUTH, titolo);
		titolo.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(titolo);
		
		JLabel password_label = new JLabel("Inserisci la password");
		springLayout.putConstraint(SpringLayout.EAST, password_label, 0, SpringLayout.EAST, label);
		add(password_label);
		
		passwordField = new JPasswordField(10);
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 113, SpringLayout.EAST, password_label);
		springLayout.putConstraint(SpringLayout.EAST, passwordField, -150, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, password_label, 5, SpringLayout.NORTH, passwordField);
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, 42, SpringLayout.SOUTH, usernameField);
		passwordField.setEchoChar('*');
		add(passwordField);
		
		utenteDot = new JRadioButton("Utente");
		springLayout.putConstraint(SpringLayout.EAST, utenteDot, 0, SpringLayout.EAST, label);
		utenteDot.setSelected(true);
		add(utenteDot);
		
		adminDot = new JRadioButton("Admin");
		springLayout.putConstraint(SpringLayout.NORTH, utenteDot, 0, SpringLayout.NORTH, adminDot);
		springLayout.putConstraint(SpringLayout.NORTH, adminDot, 46, SpringLayout.SOUTH, passwordField);
		springLayout.putConstraint(SpringLayout.WEST, adminDot, 372, SpringLayout.WEST, this);
		add(adminDot);
		
		accedi = new JButton("Accedi");
		springLayout.putConstraint(SpringLayout.WEST, accedi, 282, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, accedi, -153, SpringLayout.SOUTH, this);
		add(accedi);
		
		JLabel registrati_label = new JLabel("Non hai un account?");
		springLayout.putConstraint(SpringLayout.SOUTH, registrati_label, -99, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, registrati_label, 0, SpringLayout.EAST, label);
		add(registrati_label);
		
		registrati = new JButton("Registrati");
		springLayout.putConstraint(SpringLayout.NORTH, registrati, -5, SpringLayout.NORTH, registrati_label);
		springLayout.putConstraint(SpringLayout.WEST, registrati, 0, SpringLayout.WEST, usernameField);
		add(registrati);
		
		
		gruppo = new ButtonGroup ();
		gruppo.add(utenteDot);
		gruppo.add(adminDot);
		
		
		
		registrati.addActionListener(this);
		accedi.addActionListener(this);
	}
	
	
	/**
	 * Gestore degli eventi.
	 * Quando viene premuto un pulsate si vede quale dei due è stato premuto. Se è stato premuto il pulsante Registrati, il programma va nella pagina di registrazione,       
	 * altrimenti controlla se l'username e la password inseriti corrispondono ad un'utente registrato nel database.
	 * @param arg0 evento che fa partire il metodo
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		String actionCommand = arg0.getActionCommand();
		
		if (actionCommand.equals("Registrati"))  //se premo il pulsante registrati, vado nella pagina per registrare un nuovo account
		{
			cambiaPagina("Registrati", "");
			return;
		}
		
		String password = new String (passwordField.getPassword());
		String username = usernameField.getText();
		if (cercaErrori(password, username))
			return;
		
		
		//se scelgo di fare il login come utente
		leggiFile (username, password);
	}
	
	
	private boolean cercaErrori(String password, String username)
	{
		if (password.equals("") || username.equals(""))  //se un campo da compilare è vuoto, scrivo un messaggio
		{
			Border rosso = BorderFactory.createLineBorder(Color.red);  //bordo rosso
			
			
			JOptionPane.showMessageDialog(null, "Inserisci i campi mancanti", "Errore", JOptionPane.ERROR_MESSAGE);
			//coloro i bordi mancanti di rosso, altrimenti, li metto del bordo di default
			if (password.equals(""))		
				passwordField.setBorder(rosso);
			else
				passwordField.setBorder(bdefault);
			
			if (username.equals(""))
				usernameField.setBorder(rosso);
			else
				usernameField.setBorder(bdefault);
			
			return true;
		}
		
		
		
		//coloro i bordi dei campi del colore di default
		usernameField.setBorder(bdefault);
		passwordField.setBorder(bdefault);
		
		if (adminDot.isSelected())  //se scelgo di fare il login come root
		{
			if (username.hashCode() == 3506402 && password.hashCode() == 3565982)  //se inserisco i caratteri giusti vado alla pagina di admin
			{
				cambiaPagina("Admin", "");
				return true;
	
			}
			else
				JOptionPane.showMessageDialog(null, "Utente non trovato", "Errore", JOptionPane.ERROR_MESSAGE);
			
			return true;
		}
		return false;
	}
	
	
	
	
	
	private void cambiaPagina(String dove, String username)
	{
		frame.setVisible(false);
		frame.remove(this);
		
		if (dove.equals("Registrati"))  //vai nella pagina registrati
			frame.add(new Registrazione(frame, fileSpedizioni));
		else if (dove.equals("Home"))  //vai nella pagina home
			frame.add(new Home(username, frame, fileSpedizioni));
		else //vai nella pagina di admin
			frame.getContentPane().add(new Admin(frame, fileSpedizioni));
		frame.setVisible(true);
	}
	
	
	
	
	
	private void leggiFile(String username, String password)
	{
		File percorso = new File ("");
		int indiceProgetto = percorso.getAbsolutePath().indexOf("ProgettoCaraffiAlex");
		String percorsoRoot = percorso.getAbsolutePath().substring(0, indiceProgetto + 19);
		File file = new File (percorsoRoot + File.separator + "file" + File.separator + "utenti.dat");
		
		try
		{
			
			
			ObjectInputStream ios = new ObjectInputStream (new FileInputStream (file));
			ArchivioUtenti utenti = (ArchivioUtenti) ios.readObject();
			ios.close();
			//cerco un utente che abbia username e password uguali a quelli inseriti. Se si, viene loggato, altrimenti no
			
			for (int i = 0; i < utenti.size(); i++)
			{
				if (utenti.get(i).getUsername().equals(username) && password.hashCode() == utenti.get(i).getPassword())  //vado alla pagina home
				{
					cambiaPagina ("Home", username);
					return;
				}
			}
			
			JOptionPane.showMessageDialog(null, "Utente non trovato", "Errore", JOptionPane.ERROR_MESSAGE);	
			
		}
		catch (IOException e)
		{
			System.out.println("errore" + e.toString());
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
