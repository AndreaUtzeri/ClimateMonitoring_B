package climatemonitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssociaCentroMonitoraggio {

    // Metodo per associare un utente a un centro di monitoraggio
    public static boolean associaCentro(String username, String password, String nomeCentro) {
        String url = "jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = "postgres";
        String dbPassword = "!sqlpassword";
        
        // Query per verificare se il centro esiste
        String queryCentro = "SELECT nome FROM CentriMonitoraggio WHERE nome = ?";
        
        // Query per aggiornare il centro dell'operatore
        String queryAssocia = "UPDATE OperatoriRegistrati SET centro = ? WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement stmtCentro = conn.prepareStatement(queryCentro);
             PreparedStatement stmtAssocia = conn.prepareStatement(queryAssocia)) {
            
            // Verifica se il centro di monitoraggio esiste
            stmtCentro.setString(1, nomeCentro);
            ResultSet rsCentro = stmtCentro.executeQuery();
            
            if (rsCentro.next()) {
                // Il centro esiste, ora aggiorna l'operatore
                stmtAssocia.setString(1, nomeCentro);  // Inserisci il nome del centro
                stmtAssocia.setString(2, username);
                stmtAssocia.setString(3, password);
                
                int rowsAffected = stmtAssocia.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Operatore associato al centro con successo!");
                    return true;
                } else {
                    System.out.println("Errore: username o password errati.");
                    return false;
                }
            } else {
                System.out.println("Centro di monitoraggio non trovato.");
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        associaCentro("andrea", "utzeri", "CentroB");
    }
}
