package classipkg;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe che gestisce gli utenti presenti nell'archivio tramite un ArrayList.
 * La classe è serializzabile.
 * @author Alex Caraffi
 * 
 */

public class ArchivioUtenti implements Serializable
{
	private ArrayList <Utente> al;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Inizializzo l'ArrayList.
	 */
	public ArchivioUtenti ()
	{
		al = new ArrayList<Utente>();
	}
	
	/**
	 * Aggiungo un utente all'archivio.
	 * @param utente username dell'utente
	 * 
	 */
	public void add (Utente utente)
	{
		al.add(utente);
	}
	
	/**
	 * Utente in posizione i.
	 * @param i indice dell'utente che voglio restituire
	 * 
	 * @return
	 * Restituisco l'utente in posizione i.
	 */
	public Utente get (int i)
	{
		return al.get(i);
	}
	
	
	/**
	 * Dimensione dell'archivio.
	 * @return
	 * Restituisce la lunghezza dell'ArrayList.
	 */
	public int size ()
	{
		return al.size();
	}
	

}
