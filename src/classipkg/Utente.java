package classipkg;

import java.io.Serializable;

/**
 * Classe per la gestione degli utenti.
 * @author Alex Caraffi
 * 
 */
public class Utente implements Serializable
{
	private static final long serialVersionUID = 4L;
	private String username;
	private int password;
	private String indirizzo;
	
	/**
	 * Creazione di un nuovo utente.
	 * Crea un nuovo utente con username, password cifrata e indirizzo.
	 * @param username nome dell'utente
	 * @param password password cifrata
	 * @param indirizzo indirizzo dell'utente
	 * 
	 * 
	 */
	
	public Utente (String username, int password, String indirizzo)
	{
		this.username = username;
		this.password = password;
		this.indirizzo = indirizzo;
	}
	
	/**
	 * Password cifrata.
	 * @return
	 * Restituisce la password cifrata.
	 */
	public int getPassword()
	{
		return password;
	}
	
	/**
	 * Username.
	 * @return
	 * Restituisce l'username.
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * Indirizzo.
	 * @return
	 * Restituisce l'indirizzo.
	 * 
	 */
	public String getIndirizzo()
	{
		return indirizzo;
	}	
}
