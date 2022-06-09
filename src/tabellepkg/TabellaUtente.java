package tabellepkg;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Classe che estende una JTable e costruisce una tabella per l'utente normale.
 * @author Alex Caraffi
 */
public class TabellaUtente extends JTable
{
	private static final long serialVersionUID = 13L;
	private ModelloTabellaUtente modello;
	
	/**
	 * Costruttore della tabella. 
	 * Inizializzo il modello della tabella.
	 * @param mt modello tabella utente
	 */
	public TabellaUtente (ModelloTabellaUtente mt)
	{
		super(mt);
		modello = mt;
	}
	
	
	/**
	 * Coloro la tabella.
	 * Metodo per colorare le righe della tabella in base allo stato.<br>
	 * FALLITA = rosso<br>
	 * RICEVUTA = verde<br>
	 * RIMBORSO EROGATO = giallo<br>
	 * RIMBORSO RICHIESTO = arancione<br>
	 * IN TRANSITO = rosa<br>
	 * IN PREPARAZIONE = ciano<br>
	 * @param cella rendering del contenuto di una cella della tabella
	 * @param riga numero della riga
	 * @param colonna numero della colonna
	 * @return
	 * Restituisce la riga colorata.
	 */
	@Override
	public Component prepareRenderer(TableCellRenderer cella, int riga, int colonna) 
	{
		Component rigaTabella = super.prepareRenderer(cella, riga, colonna);

		
		//le celle sono bianche
		rigaTabella.setBackground(Color.WHITE);
		

		//coloro la tabella in base ai colori
		if ( modello.getValueAt(riga, 6).equals("FALLITA") ) 
		{
			rigaTabella.setBackground(Color.RED);
		}
		
		if ( modello.getValueAt(riga, 6).equals("RICEVUTA") ) 
		{
			rigaTabella.setBackground(Color.GREEN);
		}
		
		if ( modello.getValueAt(riga, 6).equals("RIMBORSO EROGATO") ) 
		{
			rigaTabella.setBackground(Color.YELLOW);
		}
		
		if ( modello.getValueAt(riga, 6).equals("RIMBORSO RICHIESTO") ) 
		{
			rigaTabella.setBackground(Color.ORANGE);
		}
		
		if ( modello.getValueAt(riga, 6).equals("IN TRANSITO") ) 
		{
			rigaTabella.setBackground(Color.PINK);
		}
		
		if ( modello.getValueAt(riga, 6).equals("IN PREPARAZIONE") ) 
		{
			rigaTabella.setBackground(Color.CYAN);
		}

		return rigaTabella;
	}
}
