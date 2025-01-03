package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RegistraCentro {
    // Funzione per registrare un centro di monitoraggio
    public static String registraCentroDelegation(String nomeCentro, String indirizzo, List<String> areeDiInteresse) throws RemoteException {
        CentroMonitoraggioServer get = new CentroMonitoraggioServer();
        String url = get.getdbHost(); // "jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = get.getdbUser(); // "postgres";
        String dbPassword = get.getdbPassword(); // "!sqlpassword";

        String insertCentroQuery = "INSERT INTO CentriMonitoraggio (nome, indirizzo) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword)) {
            // Disabilita il commit automatico per gestire manualmente la transazione
            conn.setAutoCommit(false);

            try (PreparedStatement insertCentroStmt = conn.prepareStatement(insertCentroQuery)) {
                // Inserimento del nuovo centro di monitoraggio
                insertCentroStmt.setString(1, nomeCentro);
                insertCentroStmt.setString(2, indirizzo);
                insertCentroStmt.executeUpdate();

                // Aggiungi aree di interesse, ma solo se tutte le aree sono valide
                String areeErrorMessage = aggiungiAree(conn, nomeCentro, areeDiInteresse);
                if (areeErrorMessage != null) {
                    // Se c'è un errore nelle aree, annulla l'inserimento del centro
                    conn.rollback();
                    return areeErrorMessage;
                }

                // Se tutto è andato bene, conferma la transazione
                conn.commit();
                return "Centro di monitoraggio registrato con successo!";
            } catch (SQLException e) {
                conn.rollback(); // Se c'è un errore, annulla tutte le operazioni
                e.printStackTrace();
                return "Errore durante l'inserimento del centro di monitoraggio: " + e.getMessage();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Errore di SQLException: " + e.getMessage();
        }
    }

    // Funzione per aggiungere aree in base al numero di aree di interesse
    public static String aggiungiAree(Connection conn, String nomeCentro, List<String> areeDiInteresse) throws RemoteException {
        String verificaAreaQuery = "SELECT COUNT(*) FROM CoordinateMonitoraggio WHERE name = ?";
        String aggiungiColonnaQueryTemplate = "ALTER TABLE CentriMonitoraggio ADD COLUMN area%s VARCHAR(255)";
        String updateQueryTemplate = "UPDATE CentriMonitoraggio SET area%s = ? WHERE nome = ?";

        try {
            // Controlla l'esistenza delle aree in CoordinateMonitoraggio
            List<String> areeValide = new ArrayList<>();
            List<String> areeNonValide = new ArrayList<>();

            try (PreparedStatement verificaAreaStmt = conn.prepareStatement(verificaAreaQuery)) {
                for (String area : areeDiInteresse) {
                    verificaAreaStmt.setString(1, area);
                    ResultSet rs = verificaAreaStmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        areeValide.add(area);
                    } else {
                        areeNonValide.add(area); // Se l'area non esiste, aggiungila alla lista delle non valide
                    }
                }
            }

            // Se ci sono aree non valide, restituisci un errore senza aggiungere il centro
            if (!areeNonValide.isEmpty()) {
                String errorMessage = "Le seguenti aree non esistono nel database: " + String.join(", ", areeNonValide);
                return errorMessage; // Restituisce l'errore come stringa
            }

            try (Statement stmt = conn.createStatement()) {
                // Controlla quante colonne "Area" esistono già
                String countAreeQuery = "SELECT COUNT(*) FROM information_schema.columns " +
                        "WHERE table_name = 'centrimonitoraggio' AND column_name LIKE 'area%'";
                ResultSet rs = stmt.executeQuery(countAreeQuery);
                rs.next();
                int numColonneAree = rs.getInt(1);

                // Aggiungi colonne se necessario
                for (int i = numColonneAree + 1; i <= areeValide.size(); i++) {
                    String aggiungiColonnaQuery = String.format(aggiungiColonnaQueryTemplate, i);
                    stmt.executeUpdate(aggiungiColonnaQuery);
                }

                // Aggiorna la riga corrispondente con le nuove aree
                for (int i = 0; i < areeValide.size(); i++) {
                    String updateQuery = String.format(updateQueryTemplate, i + 1);
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, areeValide.get(i));
                        updateStmt.setString(2, nomeCentro);
                        updateStmt.executeUpdate();
                    }
                }

                return null; // Nessun errore, tutto è andato bene

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Errore di SQLException durante l'aggiunta delle aree: " + e.getMessage();
        }
    }
}
