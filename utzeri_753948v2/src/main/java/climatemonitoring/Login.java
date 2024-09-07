package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
	// Metodo per autenticare un utente
    public static boolean loginUserDelegation(String username, String password) throws RemoteException {
        // Connessione al database
        String url = "jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = "postgres";
        String dbPassword = "!sqlpassword";

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
                System.out.println("Login avvenuto con successo!");
                return true;
            } else {
                System.out.println("Credenziali non valide!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
