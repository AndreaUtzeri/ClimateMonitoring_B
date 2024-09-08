package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {
	
	
	
	// Metodo per registrare un nuovo utente
    public static String registerUserDelegation(String username, String password, ClientInterface callback) throws RemoteException {
        // Connessione al database
        String url = "jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = "postgres";
        String dbPassword = "!sqlpassword";

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
                callback.registerFailure();
                return "registrazione non avvenuta con successo";
               
            }

            // Inserimento del nuovo utente
            insertUserStmt.setString(1, username);
            insertUserStmt.setString(2, password); 
            insertUserStmt.executeUpdate();

            callback.registerSuccess();
            return "registrazione avvenuta con successo";
            

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore");
            return "errore in registrazione";
        }
    }
}