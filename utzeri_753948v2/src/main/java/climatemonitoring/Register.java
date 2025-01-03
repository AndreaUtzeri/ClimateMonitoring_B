package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {

    // Metodo per registrare un nuovo utente
    public static String registerUserDelegation(String username, String password, String nome, String cognome, String codiceFiscale, String email, ClientInterface callback) throws RemoteException {
        CentroMonitoraggioServer get = new CentroMonitoraggioServer();
        String url = get.getdbHost(); // "jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = get.getdbUser(); // "postgres";
        String dbPassword = get.getdbPassword(); // "!sqlpassword";

        // Query per verificare se l'utente esiste già
        String checkUserQuery = "SELECT COUNT(*) FROM operatoriregistrati WHERE username = ? OR email = ?";
        
        // Query per inserire il nuovo utente
        String insertUserQuery = "INSERT INTO operatoriregistrati (username, password, nome, cognome, codice_fiscale, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement checkUserStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement insertUserStmt = conn.prepareStatement(insertUserQuery)) {

            // Verifica se l'utente o l'email esistono già
            checkUserStmt.setString(1, username);
            checkUserStmt.setString(2, email);
            ResultSet rs = checkUserStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                callback.registerFailure();
                return "Registrazione non avvenuta con successo: username o email già esistenti.";
            }

            // Inserimento del nuovo utente
            insertUserStmt.setString(1, username);
            insertUserStmt.setString(2, password);
            insertUserStmt.setString(3, nome);
            insertUserStmt.setString(4, cognome);
            insertUserStmt.setString(5, codiceFiscale);
            insertUserStmt.setString(6, email);
            insertUserStmt.executeUpdate();

            callback.registerSuccess();
            return "Registrazione avvenuta con successo.";

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante la registrazione.");
            return "Errore in registrazione: " + e.getMessage();
        }
    }
}
