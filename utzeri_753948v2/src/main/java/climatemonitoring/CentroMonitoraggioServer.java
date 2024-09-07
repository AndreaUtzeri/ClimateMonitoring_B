package climatemonitoring;

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
	
	public CentroMonitoraggioServer() throws RemoteException{}
	
	public void getDbmsCredential(String dbHost, String dbUser, String dbPassword) throws RemoteException {
        
    }
	
	
	
	
	// Metodo per registrare un nuovo utente
    public String registerUser(String username, String password) throws RemoteException {
        return Register.registerUserDelegation(username, password);
    }

	 
	 // Metodo per autenticare un utente
	    public String loginUser(String username, String password) throws RemoteException {
	        return Login.loginUserDelegation(username, password);
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
	    public void registraCentro(String nomeCentro, String indirizzo)throws RemoteException {
	        RegistraCentro.registraCentroDelegation(nomeCentro, indirizzo);
	    }
	    
	    
	    
	 // Metodo per inserire i parametri climatici per un'area di interesse
	    public String inserisciParametriClimatici(String username) throws RemoteException {
	        return InserisciParametri.inserisciParametriClimaticiDelegation(username);
	    }
	    
	    public String visualizzaAreaGeografica(String area)throws RemoteException {
	    	return VisualizzaArea.visualizzaAreaDelegation(area);
	    }


	    
	    
	    
}
