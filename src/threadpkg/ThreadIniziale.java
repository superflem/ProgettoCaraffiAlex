package threadpkg;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import classipkg.ArchivioSpedizioni;

/**
 * Classe che gestisce lo stato delle spedizioni, se IN TRANSITO, RICEVUTA o FALLITA.
 * @author Alex Caraffi
 *
 */
public class ThreadIniziale extends Thread
{
	private String codice;
	private ArchivioSpedizioni al;
	private Random rnd;
	private File fileSpedizioni;
	
	/**
	 * Costruttore del thread.
	 * 
	 * Inizializzo il thread che si riferisce alla spedizione con codice cod. Il parametro fileSpedizioni si riferisce al file delle spedizioni,
	 * in questo modo posso sincronizzare i thread.
	 * @param cod codice della spedizione
	 * @param fileSpedizioni riferimento al file delle spedizioni
	 * 
	 */
	public ThreadIniziale (String cod, File fileSpedizioni)
	{
		super();
		
		this.fileSpedizioni = fileSpedizioni;
		codice = cod;
		al = null;
		rnd = new Random();
	}
	
	
	/**
	 * Esecuzione del thread.
	 * Dopo 10 secondi la spedizione diventa IN TRANSITO, dopo altri 5 secondi diventa RICEVUTA o FALLITA
	 */
	@Override
	public void run ()
	{
		try
		{
			sleep(10000);  //dorme 10 secondi
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	
		
		//dopo 3 secondi metto lo stato IN TRANSITO, sincronizzando il file
		synchronized (fileSpedizioni)
		{
			leggiFile("IN TRANSITO");
		}	
		
		
		
		
		//PREPARO LO STATO SUCCESSIVO
		
		
		try
		{
			sleep(5000);  //dorme 5 secondi
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		int ricevuta = rnd.nextInt(100);
		
		if (ricevuta%2 == 0)  //se il numero estratto è pari, la spedizione è fallita
		{
			synchronized (fileSpedizioni)
			{
				leggiFile("FALLITA");
			}	
		}
		else
		{
			synchronized (fileSpedizioni)
			{
				leggiFile("RICEVUTA");
			}	
		}	
	}
	
	
	
	private void leggiFile(String nuovoStato)
	{
		try
		{
			ObjectInputStream ios = new ObjectInputStream (new FileInputStream (fileSpedizioni));
			al = (ArchivioSpedizioni) ios.readObject(); 
			ios.close();
			
			if (!al.get(codice).setStato(nuovoStato))  //scrivo il nuovo stato, controllando che sia accettabile
				return;
					
			//scrivo la spedizione con il nuovo stato
			
			scriviFile();
			
		}
		catch (IOException e)
		{
			System.out.println("errore nella lettura" + e.toString());
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("classe non trovata" + e.toString());
		} 
	}
		
	private void scriviFile()
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (fileSpedizioni));
			oos.writeObject(al);
			oos.close();
			
			
		}
		catch (IOException e)
		{
			System.out.println("errore nella scrittura" + e.toString());
		} 
	}
}
