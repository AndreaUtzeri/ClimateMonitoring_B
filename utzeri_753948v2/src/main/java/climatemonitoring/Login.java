package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
	// Metodo per autenticare un utente
	
    public static String loginUserDelegation(String username, String password, ClientInterface callback) throws RemoteException {
    	String url = CentroMonitoraggioServer.getdbHost();//"jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = CentroMonitoraggioServer.getdbUser();//"postgres";
        String dbPassword = CentroMonitoraggioServer.getdbPassword();//"!sqlpassword";
        
        // Query per verificare le credenziali dell'utente
        String loginQuery = "SELECT * FROM OperatoriRegistrati WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement loginStmt = conn.prepareStatement(loginQuery)) {

            // Imposta i parametri della query
            loginStmt.setString(1, username);
            loginStmt.setString(2, password); 

            ResultSet rs = loginStmt.executeQuery();

            // Se esiste il dato, il login è valido
            if (rs.next()) {
                callback.loginSuccess();
                return username;
            } else {
                callback.loginFailure();
                return "";
            }

        } catch (SQLException e) {
        	e.printStackTrace();
            if (e.getSQLState().equals("23505")) { // Codice SQL per violazione di chiave unica in PostgreSQL
                return "Errore: username già registrato nel sistema.";
            }else {
                e.printStackTrace();
                return "Errore di SQLException: " + e.getMessage();
            }
        }
    }
}
