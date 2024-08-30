package climatemonitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRegistration {

    // Metodo per registrare un nuovo utente
    public static boolean registerUser(String username, String password) {
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
                System.out.println("Username già esistente!");
                return false; // L'utente esiste già
            }

            // Inserimento del nuovo utente
            insertUserStmt.setString(1, username);
            insertUserStmt.setString(2, password); // Attenzione: in un progetto reale dovresti hashare la password!
            insertUserStmt.executeUpdate();

            System.out.println("Registrazione avvenuta con successo!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

