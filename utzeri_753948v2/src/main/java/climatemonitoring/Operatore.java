package climatemonitoring;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Operatore {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry reg = LocateRegistry.getRegistry();
		InterfacciaServer remote = (InterfacciaServer)reg.lookup("MonitoringCenter");
		
		boolean cond=true;
	    String id="";
	    Scanner sc = new Scanner(System.in);
	    while(cond) {
	    System.out.println("Enter 8 to view climate parameters registered for an area");
	    System.out.println("Enter 7 to insert new parameters (operators only)");
	    System.out.println("Enter 6 to associate to a Monitoring Center (operators only)");
	    System.out.println("Enter 5 to register a Monitoring Center (operators only)");
	    System.out.println("Enter 4 to logout");
	    System.out.println("Enter 3 to login as an operator");
	    System.out.println("Enter 2 to register as an operator");
	    System.out.println("Enter 1 to research an Area");
	    System.out.println("Enter 0 to close the program");
	    System.out.print(">");
	    String tmp=sc.nextLine();
	      
	    switch(tmp){
	      case "1":
	    	System.out.println("Desideri effettuare la ricerca dell'area tramite il nome? Digita 'true'. Altrimenti digita 'false'");
	    	boolean byname = sc.nextBoolean();
	    	System.out.println("Inserisci la denominazione dell'area che vuoi cercare.");
	    	String denominazione = sc.nextLine();
	    	System.out.println("Inserisci le coordinate geografiche dell'area che vuoi cercare.");
	    	String coordinates = sc.nextLine();
	        remote.cercaAreaGeografica(byname,denominazione, coordinates);
	        break;
	      case "0":
	        cond=false;
	        break;
	      case "2":
	    	System.out.println("Inserisci il nome utente con il quale desideri registrarti.");
	    	String user = sc.nextLine();
	    	System.out.println("Inserisci una password da associare al tuo account.");
	    	String pass = sc.nextLine();
	        System.out.println(remote.registerUser(user, pass));
	        break;
	      case "3":
	    	System.out.println("Inserisci il tuo nome utente.");
	    	String userlog = sc.nextLine();
	    	System.out.println("Inserisci la tua password.");
	    	String userpass = sc.nextLine();
	        id=remote.loginUser(userlog, userpass);
	        if(!id.equals("")) System.out.println("login avvenuto con successo");
	        else System.out.println("credenziali errate");
	        break;
	      case "4":
	        if (!id.equals("")){
	          id = null;
	          System.out.println("logout effettuato");
	          break;
	        }
	        else {
	          System.out.println("Non sei loggato");
	          break;
	        }
	          
	      case "5":
	        if (!id.equals("")){
	          System.out.println("Inserisci il nome del centro di monitoraggio che desideri registrare al sistema.");
	          String name = sc.nextLine();
	          System.out.println("Inserisci il suo indirizzo.");
	          String address = sc.nextLine();
	          remote.registraCentro(name, address);
	          break;
	        }
	        else{
	          System.out.println("Non hai effettuato il login");
	          break;
	        }
	      case "6":
	        if(!id.equals("")){
		      
	        	String associateuser = id;
	        	System.out.println("Inserisci il nome del centro di monitoraggio al quale desideri associarti.");
	        	String associatecenter = sc.nextLine();
	        	System.out.println(remote.associaCentro(associateuser, associatecenter));
	        	break;
	        }
	        else{
	          System.out.println("Non hai effettuato il login");
	          break;
	        }
	      case "7":
	    	  if(!id.equals("")) {
	    		  System.out.println(remote.inserisciParametriClimatici(id));
	    		  break;
	    	  }
	    	  else {
	    		  System.out.print("Non hai effettuato il login");
	    		  break;
	    	  }
	      case "8":
	    	  System.out.println("Inserisci il nome dell'area della quale desideri visualizzare i parametri registrati");
	          String area = sc.nextLine();
	    	  System.out.println(remote.visualizzaAreaGeografica(area));
	    	  break;
	      default:
	        System.out.println("comando non valido");
	    
	      }
	    }
	    System.out.println("execution finished");
	}
}
