package pannnelipkg;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Classe per la pagina home dell'utente appena loggato.
 * <br>
 * <img src="../../foto/home.jpg" alt="pannello per la pagina home dell'utente" >
 * @author Alex Caraffi
 * 
 */
public class Home extends JPanel implements ActionListener
{	
	private static final long serialVersionUID = 7L;
	
	
	private String utente;
	private JFrame frame;
	
	private JButton nuovo;
	private JButton visualizza;
	private JButton logout;
	
	private File fileSpedizioni;

	/**
	 * Costruttore del pannello Home.
	 * Creo il pannello per efettuare la gestione della pagina home. Il JFrame passato come parametro corrisponde al JFrame dell'applicazione e serve per    
	 * cambiare i pannelli; la stringa passata come parametro corrisponde all'utente loggato. 
	 * La variabile fileSpedizioni contiene un riferimento al file delle spedizioni e serve per sincronizzare i thread. 
	 * @param utente utente loggato
	 * @param f JFrame del progetto
	 * @param fileSpedizioni riferimento al file delle spedizioni
	 *       
	 */
	public Home(String utente, JFrame f, File fileSpedizioni) 
	{
		super();
		this.fileSpedizioni = fileSpedizioni;
		frame = f;
		this.utente = utente;
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel titolo = new JLabel("Benvenuto/a, " + utente);
		springLayout.putConstraint(SpringLayout.NORTH, titolo, 43, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, titolo, 240, SpringLayout.WEST, this);
		titolo.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		add(titolo);
		
		JLabel labelSpedizione = new JLabel("Effettua una nuova spedizione");
		springLayout.putConstraint(SpringLayout.NORTH, labelSpedizione, 84, SpringLayout.SOUTH, titolo);
		springLayout.putConstraint(SpringLayout.WEST, labelSpedizione, 92, SpringLayout.WEST, this);
		add(labelSpedizione);
		
		nuovo = new JButton("Nuovo");
		springLayout.putConstraint(SpringLayout.NORTH, nuovo, -5, SpringLayout.NORTH, labelSpedizione);
		springLayout.putConstraint(SpringLayout.WEST, nuovo, 89, SpringLayout.EAST, labelSpedizione);
		add(nuovo);
		
		JLabel labelVisualizza = new JLabel("Visualizza le tue spedizioni");
		springLayout.putConstraint(SpringLayout.EAST, labelVisualizza, 0, SpringLayout.EAST, labelSpedizione);
		add(labelVisualizza);
		
		visualizza = new JButton("Visualizza");
		springLayout.putConstraint(SpringLayout.NORTH, visualizza, -5, SpringLayout.NORTH, labelVisualizza);
		springLayout.putConstraint(SpringLayout.WEST, visualizza, 90, SpringLayout.EAST, labelVisualizza);
		add(visualizza);
		
		JLabel labelLogout = new JLabel("Effettua il Log Out");
		springLayout.putConstraint(SpringLayout.SOUTH, labelVisualizza, -60, SpringLayout.NORTH, labelLogout);
		springLayout.putConstraint(SpringLayout.NORTH, labelLogout, 315, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, labelLogout, 0, SpringLayout.EAST, labelSpedizione);
		add(labelLogout);
		
		logout = new JButton("Log Out");
		springLayout.putConstraint(SpringLayout.NORTH, logout, -5, SpringLayout.NORTH, labelLogout);
		springLayout.putConstraint(SpringLayout.WEST, logout, 0, SpringLayout.WEST, nuovo);
		add(logout);
		
		
		logout.addActionListener(this);
		visualizza.addActionListener(this);
		nuovo.addActionListener(this);

	}


	
	/**
	 * Gestore degli eventi.
	 * Gestisco la scelta dei pulsanti, se devo inserire una nuova spedizione, visualizzare le spedizioni dell'utente o tornare all'indice.
	 * @param arg0 evento che fa partire il metodo
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if (actionCommand.equals("Log Out"))  //se premo il pulsante di log out
		{
			cambiaPannello("Log Out");
			return;
		}
		
		if (actionCommand.equals("Visualizza"))  //se premo il pulsante visualizza
		{
			
			cambiaPannello("Visualizza");
			return;
		}
		
		// se premo il pulsante per inserire una nuova spedizione
		
		cambiaPannello("nuovo");
		
		
	}
	
	
	private void cambiaPannello(String dove)
	{
		frame.setVisible(false);
		frame.remove(this);
		
		if (dove.equals("Log Out"))
			frame.getContentPane().add(new Index(frame, fileSpedizioni));
		else if (dove.equals("Visualizza"))
			frame.add(new Visualizza(frame, utente, fileSpedizioni));
		else
			frame.getContentPane().add(new Nuovo(utente, frame, fileSpedizioni));
		frame.setVisible(true);
	}
}
