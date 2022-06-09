package classipkg;

import java.util.Date;

/**
 * Classe che implementa la struttura di una spedizione assicurata. Estende la classe spedizione.
 * @author Alex Caraffi
 *  
 */
public class SpedizioneAssicurata extends Spedizione{

	private static final long serialVersionUID = 3L;
	private double valoreAssicurato;
	
	/**
	 * Inizializzo la spedizione assicurata.
	 * 
	 * Il codice della spedizione è generato unendo i primi due caratteri del nome utente, gli ultimi 2 caratteri
	 * della destinazione, le decine e le unità della metà del peso, i primi 2 caratteri dell'hash della data e 3 numeri scelti in modo casuale.
	 * 
	 * @param utente username dell'utente che ha generato la spedizione
	 * @param destinazione indirizzo di destinazione
	 * @param peso peso della spedizione
	 * @param data data in cui è avvenuta la spedizione
	 * @param valoreAssicurato valore che verrà rimborsato in caso di fallimento
	 */
	public SpedizioneAssicurata (String utente, String destinazione, double peso, Date data, double valoreAssicurato)
	{
		super (utente, destinazione, peso, data);
		this.valoreAssicurato = valoreAssicurato;
	}
	
	/**
	 * Costruttore per inizializzare una nuova spedizione senza generare il codice.
	 * 
	 * Con questo costruttore il codice non viene generato random ma viene passato come parametro.
	 * 
	 * @param utente username dell'utente che ha generato la spedizione
	 * @param destinazione indirizzo di destinazione
	 * @param peso peso della spedizione
	 * @param data data in cui è avvenuta la spedizione
	 * @param valoreAssicurato valore che verrà rimborsato in caso di fallimento
	 * @param codice codice che viene passato come parametro
	 * 
	 */
	public SpedizioneAssicurata (String utente, String destinazione, double peso, Date data, double valoreAssicurato, String codice)
	{
		super (utente, destinazione, peso, data, codice);
		this.valoreAssicurato = valoreAssicurato;
	}
	
	
	/**
	 * Modifica lo stato.
	 * 
	 * @param nuovoStato nuovo stato della spedizione
	 * Modifica lo stato della spedizione
	 * 
	 * @return Restituisce true se il parametro è un valore accettabile, altrimenti false.
	 * I valori accettabili sono: IN TRANSITO, RICEVUTA, FALLITA, RIMBORSO RICHIESTO, RIMBORSO EROGATO.
	 */
	@Override
	public boolean setStato (String nuovoStato)
	{
		if (super.setStato(nuovoStato))
			return true;
		else if (nuovoStato.equals("RIMBORSO EROGATO") || nuovoStato.equals("RIMBORSO RICHIESTO"))
		{
			stato = nuovoStato;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Valore assicurato.
	 * 
	 * @return
	 * Restituisce il valore assicurato della spedizione.
	 */
	public double getValoreAssicurato()
	{
		return valoreAssicurato;
	}

}
