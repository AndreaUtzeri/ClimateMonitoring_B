package climatemonitoring;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface InterfacciaServer extends Remote {

	 public void registerUser(String username, String password) throws RemoteException;
	 public String loginUser(String username, String password) throws RemoteException;
	 public void cercaAreaGeografica(boolean cercaPerNome, String denominazione, String coordinate) throws RemoteException;
	 public void registraCentro(String nomeCentro, String indirizzo) throws RemoteException;
	 public void aggiungiAree(String nomeCentro, List<String> areeDiInteresse) throws RemoteException;
	 public List<String> raccogliAree() throws RemoteException;
	 public void associaCentro(String username, String nomeCentro) throws RemoteException;
	 public void inserisciParametriClimatici(String username)throws RemoteException;
	 
}
