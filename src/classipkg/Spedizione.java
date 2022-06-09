package classipkg;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Classe che implementa la struttura di una spedizione.
 * @author Alex Caraffi
 */
public class Spedizione implements Serializable{
	private static final long serialVersionUID = 1L;
	private String codice;
	private String destinazione;
	private String utente;
	protected String stato;
	private double peso;
	private Date data;
	
	
	/**
	 * Inizializzo una nuova spedizione generando il codice.
	 * 
	 * Crea una nuova spedizione con lo stato IN PREPARAZIONE.
	 * Il codice della spedizione è generato unendo i primi due caratteri del nome utente, gli ultimi 2 caratteri
	 * della destinazione, le decine e le unità della metà del peso e i primi 2 caratteri dell'hash della data e 3 cifre scelte in modo casuale.
	 * 
	 * @param utente utente che genera la spedizione
	 * @param destinazione indirizzo di destinazione
	 * @param peso quanto pesa il pacco da spedire
	 * @param data quando è stata spedita
	 */
	public Spedizione (String utente, String destinazione, double peso, Date data)
	{
		this.utente = utente;
		this.peso = peso;
		this.destinazione = destinazione;
		this.data = data;
		stato = "IN PREPARAZIONE";
		
		 // primi due caratteri del nome utente
		codice = "" + utente.charAt(0) + utente.charAt(1);
				
		// ultimi due caratteri della destinazione
		codice += "" + destinazione.charAt(destinazione.length() -2) + destinazione.charAt(destinazione.length() -1);
		
		// decine e unità di metà del peso
		int pesoI = (int) peso;
		pesoI /= 2;
		if (pesoI < 10)
			codice += "0" + pesoI;
		else
		{
			String pesoS = "" + pesoI;
			codice += "" + pesoS.charAt(pesoS.length() -2) + pesoS.charAt(pesoS.length() -1);
		}
		
		// i primi due caratteri del codice hash della data
		int hash = data.hashCode();
		if (hash < 0)
			hash*=-1;
		String dataS = "" + hash;
		codice += "" + dataS.charAt(0) + dataS.charAt(1);
		
		//tre cifre scelte random
		Random rnd = new Random();
		for (int i = 0; i < 3; i++)
		{
			int cifra = rnd.nextInt(9);
			String cifras = "" + cifra;
			codice += "" + cifras;
		}
	}
	
	/**
	 * Inizializzo una nuova spedizione senza generare il codice.
	 * 
	 * Con questo costruttore, il codice non viene generato casualmente ma viene passato come parametro.
	 * 
	 * @param utente utente che genera la spedizione
	 * @param destinazione indirizzo di destinazione
	 * @param peso quanto pesa il pacco da spedire
	 * @param data quando è stata spedita
	 * @param codice codice della spedizione
	 */
	public Spedizione (String utente, String destinazione, double peso, Date data, String codice)
	{
		this.utente = utente;
		this.destinazione = destinazione;
		this.peso = peso;
		this.data = data;
		this.codice = codice;
		
		stato = "IN PREPARAZIONE";
	}
	
	
	/**
	 * Modifica dello stato.
	 * Modifica lo stato della spedizione.
	 * @param nuovoStato nuovo stato della spedizione
	 * @return
	 * Restituisce true se il parametro è un valore accettabile, altrimenti false.
	 * Gli stati possibili sono: IN TRANSITO, RICEVUTA, FALLITA.
	 */
	public boolean setStato (String nuovoStato)
	{
		if (nuovoStato.equals("IN TRANSITO") || nuovoStato.equals("RICEVUTA") || nuovoStato.equals("FALLITA"))
		{
			stato = nuovoStato;
			return true;
		}
		return false;
		
	}
	
	/**
	 * Codice della spedizione.
	 * @return
	 * Restituisce il codice della spedizione.
	 */
	public String getCodice ()
	{
		return "" + codice;
	}
	
	/**
	 * Utente mittente.
	 * @return
	 * Restituisce l'utente che ha creato la spedizione.
	 */
	public String getUtente ()
	{
		return utente;
	}
	
	/**
	 * Destinazione della spedizione.
	 * @return
	 * Restituisce la destinazione della spedizione.
	 */
	public String getDestinazione ()
	{
		return destinazione;
	}
	
	/**
	 * Peso della spedizione.
	 * @return
	 * Restituisce il peso della spedizione.
	 */
	public double getPeso()
	{
		return peso;
	}
	
	/**
	 * Data della spedizione in formato stringa.
	 * @return
	 * Restituisce la data della spedizione in formato: yyyy-MM-dd HH:mm:ss.
	 */
	public String getData ()
	{
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatoData = new SimpleDateFormat(pattern);

		return formatoData.format(data);
	}
	
	/**
	 * Data della spedizione in formato Date.
	 * @return
	 * Restituisce la data in formato Date.
	 */
	public Date getDatad ()
	{

		return data;
	}
	
	/**
	 * Stato della spedizione.
	 * @return
	 * Restituisce lo stato della spedizione.
	 */
	public String getStato()
	{
		return stato;
	}

}
