package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CercaArea {

    public static String cercaAreaGeograficaDelegation(int tipoRicerca, String valoreRicerca) throws RemoteException {
        CentroMonitoraggioServer get = new CentroMonitoraggioServer();
        String url = get.getdbHost();
        String user = get.getdbUser();
        String dbPassword = get.getdbPassword();

        double range = 100000.0; // Distanza di ricerca in metri

        String searchByName = "SELECT name, country_name, coordinates FROM CoordinateMonitoraggio WHERE LOWER(name) = LOWER(?)";
        String searchByState = "SELECT name, country_name, coordinates FROM CoordinateMonitoraggio WHERE LOWER(country_code) = LOWER(?)";
        String searchByCoordinates =
            "SELECT name, country_name, coordinates, " +
            "ST_Distance(geog, ST_SetSRID(ST_MakePoint(?, ?), 4326)) AS distance " +
            "FROM CoordinateMonitoraggio " +
            "WHERE ST_Distance(geog, ST_SetSRID(ST_MakePoint(?, ?), 4326)) <= ?";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword)) {
            String query;
            PreparedStatement stmt;

            if (tipoRicerca == 0) { // Ricerca per nome
                query = searchByName;
                stmt = conn.prepareStatement(query);
                stmt.setString(1, valoreRicerca);
                System.out.println("Query per nome: " + query);
                System.out.println("Valore di ricerca (nome): " + valoreRicerca);

            } else if (tipoRicerca == 1) { // Ricerca per stato
                query = searchByState;
                stmt = conn.prepareStatement(query);
                stmt.setString(1, valoreRicerca);
                System.out.println("Query per stato: " + query);
                System.out.println("Valore di ricerca (stato): " + valoreRicerca);

            } else if (tipoRicerca == 2) { // Ricerca per coordinate
                query = searchByCoordinates;
                stmt = conn.prepareStatement(query);
                String[] coords = valoreRicerca.split(",");
                if (coords.length != 2) {
                    return "Errore: il formato delle coordinate non è corretto. Usa latitudine,longitudine.";
                }
                double userLat = Double.parseDouble(coords[0].trim());
                double userLon = Double.parseDouble(coords[1].trim());

                if (userLat < -90 || userLat > 90 || userLon < -180 || userLon > 180) {
                    return "Errore: coordinate fuori dai limiti consentiti.";
                }

                System.out.println("Query per coordinate: " + query);
                System.out.println("Valori passati:");
                System.out.println("Latitudine: " + userLat);
                System.out.println("Longitudine: " + userLon);
                System.out.println("Raggio di ricerca: " + range);

                // Impostare i parametri nella query
                stmt.setDouble(1, userLon); // Longitudine dell'utente
                stmt.setDouble(2, userLat); // Latitudine dell'utente
                stmt.setDouble(3, userLon); // Longitudine dell'utente
                stmt.setDouble(4, userLat); // Latitudine dell'utente
                stmt.setDouble(5, range);   // Raggio di ricerca in metri

            } else {
                return "Errore: tipo di ricerca non valido.";
            }

            ResultSet rs = stmt.executeQuery();
            StringBuilder result = new StringBuilder();
            boolean trovato = false;

            while (rs.next()) {
                trovato = true;
                String nome = rs.getString("name");
                String paese = rs.getString("country_name");
                String coordinate = rs.getString("coordinates");

                result.append("Nome: ").append(nome)
                      .append(", Paese: ").append(paese)
                      .append(", Coordinate: ").append(coordinate);

                if (tipoRicerca == 2) { // Solo per ricerca per coordinate
                    double distanza = rs.getDouble("distance");
                    result.append(", Distanza: ").append(distanza / 1000).append(" km"); // Converti da metri a km
                }

                result.append("\n");
            }

            if (trovato) {
                return result.toString();
            } else {
                return "Nessun risultato trovato per il valore specificato.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Errore durante la ricerca.";
        }
    }
}
