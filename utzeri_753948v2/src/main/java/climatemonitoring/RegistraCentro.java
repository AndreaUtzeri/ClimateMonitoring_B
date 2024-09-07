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
import java.util.Scanner;

public class RegistraCentro {
	// Funzione per registrare un centro di monitoraggio
    public static void registraCentroDelegation(String nomeCentro, String indirizzo)throws RemoteException {
    	CentroMonitoraggioServer credenziali = new CentroMonitoraggioServer();
    	String url = credenziali.getdbHost();  //"jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = credenziali.getdbUser();  //"postgres";
        String dbPassword =credenziali.getdbPassword();   //"!sqlpassword";

        String insertCentroQuery = "INSERT INTO CentriMonitoraggio (nome, indirizzo) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement insertCentroStmt = conn.prepareStatement(insertCentroQuery)) {

            // Inserimento del nuovo centro di monitoraggio
            insertCentroStmt.setString(1, nomeCentro);
            insertCentroStmt.setString(2, indirizzo);
            insertCentroStmt.executeUpdate();

            System.out.println("Centro di monitoraggio registrato con successo!");
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
     // Raccogli e aggiungi aree di interesse
        List<String> areeDiInteresse = raccogliAree();
        aggiungiAree(nomeCentro, areeDiInteresse);
    }
 
    // Funzione per aggiungere colonne in base al numero di aree di interesse
    public static void aggiungiAree(String nomeCentro, List<String> areeDiInteresse)throws RemoteException {
        String url = "jdbc:postgresql://localhost:5432/ClimateMonitor";
        String user = "postgres";
        String password = "!sqlpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // Controlla quante colonne "Area" esistono già
            String countAreeQuery = "SELECT COUNT(*) FROM information_schema.columns " +
                    "WHERE table_name = 'centrimonitoraggio' AND column_name LIKE 'area%'";
            ResultSet rs = stmt.executeQuery(countAreeQuery);
            rs.next();
            int numColonneAree = rs.getInt(1);

            // Aggiungi colonne se necessario
            for (int i = numColonneAree + 1; i <= areeDiInteresse.size(); i++) {
                String aggiungiColonnaQuery = "ALTER TABLE CentriMonitoraggio ADD COLUMN area" + i + " VARCHAR(255)";
                stmt.executeUpdate(aggiungiColonnaQuery);
            }

            // Aggiorna la riga corrispondente con le nuove aree
            StringBuilder updateQuery = new StringBuilder("UPDATE CentriMonitoraggio SET ");
            for (int i = 0; i < areeDiInteresse.size(); i++) {
                updateQuery.append("area").append(i + 1).append(" = '").append(areeDiInteresse.get(i)).append("'");
                if (i < areeDiInteresse.size() - 1) {
                    updateQuery.append(", ");
                }
            }
            updateQuery.append(" WHERE nome = '").append(nomeCentro).append("'");
            stmt.executeUpdate(updateQuery.toString());

            System.out.println("Aree di interesse aggiornate con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // Funzione per raccogliere le aree di interesse dall'utente
    public static List<String> raccogliAree() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        List<String> areeDiInteresse = new ArrayList<>();
        boolean continua = true;

        while (continua) {
            System.out.print("Inserisci un'area di interesse: ");
            String area = scanner.nextLine();
            areeDiInteresse.add(area);

            System.out.print("Vuoi inserire un'altra area? (sì/no): ");
            String risposta = scanner.nextLine();
            if (!risposta.equalsIgnoreCase("sì")) {
                continua = false;
            }
        }

        return areeDiInteresse;
    }
}
