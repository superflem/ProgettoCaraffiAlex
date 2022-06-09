package classipkg;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe che gestisce le spedizioni presenti nell'archivio tramite un ArrayList.
 * La classe è serializzabile.
 * @author Alex Caraffi
 */
public class ArchivioSpedizioni implements Serializable
{
	
	private ArrayList <Spedizione> al;	
	private static final long serialVersionUID = 2L;
	
	
	/**
	 * Inizializzo l'ArrayList.
	 */
	public ArchivioSpedizioni ()
	{
		al = new ArrayList<Spedizione>();
	}
	
	/**
	 * Aggiungo una spedizione all'archivio.
	 * @param spedizione sedizione da aggiungere all'archivio
	 */
	public void add (Spedizione spedizione)
	{
		al.add(spedizione);
	}
	
	/**
	 * Ricerca di una spedizione dato il suo codice.
	 * @param codice codice della spedizione da cercare.
	 * @return
	 * Ritorno la spedizione con il codice assegnato. Se non lo trovo restituisco null.
	 */
	public Spedizione get (String codice)
	{
		for (int i = 0; i < al.size(); i++)
		{
			if (al.get(i).getCodice().equals(codice))
				return al.get(i);
		}
		
		return null;
	}
	
	/**
	 * Ricerca di una spedizione dato il suo indice.
	 * @param i indice della spedizione
	 * @return
	 * Ritorno la spedizione in posizione i.
	 */
	public Spedizione get (int i)
	{
		return al.get(i);
	}
	
	
	/**
	 * Dimensione dell'archivio.
	 * @return
	 * Restituisco la dimensione dell'archivio.
	 */
	public int size ()
	{
		return al.size();
	}
	
	/**
	 * Rimozione di una spedizione dato il suo indice.
	 * @param i indice della spedizione
	 * Elimina l'elemento in posizione i.
	 */
	public void remove(int i)
	{
		al.remove(i);
	}

}
