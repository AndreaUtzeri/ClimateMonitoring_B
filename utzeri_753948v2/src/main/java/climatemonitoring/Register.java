package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {
	
	
	
	// Metodo per registrare un nuovo utente
    public static String registerUserDelegation(String username, String password) throws RemoteException {
        // Connessione al database
    	CentroMonitoraggioServer credenziali = new CentroMonitoraggioServer();
    	String url = credenziali.getdbHost();  //"jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = credenziali.getdbUser();  //"postgres";
        String dbPassword =credenziali.getdbPassword();   //"!sqlpassword";

        // Query per verificare se l'utente esiste già
        String checkUserQuery = "SELECT COUNT(*) FROM operatoriregistrati WHERE username = ?";
        
        // Query per inserire il nuovo utente
        String insertUserQuery = "INSERT INTO operatoriregistrati (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement checkUserStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement insertUserStmt = conn.prepareStatement(insertUserQuery)) {

            // Verifica se l'utente esiste già
            checkUserStmt.setString(1, username);
            ResultSet rs = checkUserStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                return "Username già esistente!";
               
            }

            // Inserimento del nuovo utente
            insertUserStmt.setString(1, username);
            insertUserStmt.setString(2, password); 
            insertUserStmt.executeUpdate();

            return "Registrazione avvenuta con successo!";
            

        } catch (SQLException e) {
            e.printStackTrace();
            return "errore";
        }
    }
}
