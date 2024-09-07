package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CercaArea {
	public static void cercaAreaGeograficaDelegation(boolean cercaPerNome, String denominazione, String coordinate) throws RemoteException {
		CentroMonitoraggioServer credenziali = new CentroMonitoraggioServer();
    	String url = credenziali.getdbHost();  //"jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = credenziali.getdbUser();  //"postgres";
        String dbPassword =credenziali.getdbPassword();   //"!sqlpassword";

        // Query per cercare per denominazione
        String searchByName = "SELECT * FROM CoordinateMonitoraggio WHERE name LIKE ?";
        // Query per cercare per coordinate
        String searchByCoordinates = "SELECT * FROM CoordinateMonitoraggio WHERE coordinates = ?";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword)) {
            
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
}
