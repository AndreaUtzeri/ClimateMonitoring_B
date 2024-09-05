package climatemonitoring;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CentroMonitoraggioServer extends UnicastRemoteObject implements InterfacciaServer {

	private static final long serialVersionUID = 1L;
	
	public CentroMonitoraggioServer() throws RemoteException{}
	
	 // Metodo per registrare un nuovo utente
    public void registerUser(String username, String password) throws RemoteException {
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
                //return false; // L'utente esiste già
            }

            // Inserimento del nuovo utente
            insertUserStmt.setString(1, username);
            insertUserStmt.setString(2, password); // Attenzione: in un progetto reale dovresti hashare la password!
            insertUserStmt.executeUpdate();

            System.out.println("Registrazione avvenuta con successo!");
            //return true;

        } catch (SQLException e) {
            e.printStackTrace();
            //return false;
        }
    }

	 
	 // Metodo per autenticare un utente
	    public String loginUser(String username, String password) throws RemoteException {
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
	                return username;
	            } else {
	                System.out.println("Credenziali non valide!");
	                return username;
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    
	    public static void main(String[] args) throws RemoteException {
	    	CentroMonitoraggioServer server = new CentroMonitoraggioServer();
	    	Registry reg = LocateRegistry.createRegistry(1099);
	    	reg.rebind("MonitoringCenter", server);
	    }
	    
	    // Metodo per associare un utente a un centro di monitoraggio
	    public void associaCentro(String username, String nomeCentro)throws RemoteException {
	        String url = "jdbc:postgresql://localhost:5432/ClimateMonitor";
	        String user = "postgres";
	        String dbPassword = "!sqlpassword";
	        
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
	                    System.out.println("Operatore associato al centro con successo!");
	                    
	                } else {
	                    System.out.println("Errore: username o password errati.");
	                    
	                }
	            } else {
	                System.out.println("Centro di monitoraggio non trovato.");
	                
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            
	        }
	    }
	    
	    public void cercaAreaGeografica(boolean cercaPerNome, String denominazione, String coordinate) throws RemoteException {
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
	    
	    
	 // Funzione per registrare un centro di monitoraggio
	    public void registraCentro(String nomeCentro, String indirizzo)throws RemoteException {
	        String url = "jdbc:postgresql://localhost:5432/ClimateMonitor";
	        String user = "postgres";
	        String password = "!sqlpassword";

	        String insertCentroQuery = "INSERT INTO CentriMonitoraggio (nome, indirizzo) VALUES (?, ?)";

	        try (Connection conn = DriverManager.getConnection(url, user, password);
	             PreparedStatement insertCentroStmt = conn.prepareStatement(insertCentroQuery)) {

	            // Inserimento del nuovo centro di monitoraggio
	            insertCentroStmt.setString(1, nomeCentro);
	            insertCentroStmt.setString(2, indirizzo);
	            insertCentroStmt.executeUpdate();

	            System.out.println("Centro di monitoraggio registrato con successo!");

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    // Funzione per aggiungere colonne in base al numero di aree di interesse
	    public void aggiungiAree(String nomeCentro, List<String> areeDiInteresse)throws RemoteException {
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
	    
	 // Metodo per inserire i parametri climatici per un'area di interesse
	    public void inserisciParametriClimatici(String username) throws RemoteException {
	        String url = "jdbc:postgresql://localhost:5432/ClimateMonitor";
	        String user = "postgres";
	        String dbPassword = "!sqlpassword";

	        Scanner scanner = new Scanner(System.in);

	        try (Connection conn = DriverManager.getConnection(url, user, dbPassword)) {
	            // Verifica che l'utente sia associato a un centro di monitoraggio
	            String queryCentro = "SELECT centro FROM OperatoriRegistrati WHERE username = ?";
	            try (PreparedStatement stmtCentro = conn.prepareStatement(queryCentro)) {
	                stmtCentro.setString(1, username);
	                ResultSet rsCentro = stmtCentro.executeQuery();

	                if (!rsCentro.next() || rsCentro.getString("centro") == null) {
	                    System.out.println("Errore: non sei associato a nessun centro di monitoraggio.");
	                    return;
	                }

	                String nomeCentro = rsCentro.getString("centro");

	                // Chiedi all'utente di inserire l'area di interesse
	                System.out.print("Inserisci l'area di interesse: ");
	                String area = scanner.nextLine();

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
	                    System.out.println("Errore: non ci sono colonne area disponibili nella tabella CentriMonitoraggio.");
	                    return;
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
	                        System.out.println("Errore: quest'area non è associata al centro di monitoraggio " + nomeCentro);
	                        return;
	                    }
	                }

	                // Chiedi all'utente di inserire i parametri climatici
	                System.out.print("Inserisci il valore per vento (1-5): ");
	                int vento = getValidatedInput(scanner);

	                System.out.print("Inserisci il valore per umidità (1-5): ");
	                int umidità = getValidatedInput(scanner);

	                System.out.print("Inserisci il valore per pressione (1-5): ");
	                int pressione = getValidatedInput(scanner);

	                System.out.print("Inserisci il valore per temperatura (1-5): ");
	                int temperatura = getValidatedInput(scanner);

	                System.out.print("Inserisci il valore per precipitazione (1-5): ");
	                int precipitazioni = getValidatedInput(scanner);

	                System.out.print("Inserisci il valore per altitudine dei ghiacciai (1-5): ");
	                int altitudineGhiacciai = getValidatedInput(scanner);

	                System.out.print("Inserisci il valore per massa dei ghiacciai (1-5): ");
	                int massaGhiacciai = getValidatedInput(scanner);

	                System.out.print("Inserisci eventuali note: ");
	                String note = scanner.nextLine();

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
	                    stmtInserisci.setInt(4, umidità);
	                    stmtInserisci.setInt(5, pressione);
	                    stmtInserisci.setInt(6, temperatura);
	                    stmtInserisci.setInt(7, precipitazioni);
	                    stmtInserisci.setInt(8, altitudineGhiacciai);
	                    stmtInserisci.setInt(9, massaGhiacciai);
	                    stmtInserisci.setString(10, note);
	                    stmtInserisci.setString(11, data); // Aggiungi la data corrente

	                    stmtInserisci.executeUpdate();
	                    System.out.println("Parametri climatici inseriti con successo!");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }


	    
	    // Metodo per validare l'input e assicurarsi che sia un numero intero tra 1 e 5
	    private static int getValidatedInput(Scanner scanner) {
	        int valore;
	        while (true) {
	            try {
	                valore = Integer.parseInt(scanner.nextLine());
	                if (valore >= 1 && valore <= 5) {
	                    break;
	                } else {
	                    System.out.print("Errore: inserisci un numero tra 1 e 5: ");
	                }
	            } catch (NumberFormatException e) {
	                System.out.print("Errore: inserisci un numero valido: ");
	            }
	        }
	        return valore;
	    }
	    
	 // Funzione per raccogliere le aree di interesse dall'utente
	    public List<String> raccogliAree() throws RemoteException {
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
