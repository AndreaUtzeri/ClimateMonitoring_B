package climatemonitoring;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
	
	 public void registerSuccess() throws RemoteException;
	 public void loginSuccess() throws RemoteException;
	 public void registerFailure() throws RemoteException;
	 public void loginFailure() throws RemoteException;

}
