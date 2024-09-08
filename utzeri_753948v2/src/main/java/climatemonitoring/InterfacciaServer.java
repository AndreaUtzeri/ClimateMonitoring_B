package climatemonitoring;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface InterfacciaServer extends Remote {

	 public String registerUser(String username, String password) throws RemoteException, NotBoundException ;
	 public String loginUser(String username, String password) throws RemoteException, NotBoundException;
	 public void cercaAreaGeografica(boolean cercaPerNome, String denominazione, String coordinate) throws RemoteException;
	 public void registraCentro(String nomeCentro, String indirizzo) throws RemoteException;
	 public void associaCentro(String username, String nomeCentro) throws RemoteException;
	 public void inserisciParametriClimatici(String username)throws RemoteException;
	 public String visualizzaAreaGeografica(String area)throws RemoteException;
	 public boolean getDbmsCredential(String dbHost, String dbUser, String dbPassword) throws RemoteException;
	 
}