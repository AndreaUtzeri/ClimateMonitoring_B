package climatemonitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AreaGeografica {

    // Funzione per cercare un'area geografica per denominazione o coordinate
    public static void cercaAreaGeografica(boolean cercaPerNome, String denominazione, String coordinate) {
        String url = "jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = "postgres";
        String password = "!sqlpassword";

        // Query per cercare per denominazione
        String searchByName = "SELECT * FROM CoordinateMonitoraggio WHERE name LIKE ?";
        // Query per cercare per coordinate
        String searchByCoordinates = "SELECT * FROM CoordinateMonitoraggio WHERE coordinates = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            
            if (cercaPerNome) {
                // Ricerca per denominazione
                try (PreparedStatement stmtByName = conn.prepareStatement(searchByName)) {
                    stmtByName.setString(1, "%" + denominazione + "%");
                    ResultSet rs = stmtByName.executeQuery();
                    
                    System.out.println("Risultati per nome:");
                    while (rs.next()) {
                        System.out.println("Nome: " + rs.getString("name") +
                                ", Paese: " + rs.getString("country_name") +
                                ", Coordinate: " + rs.getString("coordinates"));
                    }
                }
            } else {
                // Ricerca per coordinate
                try (PreparedStatement stmtByCoordinates = conn.prepareStatement(searchByCoordinates)) {
                    stmtByCoordinates.setString(1, coordinate);
                    ResultSet rs = stmtByCoordinates.executeQuery();
                    
                    System.out.println("\nRisultati per coordinate:");
                    while (rs.next()) {
                        System.out.println("Nome: " + rs.getString("name") +
                                ", Paese: " + rs.getString("country_name") +
                                ", Coordinate: " + rs.getString("coordinates"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Esempio di ricerca per nome
        //cercaAreaGeografica(true, "Milano", null);
        
        // Esempio di ricerca per coordinate
        cercaAreaGeografica(false, null, "41.74177, -70.65781");
    }
}
