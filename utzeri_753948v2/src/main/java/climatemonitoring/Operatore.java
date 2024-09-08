package climatemonitoring;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Operatore extends UnicastRemoteObject implements ClientInterface {

	
	private InterfacciaServer remote;
	private Scanner sc = new Scanner(System.in);
	String id ="";
	boolean dbcredentials = false;
	
	public Operatore() throws RemoteException, NotBoundException {
		Registry reg = LocateRegistry.getRegistry();
		remote = (InterfacciaServer)reg.lookup("MonitoringCenter");
	}
	
	public void cercaAreaGeografica() throws RemoteException {
		
		if(dbcredentials == false) {
			System.out.println("Non hai effettuato l'accesso al database.");
			return;
		}
		System.out.println("Desideri effettuare la ricerca dell'area tramite il nome? Digita 'true'. Altrimenti digita 'false'");
    	boolean byname = sc.nextBoolean();
    	System.out.println("Inserisci la denominazione dell'area che vuoi cercare.");
    	String den = sc.nextLine();
    	System.out.println("Inserisci le coordinate geografiche dell'area che vuoi cercare.");
    	String coordinates = sc.nextLine();
        remote.cercaAreaGeografica(byname,den, coordinates);
	}
	
	 public String registraCentro(String centro,String indirizzo,List<String> areeDiInteresse) throws RemoteException {
		 if(dbcredentials == false) {
				return "Non hai effettuato l'accesso al database.";
				
			}
		 if (!id.equals("")){
	          
	          return remote.registraCentro(centro, indirizzo,areeDiInteresse);
	    }else{
         return "Non hai effettuato il login";
       }

	}
	 
	 public String associaCentro(String associateCenter) throws RemoteException {
	        if (!dbcredentials) {
	            return "Non hai effettuato l'accesso al database.";
	        }

	        if (!id.equals("")) {
	            if (associateCenter != null) {
	                // Chiamata al metodo remoto, i parametri vengono passati dalla GUI
	                
	                return remote.associaCentro(id, associateCenter);
	            } else {
	                return "\nParametri non validi.";
	            }
	        } else {
	            return "\nNon hai effettuato il login.";
	        }
	    }

	 
	 public String loginUser(String username, String password) throws RemoteException, NotBoundException {
		    if (!dbcredentials) {
		        return "Non hai effettuato l'accesso al database.";
		    }
		    
		    // Effettua il login tramite i parametri passati dalla GUI
		    id = remote.loginUser(username, password);
		    
		    if (!id.isEmpty()) {
		        return "Login avvenuto con successo!";
		    } else {
		        return "Credenziali non valide!";
		    }
		}
	 
	 
	 public String registerUser(String username,String password) throws RemoteException, NotBoundException{
		 
		 if(dbcredentials == false) {
				return"Non hai effettuato l'accesso al database.";
				
			}
	        return remote.registerUser(username, password);
		 
	 }
	 
	 public String inserisciParametriClimatici(String area, int vento, int umidita, int pressione, int temperatura, int precipitazioni, int altitudineGhiacciai, int massaGhiacciai, String note) throws RemoteException {
	        if (dbcredentials && !id.equals("")) {
	           return remote.inserisciParametriClimatici(id,area, vento, umidita, pressione, temperatura, precipitazioni, altitudineGhiacciai, massaGhiacciai, note);
	        } else {
	            return "DBMS non configurato o login non effettuato.";
	        }
	    }
	 
	 public String visualizzaAreaGeografica()throws RemoteException{
		 if(dbcredentials == false) {
				return "Non hai effettuato l'accesso al database.";
				
			}
		 System.out.println("Inserisci il nome dell'area della quale desideri visualizzare i parametri climatici registrati.");
		 String area = sc.nextLine();
		 return remote.visualizzaAreaGeografica(area);
	 }
	 
	 public void logout() {
		 id = "";
		 System.out.println("Logout avvenuto con successo!");
	 }
	 
	 public void registerSuccess() {
		 System.out.println("Registrazione avvenuta con successo!");
	 }
	 
	 public void loginSuccess() {
		 System.out.println("Login avvenuto con successo!");
	 }
	 
	 public void getDbmsCredential() throws RemoteException {
			System.out.println("Inserisci l'host del DB");
			String dbhost = sc.nextLine();
			System.out.println("Inserisci lo user del DB");
			String dbuser = sc.nextLine();
			System.out.println("Inserisci la password del DB");
			String dbpsw = sc.nextLine();
			dbcredentials = remote.getDbmsCredential(dbhost, dbuser, dbpsw);
			
	    }
	 
	 public void registerFailure()throws RemoteException{
		 System.out.println("Username già esistente!");
	 }
	 
	 public void loginFailure()throws RemoteException{
		 System.out.println("Credenziali non valide!");
	 }
	 
	 
	 


	 
	 
}
