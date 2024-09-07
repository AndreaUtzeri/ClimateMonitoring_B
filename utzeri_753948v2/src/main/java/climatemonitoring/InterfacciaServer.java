package climatemonitoring;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface InterfacciaServer extends Remote {

	 public boolean registerUser(String username, String password) throws RemoteException;
	 public boolean loginUser(String username, String password) throws RemoteException;
	 public void cercaAreaGeografica(boolean cercaPerNome, String denominazione, String coordinate) throws RemoteException;
	 public void registraCentro(String nomeCentro, String indirizzo) throws RemoteException;
	 public void associaCentro(String username, String nomeCentro) throws RemoteException;
	 public void inserisciParametriClimatici(String username)throws RemoteException;
	 public void visualizzaAreaGeografica(String area)throws RemoteException;
	 public void getDbmsCredential(String dbHost, String dbUser, String dbPassword) throws RemoteException;
	 
}