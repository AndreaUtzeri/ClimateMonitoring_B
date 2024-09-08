package climatemonitoring;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CentroMonitoraggioServer extends UnicastRemoteObject implements InterfacciaServer {

	private static final long serialVersionUID = 1L;
	private String dbHost;
    private String dbUser;
    private String dbPassword;
    
	
	public CentroMonitoraggioServer() throws RemoteException{
    	
	}
	
	
	
	public boolean getDbmsCredential(String dbHost, String dbUser, String dbPassword) throws RemoteException {
		this.dbHost = dbHost;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        System.out.println("prova per vedere se le credenziali vengono passate");
        return true;
    }
	public String getdbHost() {
		return dbHost;
	}
	public String getdbUser() {
		return dbUser;
	}
	public String getdbPassword() {
		return dbPassword;
	}
	
	
	
	// Metodo per registrare un nuovo utente
    public String registerUser(String username, String password) throws RemoteException, NotBoundException {
    	Registry reg = LocateRegistry.getRegistry();
    	
		ClientInterface callback = (ClientInterface)reg.lookup("CallbackClient");
    	
        return Register.registerUserDelegation(username, password, callback);
    }

	 
	 // Metodo per autenticare un utente
	    public String loginUser(String username, String password) throws RemoteException, NotBoundException {
	    	Registry reg = LocateRegistry.getRegistry();
	    	
			ClientInterface callback = (ClientInterface)reg.lookup("CallbackClient");
	        return Login.loginUserDelegation(username, password, callback);
	    }
	    
	    
	    public static void main(String[] args) throws RemoteException {
	    	CentroMonitoraggioServer server = new CentroMonitoraggioServer();
	    	Registry reg = LocateRegistry.createRegistry(1099);
	    	reg.rebind("MonitoringCenter", server);
	    }
	    
	    // Metodo per associare un utente a un centro di monitoraggio
	    public String associaCentro(String username, String nomeCentro)throws RemoteException {
	    	return AssociaCentro.associaCentroDelegation(username, nomeCentro);
	    }   
	    
	    public void cercaAreaGeografica(boolean cercaPerNome, String denominazione, String coordinate) throws RemoteException {
	        CercaArea.cercaAreaGeograficaDelegation(cercaPerNome, denominazione, coordinate);
	    }
	    
	    
	 // Funzione per registrare un centro di monitoraggio
	    public String registraCentro(String nomeCentro, String indirizzo,List<String> areeDiInteresse)throws RemoteException {
	        return RegistraCentro.registraCentroDelegation(nomeCentro, indirizzo,areeDiInteresse);
	    }
	    
	    
	    
	 // Metodo per inserire i parametri climatici per un'area di interesse
	    public String inserisciParametriClimatici(String username, String area, int vento, int umidita, int pressione, int temperatura, int precipitazioni, int altitudineGhiacciai, int massaGhiacciai, String note) throws RemoteException {
	       return InserisciParametri.inserisciParametriClimaticiDelegation(username,  area,  vento,  umidita,  pressione,  temperatura,  precipitazioni,  altitudineGhiacciai,  massaGhiacciai,note);
	    }
	    
	    public String visualizzaAreaGeografica(String area)throws RemoteException {
	    	return VisualizzaArea.visualizzaAreaDelegation(area);
	    }


	    
	    
	    
}
