package threadpkg;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import classipkg.ArchivioSpedizioni;

/**
 * Classe per il rimborso delle evntuali spedizioni assicurate fallite.
 * @author Alex Caraffi
 */
public class ThreadAssicurato extends Thread
{
	private String codice;
	private ArchivioSpedizioni al;
	private File fileSpedizioni;
	
	/**
	 * Costruttore del Thread.
	 * 
	 * Inizializzo il thread che si riferisce alla spedizione con codice cod. Il parametro fileSpedizioni serve per sincronizzare i thread nella lettura  
	 * del file.
	 * @param cod codice della spedizione
	 * @param fileSpedizioni riferimento al file delle spedizioni
	 */
	public ThreadAssicurato (String cod, File fileSpedizioni)
	{
		super();
		
		this.fileSpedizioni = fileSpedizioni;
		codice = cod;
		al = null;
	}
	
	
	/**
	 * Esecuzione del thread. 
	 * Dopo 10 secondi, eroga il rimborso.
	 */
	@Override
	public void run()
	{
		try
		{
			sleep(10000);  //dorme 10 secondi
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		
		//dopo 10 secondi legge il file
		synchronized (fileSpedizioni)
		{
			leggiFile();
		}	
		
		
	}
	
	
	
	private void leggiFile()
	{
		try
		{
			ObjectInputStream ios = new ObjectInputStream (new FileInputStream (fileSpedizioni));
			al = (ArchivioSpedizioni) ios.readObject(); 
			ios.close();
			
			
			
			if (!al.get(codice).setStato("RIMBORSO EROGATO"))  //stato RIMBORSO EROGATO, controllo di non aver messo un valore corretto, se sbaglio, finisce il thread      
				return;
					
			//scrivo la spedizione con stato RIMBORSO EROGATO
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
