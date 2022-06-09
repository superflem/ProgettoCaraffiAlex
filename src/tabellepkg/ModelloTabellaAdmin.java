package tabellepkg;

import javax.swing.table.AbstractTableModel;

import classipkg.ArchivioSpedizioni;
import classipkg.SpedizioneAssicurata;

/**
 * Modello della tabella del pannello di controllo dell'admin.
 * @author Alex Caraffi
 */
public class ModelloTabellaAdmin extends AbstractTableModel
{
	private static final long serialVersionUID = 11L;
	
	
	private ArchivioSpedizioni al;

	/**
	 * Costruttore del modello.
	 * Viene costruita partendo dall'ArrayList delle spedizioni.
	 * @param al ArrayList dell'archivio delle spedizioni. 
	 */
	public ModelloTabellaAdmin (ArchivioSpedizioni al)
	{
		this.al = al;
	}
	
	
	
	
	
	/**
	 * Numero delle colonne.
	 * @return
	 * Restituisce quante colonne sono presenti nella tabella ovvero 7 che sono: codice, mittente, destinazione, peso, data, valore assicurato, stato.
	 */
	@Override
	public int getColumnCount() 
	{
		return 7;
	}

	
	/**
	 * Numero delle righe.
	 * @return
	 * Restituisce quante righe sono presenti nella tabella ovvero la dimensione dell'ArrayList.
	 */
	@Override
	public int getRowCount() 
	{
		return al.size();  
	}

	/**
	 * Oggetto in posizione [riga; colonna].
	 * @param riga riga nella tabella
	 * @param colonna colonna nella tabella
	 * @return
	 * Restituisce l'oggetto in posizione [riga; colonna].
	 */
	@Override
	public Object getValueAt(int riga, int colonna) 
	{		
		switch (colonna)
		{
			case 0: return al.get(riga).getCodice();
			case 1: return al.get(riga).getUtente();
			case 2: return al.get(riga).getDestinazione();
			case 3: return al.get(riga).getPeso() + " kg";
			case 4: return al.get(riga).getData();
			case 5:
				if (al.get(riga) instanceof SpedizioneAssicurata)  //se è una spedizione assicurata ritorno il suo valore
				{
					SpedizioneAssicurata spedizione = (SpedizioneAssicurata) al.get(riga);
					return spedizione.getValoreAssicurato() + " €";
				}
				//restituisco -
				return "-";
				
			default: return al.get(riga).getStato();
		}
		
	}
	
	
	/**
	 * Nome della colonna.
	 * @return
	 * Restituisce il nome della colonna nella posizione passata come parametro.
	 */
	@Override
	public String getColumnName(int colonna)
	{
		switch(colonna)
		{
			case 0: return "CODICE";
			case 1: return "MITTENTE";
			case 2: return "DESTINAZIONE";
			case 3: return "PESO";
			case 4: return "DATA";
			case 5: return "VALORE ASSICURATO";
			default: return "STATO";
		}

	}
}
