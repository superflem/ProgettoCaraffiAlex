package tabellepkg;

import javax.swing.table.AbstractTableModel;

import classipkg.ArchivioSpedizioni;
import classipkg.SpedizioneAssicurata;

/**
 * Modello della tabella per l'utente. 
 * @author Alex Caraffi
 *
 */
public class ModelloTabellaUtente extends AbstractTableModel
{
	private static final long serialVersionUID = 11L;
	private ArchivioSpedizioni al;
	
	/**
	 * Costruttore del modello. 
	 * Ha come input l'archivio delle spedizioni.
	 * @param al ArrayList dell'archivio delle spedizioni.
	 * 
	 */
	public ModelloTabellaUtente (ArchivioSpedizioni al)
	{
		this.al = al;
	}
	

	
	/**
	 * Numero delle colonne.
	 * @return
	 * Restituisce quante colonne sono presenti nella tabella ovvero 6 che sono: codice, destinazione, peso, data, valore assicurato, stato.
	 */
	@Override
	public int getColumnCount() 
	{
		return 6;
	}

	
	/**
	 * Numero delle righe
	 * @return
	 * Restituisce il numero di righe ovvero, la dimensione dell'archivio delle spedizioni.
	 */
	@Override
	public int getRowCount() 
	{
		return al.size();
	}

	/**
	 * Oggetto in posizione [riga; colonna].
	 * @param riga numero della riga
	 * @param colonna numero della colonna
	 * @return
	 * Restituisce l'ggetto nella tabella in posizione [riga; colonna].
	 */
	@Override
	public Object getValueAt(int riga, int colonna) 
	{
		switch(colonna)
		{
		case 0: return al.get(riga).getCodice();
		case 1: return al.get(riga).getDestinazione();
		case 2: return al.get(riga).getPeso() + " kg";
		case 3: return al.get(riga).getData();
		case 4:
			if (al.get(riga) instanceof SpedizioneAssicurata)  //se è una spedizione assicurata ritorno il suo valore
			{
				SpedizioneAssicurata spedizione = (SpedizioneAssicurata) al.get(riga);
				return spedizione.getValoreAssicurato() + " €";  //due decimali
			}
			//restituisco -
			return "-";
			
		default: return al.get(riga).getStato();
		}
	}
	
	
	
	/**
	 * Nome della colonna.
	 * @param colonna numero della colonna
	 * @return
	 * Restituisce il nome della colonna nella posizione passata come parametro.
	 */
	@Override
	public String getColumnName(int colonna)
	{
		switch(colonna)
		{
			case 0: return "CODICE";
			case 1: return "DESTINAZIONE";
			case 2: return "PESO";
			case 3: return "DATA";
			case 4: return "VALORE ASSICURATO";
			default: return "STATO";
		}

	}
}
