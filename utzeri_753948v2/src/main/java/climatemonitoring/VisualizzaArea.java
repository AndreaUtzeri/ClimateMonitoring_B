package climatemonitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisualizzaArea {
	
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/ClimateMonitor";
    private static final String USER = "postgres";
    private static final String PASSWORD = "!sqlpassword";


    public static String visualizzaAreaDelegation(String area) {
        // Query per cercare i dati in base all'area
        String query = "SELECT * FROM parametriclimatici WHERE area = ?";
        String risultato="";
        // Connessione al database
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Imposta il valore dell'area nel PreparedStatement
            statement.setString(1, area);
            
            // Esegui la query
            ResultSet resultSet = statement.executeQuery();
            
            

            // Itera sui risultati
            
            while (resultSet.next()) {
                // Stampa i dati corrispondenti alla riga trovata
                risultato+="\n"+"Centro: " + resultSet.getString("centro");
                risultato+="\n"+"Area: " + resultSet.getString("area");
                risultato+="\n"+"Vento: " + resultSet.getString("vento");
                risultato+="\n"+"Umidità: " + resultSet.getString("umidità");
                risultato+="\n"+"Pressione: " + resultSet.getString("pressione");
                risultato+="\n"+"Temperatura: " + resultSet.getString("temperatura");
                risultato+="\n"+"Precipitazioni: " + resultSet.getString("precipitazioni");
                risultato+="\n"+"Altitudine Ghiacciai: " + resultSet.getString("altitudine_ghiacciai");
                risultato+="\n"+"Massa Ghiacciai: " + resultSet.getString("massa_ghiacciai");
                risultato+="\n"+"Note: " + resultSet.getString("note");
                risultato+="\n"+"Data: " + resultSet.getString("data");
                risultato+="\n"+"----------------------------";
            }
            
            

        } catch (SQLException e) {
            e.printStackTrace();
            return "Errore";
        }
        return risultato;
    }
}