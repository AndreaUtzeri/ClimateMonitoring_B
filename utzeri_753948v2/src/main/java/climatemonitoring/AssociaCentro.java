package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssociaCentro {
	// Metodo per associare un utente a un centro di monitoraggio
    public static String associaCentroDelegation(String username, String nomeCentro)throws RemoteException {
        CentroMonitoraggioServer get = new CentroMonitoraggioServer();
    	String url = get.getdbHost();//"jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = get.getdbUser();//"postgres";
        String dbPassword = get.getdbPassword();//"!sqlpassword";
        
        // Query per verificare se il centro esiste
        String queryCentro = "SELECT nome FROM CentriMonitoraggio WHERE nome = ?";
        
        // Query per aggiornare il centro dell'operatore
        String queryAssocia = "UPDATE OperatoriRegistrati SET centro = ? WHERE username = ? ";

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
                
                
                int rowsAffected = stmtAssocia.executeUpdate();
                
                if (rowsAffected > 0) {
                    return "Operatore associato al centro con successo!";
                    
                } else {
                    return "Errore: username o password errati.";
                    
                }
            } else {
                return "Centro di monitoraggio non trovato.";
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "Errore di SQLException";
        }
    }
}
