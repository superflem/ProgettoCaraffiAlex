package mainpkg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.JFrame;

import classipkg.*;
import pannnelipkg.Index;

/**
 * Classe principale che contiene il metodo main. Il programma inizia da qua, viene creato un JFrame con un pannello Index.
 * @author Alex Caraffi
 */
public class Main {

	/**
	 * Metodo iniziale.
	 * @param args argomenti passati come parametri all'avvio del programma
	 */
	public static void main(String[] args)
	{
	
		//metto due utenti nel database
		/*
		File percorso = new File ("");
		int indiceProgetto = percorso.getAbsolutePath().indexOf("ProgettoCaraffiAlex");
		String percorsoRoot = percorso.getAbsolutePath().substring(0, indiceProgetto + 19);
		File file = new File (percorsoRoot + File.separator + "file" + File.separator + "utenti.dat");	
		try
		{
			ArchivioUtenti al = new ArchivioUtenti();
			al.add(new Utente ("alex", "caraffi".hashCode(), "via labellarte"));
			al.add(new Utente ("sara", "davalli".hashCode(), "via peppino"));
			
			
			
			
			ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (file));
			oos.writeObject(al);
			oos.close();
			
			
			System.out.println("utenti");
		}
		catch (IOException e)
		{
			System.out.println("errore" + e.toString());
		}
		*/

		
		//metto una spedizione nel database
		
		File percorso2 = new File ("");
		int indiceProgetto2 = percorso2.getAbsolutePath().indexOf("ProgettoCaraffiAlex");
		String percorsoRoot2 = percorso2.getAbsolutePath().substring(0, indiceProgetto2 + 19);
		File file2 = new File (percorsoRoot2 + File.separator + "file" + File.separator + "spedizioni.dat");
		
		try
		{
			ArchivioSpedizioni al = new ArchivioSpedizioni();
			al.add(new Spedizione ("root", "root", 0, new Date()));
			
			System.out.println("spedizione");
			piuSpedizioni (al); //aggiungo pi spedizioni
			
			ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (file2));
			oos.writeObject(al);
			oos.close();
			
			
		}
		catch (IOException e)
		{
			System.out.println("errore" + e.toString());
			return;
		}
		
		
		
		//creo il JFrame e inizia il programma con la pagina index
		JFrame f = new JFrame ("Poste");
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(650,440);
		
		
		f.add(new Index(f, file2));
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setResizable(false);	
	}
	
	
	private static void piuSpedizioni(ArchivioSpedizioni al)
	{
		System.out.println("piu spedizioni");
		al.add(new SpedizioneAssicurata ("root", "root", 0, new Date(), 1));
		al.get(1).setStato("RIMBORSO RICHIESTO");
		
		al.add(new SpedizioneAssicurata ("root", "root", 0, new Date(), 2));
		al.get(2).setStato("RIMBORSO EROGATO");
		
		al.add(new SpedizioneAssicurata ("root", "root", 2, new Date(), 3));
		al.get(3).setStato("IN TRANSITO");
		
		al.add(new Spedizione ("root", "root", 0, new Date()));
		al.get(4).setStato("FALLITA");
		
		al.add(new Spedizione ("root", "root", 1, new Date()));
		al.get(5).setStato("RICEVUTA");
		
		al.add(new SpedizioneAssicurata ("root", "root", 0, new Date(), 6));
		al.get(6).setStato("FALLITA");
		
		al.add(new SpedizioneAssicurata ("root", "root", 0, new Date(), 1));
		al.get(7).setStato("RIMBORSO RICHIESTO");
		
		al.add(new SpedizioneAssicurata ("root", "root", 0, new Date(), 2));
		al.get(8).setStato("RIMBORSO EROGATO");
		
		al.add(new SpedizioneAssicurata ("root", "root", 2, new Date(), 3));
		al.get(9).setStato("IN TRANSITO");
		
		al.add(new Spedizione ("root", "root", 0, new Date()));
		al.get(10).setStato("FALLITA");
		
		al.add(new Spedizione ("root", "root", 1, new Date()));
		al.get(11).setStato("RICEVUTA");
		
		al.add(new SpedizioneAssicurata ("root", "root", 0, new Date(), 6));
		al.get(12).setStato("FALLITA");
		
		al.add(new Spedizione ("root", "root", 0, new Date()));
		al.get(13).setStato("FALLITA");
		
		al.add(new Spedizione ("root", "root", 1, new Date()));
		al.get(14).setStato("RICEVUTA");
		
		al.add(new SpedizioneAssicurata ("root", "root", 0, new Date(), 6));
		al.get(15).setStato("FALLITA");
		
		al.add(new Spedizione ("root", "root", 0, new Date()));
		al.get(16).setStato("FALLITA");
		
		al.add(new Spedizione ("root", "root", 1, new Date()));
		al.get(17).setStato("RICEVUTA");
		
		al.add(new SpedizioneAssicurata ("root", "root", 0, new Date(), 6));
		al.get(18).setStato("FALLITA");
		
	}
	
}
