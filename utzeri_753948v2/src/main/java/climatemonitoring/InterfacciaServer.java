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
	 public String registraCentro(String nomeCentro, String indirizzo,List<String> areeDiInteresse) throws RemoteException;
	 public String associaCentro(String username, String nomeCentro) throws RemoteException;
	 public String inserisciParametriClimatici(String username, String area, int vento, int umidita, int pressione, int temperatura, int precipitazioni, int altitudineGhiacciai, int massaGhiacciai, String note)throws RemoteException;
	 public String visualizzaAreaGeografica(String area)throws RemoteException;
	 public boolean getDbmsCredential(String dbHost, String dbUser, String dbPassword) throws RemoteException;
	 
}