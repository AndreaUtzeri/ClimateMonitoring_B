package climatemonitoring;

import java.util.Scanner;
import java.util.LinkedList;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/** Classe contenente tutti i metodi dell'applicazione. */
public class Metodi {
	
	/** Costruttore privato. I metodi sono tutti statici, perciò non c'è necessità di istanziare le classe. */
	private Metodi() {}
	/** Metodo utilizzato dagli operatori per registrarsi al sistema. */
	
	public static String registrazione() {
		try {
			
			BufferedWriter bf =new BufferedWriter(new FileWriter("OperatoriRegistrati.txt",true));//tenere true per non sovrascrivere il file
			Scanner sc = new Scanner(System.in);
			System.out.print("Id >");
			String id=sc.nextLine();
			System.out.println("Password >");
			String password=sc.nextLine();
			BufferedReader br = new BufferedReader(new FileReader("OperatoriRegistrati.txt"));
			String line;
			while((line=br.readLine())!=null) {
				String[] words = line.split(" ");
				if(words[0].equals(id)){
					System.out.println("Nome utente già in uso, riprovare");
					return null;
				}
			}
			bf.write(id+" "+password);
			bf.newLine();
			bf.close();
			br.close();
			System.out.println("Registrazione avvenuta con successo");
			return id;
		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Errore nella registrazione, riprovare");
			return null;
		}

	}
	
	
								///////LOGIN\\\\\\\\\
	
	/** Metodo utilizzato dagli operatori per effettuare il login. 
		@return L'id dell'utente che ha effettuato il login.
	*/
	
	public static String login() throws IOException {
		  try {
		    BufferedReader br = new BufferedReader(new FileReader("OperatoriRegistrati.txt"));
		    Scanner sc = new Scanner(System.in);
		    System.out.println("Id >");
		    String id=sc.nextLine();
		    System.out.println("Password >");
		    String password=sc.nextLine();
		    String line;
		    while((line=br.readLine())!=null) {
		      if(line.contains(id+" "+password)) {
		        System.out.println("Login effettuato con successo");
		        return id;}
		    }
		    System.out.println("id o password errati");
		    br.close();
		    return null;
		  }
		  catch (FileNotFoundException e) {
		    e.printStackTrace();
		    return null;
		    }
		  }
	
                   
					///////CERCA AREA GEOGRAFICA\\\\\\\\\\
	
	
	/**
	Stampa a schermo informazioni generiche relative all'area geografica inserita dall'utente (tramite nome o coordinate).
	*/
	
	public static void cercaAreaGeografica() throws IOException{
	    
	      BufferedReader br = null;
	    try {
	      br = new BufferedReader(new FileReader("CoordinateMonitoraggio.txt"));
	      System.out.println("Inserisci 1 se vuoi cercare l'area inserendo il suo nome.\nInserisci 2 se vuoi cercare l'area inserendo le sue coordinate geografiche.");
	      Scanner sc = new Scanner(System.in);
	      String scelta = sc.nextLine();
	      String line;
	      
	      switch(scelta){
	        
	      case "1":
	        System.out.print("Località: >");
	        String localita=sc.nextLine();
	        
	      
	        while((line=br.readLine())!=null) {
	          String[] words = line.split(" ");
	          if((words[0].toLowerCase()).equals((localita.toLowerCase()).replaceAll("\\s",""))){
	            System.out.println(line);
	            return;
	          }
	        
	        }
	  
	        System.out.print("Nessun risultato trovato");
	        break;
	      
	      case "2":
	        System.out.println("Latitudine: >");
	        String lat = sc.nextLine();
	        System.out.println("Longitudine: >");
	        String lon = sc.nextLine();
	        
	        while((line=br.readLine())!=null) {
	          if(line.contains(lat)&&line.contains(lon)){
	            System.out.println(line);
	            return;
	          }
	        
	        }
	        
	        System.out.print("Nessun risultato trovato");
	        break;
	        
	      case "default":
	        System.out.println("Comando non valido");
	        break;
	        
	      
	      }
	      
	    }
	    catch(FileNotFoundException e) {
	      e.printStackTrace();
	    }
	    finally{
	      br.close();
	    }
	  }
	
	/**
	Metodo utilizzato dagli operatori per registrare un centro di monitoraggio ed associare ad esso le aree geografiche di sua competenza.
	*/
	
	public static void registraCentroAree() throws IOException {
		try {
			boolean cond=true;
			BufferedWriter bf =new BufferedWriter(new FileWriter("CentroMonitoraggio.txt",true));
			BufferedReader br = new BufferedReader(new FileReader("CentroMonitoraggio.txt"));
			Scanner sc = new Scanner(System.in);
			System.out.print("Nome centro di monitoraggio >");
			String nome=sc.nextLine();
			String localita="";
			String localitaTmp="";
			String line;
			boolean areaRegistrata=true;
			while(cond) {
				System.out.print("Area di interesse >");
				localitaTmp=sc.nextLine();
				br = new BufferedReader(new FileReader("CoordinateMonitoraggio.txt"));
				while((line=br.readLine())!=null) {
			          String[] words = line.split(" ");
			          if((words[0].toLowerCase()).equals((localitaTmp.toLowerCase()).replaceAll("\\s",""))){
			            areaRegistrata=false;
			            break;
			          }
			        
			        }
				if(areaRegistrata) {
					System.out.print("L'area inserita non è registrata nelle aree esistenti");
					return;
				}
				
				localita=(localita+localitaTmp.replaceAll("\\s","")+" ").toLowerCase();
				System.out.print("Premere invio per inserire un'altra area o digitare 0 per terminare");
				if(sc.nextLine().equals("0"))
					cond=false;
			}
			

			while((line=br.readLine())!=null) {
				String[] words = line.split(" ");
				if(words[0].equals(nome.replaceAll("\\s",""))){
					System.out.println("Centro di monitoraggio già registrato");
					return;
				}
			}
			
			
			bf.write(nome.replaceAll("\\s","")+" "+localita);
			bf.newLine();
			bf.close();
			System.out.println(" centro registrato con successo");
			
		
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("errore");
		}
	}
	
	/** Metodo utilizzato dagli operatori per associarsi ad un centro di monitoraggio registrato.
	@param id Id dell'operatore che ha effettuato il login. 
	*/
	
	public static void associaCentro(String id) throws IOException{
	      
	      try{
	      
	      Scanner sc = new Scanner(System.in);
	      System.out.println("Inserisci il nome del centro di monitoraggio al quale desideri associarti: ");
	      String nomeCentro= sc.nextLine();
	      nomeCentro=nomeCentro.replaceAll("\\s","");
	      BufferedReader br = new BufferedReader(new FileReader("CentroMonitoraggio.txt"));
	      String controllo; 
	      boolean condizioneuscita = true;
	      
	      while((controllo=br.readLine())!=null) {
	    	  if(controllo.contains(nomeCentro)) {
	    		  condizioneuscita = false;
	    		  break;}
	      }
	      
	      if(condizioneuscita){
	        System.out.println("Questo centro non e' registrato.");
	        return;
	      }
	      
	      br.close();
	      

	      BufferedReader lettore = new BufferedReader(new FileReader("OperatoriRegistrati.txt"));
	      String linea;
	      LinkedList<String> nuovotesto = new LinkedList<>();
	      
	      
	      while((linea=lettore.readLine())!=null) {
	        nuovotesto.add(linea);   //inserisci ogni linea nella linkedlist
	      }
	      
	      lettore.close();
	      
	      
	      for(int j=0; j < nuovotesto.size(); j++){
	        
	        String[] parole = new String[3];
	        String[] toCopy = nuovotesto.get(j).split(" ");  //
	        
	        for(int k= 0; k < toCopy.length; k++){
	          parole[k] = toCopy[k];
	        }
	        
	        if(parole[0].equals(id)) {
	          
	          if(parole[2] != null){  
	            System.out.println("Sei già associato ad un centro di monitoraggio");
	            return;
	          }else{
	            nuovotesto.set(j, nuovotesto.get(j) + " " + nomeCentro.replaceAll("\\s",""));
	            break;
	          }
	          
	        }
	      }
	      
	        BufferedWriter scrittore = new BufferedWriter(new FileWriter("OperatoriRegistrati.txt"));
	        
	        for(String f: nuovotesto){
	          scrittore.write(f);
	          scrittore.newLine();
	        }
	        scrittore.close();
	        System.out.println("Associazione effettuata con successo");
	      
	    
	      }catch(FileNotFoundException e){
	        e.printStackTrace();
	        System.out.println("Errore");
	      }  
	  }
	  
	  
	/** Metodo utilizzato dagli operatori per inserire i parametri climatici relativi ad una determinata area geografica. L'area dev'essere di competenza del centro associato all'operatore.
	@param id Id dell'operatore che ha effettuato il login. 
	*/

	public static void inserisciParametriClimatici(String id) throws IOException {
		BufferedWriter bf =new BufferedWriter(new FileWriter("ParametriClimatici.txt",true));
		BufferedReader br = new BufferedReader(new FileReader("OperatoriRegistrati.txt"));
		boolean cond=true;
		String line;
		String nomeCentro=null;
		while((line=br.readLine())!=null) {
			String[] words = line.split(" ");
			if(words[0].equals(id)) {						//Controllo associazione al centro
				if(words.length>2) {
					nomeCentro=words[2];
					cond=false;
					break;
					}
				}
		    }
		if(cond) {
			System.out.println("Non sei registrato ad alcun centro di monitoraggio");
			return;
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("Inserire l'area di interesse");
		String area=sc.nextLine();
		//String tmch=area.replaceAll("\\n","-"); 
		//String areaVisualizzata=tmch;
		/* questa variabile permette di avere area visualizzata con i giusti spazi quando l'utente guarderà
		  i parametri, con la variabile area invece si fa la ricerca all'internodei file di sitema, ad esempio
		  inserendo Busto Arsizio, useremo area per cercare bustoarsizio all'interno dei file di sistema
		  mentre una volta verificato che sia effettivamente un area di competenza del centro andremo a salvare nel file
		  dei parametri climatici la sua versione non strecchata*/
		
		
		BufferedReader lettore = new BufferedReader(new FileReader("CentroMonitoraggio.txt"));
		    boolean uscita = true;
		    
		    while((line=lettore.readLine())!=null){
		      if(line.contains(nomeCentro)){
		    	  line=line.toLowerCase();
		    	  if(line.contains(area.toLowerCase().replaceAll("\\s","")))
		          uscita = false;
		        break;
		      }
		    }
		    
		    lettore.close();
		    
		    if(uscita){
		      System.out.println("L'area che hai inserito non e' di competenza del tuo centro di monitoraggio");
		      return;    
		    }
		
		
		
		Date data=new Date();
		String escamotage=data+"";
		escamotage=escamotage.replaceAll("\\s","-");
		String vento=null;
		String umidita=null;
		String pressione=null;
		String temperatura=null;
		String pioggia=null;
		String altitudine=null;
		String massa=null;
		boolean tmp=true;
		while(tmp) {
			System.out.println("Inserire punteggio(1/5) relativo alla velocità del vento (km/h), suddivisa in fasce");
			vento=sc.nextLine();
			if(vento.equals("1")||vento.equals("2")||vento.equals("3")||vento.equals("4")||vento.equals("5")){
				tmp=false;
			}
			else System.out.println("il valore deve essere compreso fra 1 e 5");
		}
		tmp=true;
		while(tmp) {
			System.out.println("Inserire punteggio(1/5) relativo alla % di Umidità, suddivisa in fasce");
			umidita=sc.nextLine();
			if(umidita.equals("1")||umidita.equals("2")||umidita.equals("3")||umidita.equals("4")||umidita.equals("5")){
				tmp=false;
			}
			else System.out.println("il valore deve essere compreso fra 1 e 5");
		}
		
		tmp=true;
		while(tmp) {
			System.out.println("Inserire punteggio(1/5) relativo al hPa, suddivisa in fasce");
			pressione=sc.nextLine();
			if(pressione.equals("1")||pressione.equals("2")||pressione.equals("3")||pressione.equals("4")||pressione.equals("5")){
				tmp=false;
			}
			else System.out.println("il valore deve essere compreso fra 1 e 5");
		}
		
		tmp=true;
		while(tmp) {
			System.out.println("Inserire punteggio(1/5) relativo ai C°, suddivisa in fasce");
			temperatura=sc.nextLine();
			if(temperatura.equals("1")||temperatura.equals("2")||temperatura.equals("3")||temperatura.equals("4")||temperatura.equals("5")){
				tmp=false;
			}
			else System.out.println("il valore deve essere compreso fra 1 e 5");
		}
		
		tmp=true;
		while(tmp) {
			System.out.println("Inserire punteggio(1/5) precipitazioni in mm di pioggia,suddivisa in fasce");
			pioggia=sc.nextLine();
			if(pioggia.equals("1")||pioggia.equals("2")||pioggia.equals("3")||pioggia.equals("4")||pioggia.equals("5")){
				tmp=false;
			}
			else System.out.println("il valore deve essere compreso fra 1 e 5");
		}
		
		tmp=true;
		while(tmp) {
			System.out.println("Inserire punteggio(1/5) relativoin ai m, suddivisa in piogge");
			altitudine=sc.nextLine();
			if(altitudine.equals("1")||altitudine.equals("2")||altitudine.equals("3")||altitudine.equals("4")||altitudine.equals("5")){
				tmp=false;
			}
			else System.out.println("il valore deve essere compreso fra 1 e 5");
		}
		
		tmp=true;
		while(tmp) {
			System.out.println("Inserire punteggio(1/5) relativoin ai kg, suddivisa in fasce");
			massa=sc.nextLine();
			if(massa.equals("1")||massa.equals("2")||massa.equals("3")||massa.equals("4")||massa.equals("5")){
				tmp=false;
			}
			else System.out.println("il valore deve essere compreso fra 1 e 5");
		}
		
		boolean contacaratteri=true;
		String note="Nessuna nota inserita";
		boolean comando=true;
		while(comando) {
			System.out.println("Premere 1 per inserire una nota(max 256 caratteri) oppure 0 per non inserire");
			String scelta=sc.nextLine();
			switch(scelta) {
		
				case "1":
				
					while(contacaratteri) {
						System.out.println("Inserire le note:");
						note=sc.nextLine();
						if(note.length()>256) {
							System.out.println("Limite di 256 caratteri superato, rinserire la nota");
						}
						else contacaratteri=false;
					}
					comando=false;
					break;
			
				case "0":
					comando=false;
					break;
				default:System.out.println("Comando inserito non valido");
			
			}
		}
		bf.write(nomeCentro+" "+area.replaceAll("\\s","")+" "+escamotage+" "+vento+" "+umidita+" "+pressione+" "+temperatura+" "+pioggia+" "+altitudine+" "+massa+" "+note.replaceAll("\\s","-"));
		bf.newLine();
		bf.close();
		
		
	}
	
	/** Metodo utilizzato per visualizzare i parametri climatici registrati dai vari centri di monitoraggio relativamente ad una specifica area geografica.
	*/
	
	public static void visualizzaAreaGeografica() throws IOException{
	    
	    Scanner sc = new Scanner(System.in);
	    BufferedReader br = new BufferedReader(new FileReader("ParametriClimatici.txt"));
	    System.out.println("Inserisci il nome dell'area della quale desideri visualizzare i parametri registrati");
	    String area= sc.nextLine().replaceAll(" ", "");
	    System.out.println("");
	    String line;
	    String[] words= null;
	    boolean uscita = true;
	    
	    while((line = br.readLine()) != null){
	    	if((line.toLowerCase()).contains(area.toLowerCase())){
	        uscita = false;
	        words = line.split(" ");
	        System.out.println("Parametri registrati da: " + words[0]);
	        System.out.println("Data di registrazione dei parametri " + words[2]);
	        System.out.println("Punteggio assegnato al vento: " + words[3]);
	        System.out.println("Punteggio assegnato all'umidità: " + words[4]);
	        System.out.println("Punteggio assegnato alla pressione atmosferica: " + words[5]);
	        System.out.println("Punteggio assegnato alla temperatura: " + words[6]);
	        System.out.println("Punteggio assegnato alle precipitazioni: " + words[7]);
	        System.out.println("Punteggio assegnato all'altitudine dei ghiacciai: " + words[8]);
	        System.out.println("Punteggio assegnato alle massa dei ghiacciai: " + words[9]);
	        System.out.println("Note: " + words[10].replaceAll("-"," ") + '\n');
	      }
	    }
	    
	    br.close();
	    
	    if(uscita)
	      System.out.println("Non sono stati registrati parametri per l'area che hai inserito.");
	    
	    
	  }
	
	
	
	
}
