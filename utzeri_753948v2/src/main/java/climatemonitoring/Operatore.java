package climatemonitoring;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Operatore {

	
	private InterfacciaServer remote;
	private Scanner sc = new Scanner(System.in);
	boolean id = false;
	
	public Operatore() throws RemoteException, NotBoundException {
		Registry reg = LocateRegistry.getRegistry();
		remote = (InterfacciaServer)reg.lookup("MonitoringCenter");
	}
	
	public void cercaAreaGeografica() throws RemoteException {
		
		System.out.println("Desideri effettuare la ricerca dell'area tramite il nome? Digita 'true'. Altrimenti digita 'false'");
    	boolean byname = sc.nextBoolean();
    	System.out.println("Inserisci la denominazione dell'area che vuoi cercare.");
    	String den = sc.nextLine();
    	System.out.println("Inserisci le coordinate geografiche dell'area che vuoi cercare.");
    	String coordinates = sc.nextLine();
        remote.cercaAreaGeografica(byname,den, coordinates);
	}
	
	 public void registraCentro() throws RemoteException {
		 if (id != false){
	          System.out.println("Inserisci il nome del centro di monitoraggio che desideri registrare al sistema.");
	          String name = sc.nextLine();
	          System.out.println("Inserisci il suo indirizzo.");
	          String address = sc.nextLine();
	          remote.registraCentro(name, address);
	    }else{
         System.out.println("Non hai effettuato il login");
       }

	}
	 
	 public void associaCentro() throws RemoteException{
		 if(id != false){
		      System.out.println("Inserisci il tuo nome utente.");
		      String associateuser = sc.nextLine();
	          System.out.println("Inserisci il nome del centro di monitoraggio al quale desideri associarti.");
	          String associatecenter = sc.nextLine();
	          remote.associaCentro(associateuser, associatecenter);
	          
	        }
	        else{
	          System.out.println("Non hai effettuato il login");
	        }
	 }
	 
	 public void loginUser() throws RemoteException{
		 System.out.println("Inserisci il tuo nome utente.");
	    	String userlog = sc.nextLine();
	    	System.out.println("Inserisci la tua password.");
	    	String userpass = sc.nextLine();
	        id=remote.loginUser(userlog, userpass);
	 }
	 
	 
	 public void registerUser() throws RemoteException{
		 
		 System.out.println("Inserisci il nome utente con il quale desideri registrarti.");
	    	String user = sc.nextLine();
	    	System.out.println("Inserisci una password da associare al tuo account.");
	    	String pass = sc.nextLine();
	        id=remote.registerUser(user, pass);
		 
	 }
	 
	 public void inserisciParametriClimatici()throws RemoteException{
		 
		 System.out.println("Inserisci il tuo nome utente.");
		 String username = sc.nextLine();
		 
		  if(id != false) {
    		  remote.inserisciParametriClimatici(username);
    	  }
    	  else {
    		  System.out.print("Non hai effettuato il login");
    	  }
		  
	 }
	 
	 public void visualizzaAreaGeografica()throws RemoteException{
		 System.out.println("Inserisci il nome dell'area della quale desideri visualizzare i parametri climatici registrati.");
		 String area = sc.nextLine();
		 remote.visualizzaAreaGeografica(area);
	 }
	 
	 public void logout() {
		 id = false;
	 }
	 


	 
	 
}
