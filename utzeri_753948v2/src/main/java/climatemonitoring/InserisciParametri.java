package climatemonitoring;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InserisciParametri {

    // Metodo per inserire i parametri climatici per un'area di interesse
    public static String inserisciParametriClimaticiDelegation(String username, String area, int vento, int umidita, int pressione, int temperatura, int precipitazioni, int altitudineGhiacciai, int massaGhiacciai, String note) throws RemoteException {
        String url = "jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = "postgres";
        String dbPassword = "!sqlpassword";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword)) {
            // Verifica che l'utente sia associato a un centro di monitoraggio
            String queryCentro = "SELECT centro FROM OperatoriRegistrati WHERE username = ?";
            try (PreparedStatement stmtCentro = conn.prepareStatement(queryCentro)) {
                stmtCentro.setString(1, username);
                ResultSet rsCentro = stmtCentro.executeQuery();

                if (!rsCentro.next() || rsCentro.getString("centro") == null) {
                    return "Errore: non sei associato a nessun centro di monitoraggio.";
                }

                String nomeCentro = rsCentro.getString("centro");

                // Controlla quante colonne "Area" esistono già
                String countAreeQuery = "SELECT COUNT(*) FROM information_schema.columns " +
                        "WHERE table_name = 'centrimonitoraggio' AND column_name LIKE 'area%'";
                
                int numColonneAree = 0;
                try (PreparedStatement stmtCount = conn.prepareStatement(countAreeQuery);
                     ResultSet rsCount = stmtCount.executeQuery()) {
                    if (rsCount.next()) {
                        numColonneAree = rsCount.getInt(1);
                    }
                }

                if (numColonneAree <= 0) {
                    return "Errore: non ci sono colonne area disponibili nella tabella CentriMonitoraggio.";
                }

                // Costruisci dinamicamente la query per verificare l'area
                StringBuilder queryAreaBuilder = new StringBuilder("SELECT 1 FROM CentriMonitoraggio WHERE nome = ? AND (");

                for (int i = 1; i <= numColonneAree; i++) {
                    queryAreaBuilder.append("area").append(i).append(" = ?");
                    if (i < numColonneAree) {
                        queryAreaBuilder.append(" OR ");
                    }
                }
                queryAreaBuilder.append(")");

                try (PreparedStatement stmtArea = conn.prepareStatement(queryAreaBuilder.toString())) {
                    stmtArea.setString(1, nomeCentro);
                    for (int i = 2; i <= numColonneAree + 1; i++) {
                        stmtArea.setString(i, area);
                    }
                    ResultSet rsArea = stmtArea.executeQuery();

                    if (!rsArea.next()) {
                        return "Errore: quest'area non è associata al centro di monitoraggio " + nomeCentro;
                    }
                }

                // Ottieni la data e ora corrente
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String data = now.format(formatter);

                // Inserisci i parametri climatici nella tabella
                String queryInserisci = "INSERT INTO ParametriClimatici (centro, area, vento, umidità, pressione, temperatura, precipitazioni, altitudine_ghiacciai, massa_ghiacciai, note, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmtInserisci = conn.prepareStatement(queryInserisci)) {
                    stmtInserisci.setString(1, nomeCentro);
                    stmtInserisci.setString(2, area);
                    stmtInserisci.setInt(3, vento);
                    stmtInserisci.setInt(4, umidita);
                    stmtInserisci.setInt(5, pressione);
                    stmtInserisci.setInt(6, temperatura);
                    stmtInserisci.setInt(7, precipitazioni);
                    stmtInserisci.setInt(8, altitudineGhiacciai);
                    stmtInserisci.setInt(9, massaGhiacciai);
                    stmtInserisci.setString(10, note);
                    stmtInserisci.setString(11, data); // Aggiungi la data corrente

                    stmtInserisci.executeUpdate();
                }
                return "Parametri aggiunti con successo";
            }
        } catch (SQLException e) {
            return "Errore durante l'inserimento dei parametri climatici.";
        }
    }
}
