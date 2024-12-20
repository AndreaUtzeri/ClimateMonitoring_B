package climatemonitoring;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class ClientGUI {

    private static JTextArea textArea;
    private static Operatore client;
    private static JTextField textFieldArea;
    private static JTextField textFieldVento;
    private static JTextField textFieldUmidita;
    private static JTextField textFieldPressione;
    private static JTextField textFieldTemperatura;
    private static JTextField textFieldPrecipitazioni;
    private static JTextField textFieldAltitudineGhiacciai;
    private static JTextField textFieldMassaGhiacciai;
    private static JTextArea textAreaNote;

    public static void main(String[] args) throws RemoteException, NotBoundException {

        JFrame frame = new JFrame("Climate Monitoring");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        client = new Operatore();
        Registry reg = LocateRegistry.getRegistry();
        reg.rebind("CallbackClient", client);

        // Bottone per inserire le credenziali DBMS
        JButton buttonzero = new JButton("Insert DBMS credentials");

        // Altri bottoni (inizialmente nascosti)
        JButton buttonone = new JButton("Research an Area");
        JButton buttontwo = new JButton("Register as an operator");
        JButton buttonthree = new JButton("Login as an operator");
        JButton buttonfour = new JButton("Logout");
        JButton buttonfive = new JButton("Register a Monitoring Center");
        JButton buttonsix = new JButton("Associate to a Monitoring Center");
        JButton buttonseven = new JButton("Insert new climate parameters");
        JButton buttoneight = new JButton("View climate parameters registered for an area");

        // Pannello per i bottoni
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Pannello per i messaggi di output
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        textArea = new JTextArea(10, 60);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        // Pannello per i parametri climatici (inizialmente nascosto)
        JPanel paramPanel = new JPanel();
        paramPanel.setLayout(new GridLayout(9, 2));

        // Rimuovo i parametri climatici dalla GUI iniziale
        textFieldArea = new JTextField(20);
        textFieldVento = new JTextField(20);
        textFieldUmidita = new JTextField(20);
        textFieldPressione = new JTextField(20);
        textFieldTemperatura = new JTextField(20);
        textFieldPrecipitazioni = new JTextField(20);
        textFieldAltitudineGhiacciai = new JTextField(20);
        textFieldMassaGhiacciai = new JTextField(20);
        textAreaNote = new JTextArea(5, 20);
        // Parametri climatici non aggiunti al pannello inizialmente

        // Layout principale del frame
        frame.setLayout(new BorderLayout());

        // Aggiungi inizialmente solo il bottone delle credenziali
        buttonPanel.add(buttonzero);

        // Azioni per il pulsante delle credenziali DBMS
        buttonzero.addActionListener(e -> {
            // Richiedi le credenziali tramite un'interfaccia grafica
            JTextField hostField = new JTextField();
            JTextField userField = new JTextField();
            JPasswordField passwordField = new JPasswordField();

            JPanel dbmsPanel = new JPanel(new GridLayout(0, 1));
            dbmsPanel.add(new JLabel("Host Database:"));
            dbmsPanel.add(hostField);
            dbmsPanel.add(new JLabel("Username:"));
            dbmsPanel.add(userField);
            dbmsPanel.add(new JLabel("Password:"));
            dbmsPanel.add(passwordField);

            int option = JOptionPane.showConfirmDialog(frame, dbmsPanel, "Enter DBMS Credentials", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String host = hostField.getText();
                String username = userField.getText();
                String password = new String(passwordField.getPassword());

                if (host != null && username != null && password != null) {
                    try {
                        // Passa le credenziali al metodo getDbmsCredential
                        client.getDbmsCredential(host, username, password);
                        // Mostra solo i bottoni di ricerca, registrazione, login e logout
                        buttonPanel.removeAll();
                        buttonPanel.add(buttonone);
                        buttonPanel.add(buttontwo);
                        buttonPanel.add(buttonthree);
                        buttonPanel.add(buttonfour);
                        buttonPanel.add(buttoneight);  // View Climate Parameters
                        frame.revalidate();  // Rendi visibili i bottoni aggiornati
                        frame.repaint();
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    textArea.append("Credenziali DBMS mancanti.\n");
                }
            } else {
                textArea.append("Inserimento delle credenziali DBMS annullato.\n");
            }
        });

        // Implementazioni degli altri bottoni
        buttonone.addActionListener(e -> {
            try {
                client.cercaAreaGeografica();
                textArea.append("\n");
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        buttontwo.addActionListener(e -> {
            JTextField usernameField = new JTextField();
            JTextField passwordField = new JPasswordField();
            Object[] message = {
                "Inserisci il tuo nome utente:", usernameField,
                "Inserisci la tua password:", passwordField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Registrazione", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (username != null && password != null) {
                    try {
                        String result = client.registerUser(username, password);
                        textArea.append(result + "\n");
                    } catch (RemoteException | NotBoundException e1) {
                        e1.printStackTrace();
                        textArea.append("Errore durante la registrazione.\n");
                    }
                } else {
                    textArea.append("Registrazione annullata.\n");
                }
            } else {
                textArea.append("Registrazione annullata.\n");
            }
        });

        buttonthree.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(null, "Inserisci il tuo nome utente:");
            String password = JOptionPane.showInputDialog(null, "Inserisci la tua password:");

            if (username != null && password != null) {
                try {
                    String result = client.loginUser(username, password);
                    textArea.append(result + "\n");

                    // Mostra i bottoni aggiuntivi dopo il login
                    buttonPanel.add(buttonseven); // Insert new climate parameters
                    buttonPanel.add(buttonfive);  // Register a Monitoring Center
                    buttonPanel.add(buttonsix);   // Associate to a Monitoring Center
                    frame.revalidate();    // Rendi visibili i bottoni aggiornati
                    frame.repaint();

                } catch (RemoteException | NotBoundException e1) {
                    e1.printStackTrace();
                }
            } else {
                textArea.append("Login annullato.\n");
            }
        });

        buttonfour.addActionListener(e -> {
            client.logout();
            textArea.append("Logged out\n");
        });

        // Azione per il bottone "Inserisci nuovi parametri climatici"
        buttonseven.addActionListener(e -> {
            // Mostra una finestra per inserire i parametri climatici
            JPanel paramDialogPanel = new JPanel(new GridLayout(9, 2));
            JTextField areaField = new JTextField(20);
            JTextField ventoField = new JTextField(20);
            JTextField umiditaField = new JTextField(20);
            JTextField pressioneField = new JTextField(20);
            JTextField temperaturaField = new JTextField(20);
            JTextField precipitazioniField = new JTextField(20);
            JTextField altitudineGhiacciaiField = new JTextField(20);
            JTextField massaGhiacciaiField = new JTextField(20);
            JTextArea noteArea = new JTextArea(5, 20);

            paramDialogPanel.add(new JLabel("Area:"));
            paramDialogPanel.add(areaField);
            paramDialogPanel.add(new JLabel("Vento:"));
            paramDialogPanel.add(ventoField);
            paramDialogPanel.add(new JLabel("Umidita:"));
            paramDialogPanel.add(umiditaField);
            paramDialogPanel.add(new JLabel("Pressione:"));
            paramDialogPanel.add(pressioneField);
            paramDialogPanel.add(new JLabel("Temperatura:"));
            paramDialogPanel.add(temperaturaField);
            paramDialogPanel.add(new JLabel("Precipitazioni:"));
            paramDialogPanel.add(precipitazioniField);
            paramDialogPanel.add(new JLabel("Altitudine Ghiacciai:"));
            paramDialogPanel.add(altitudineGhiacciaiField);
            paramDialogPanel.add(new JLabel("Massa Ghiacciai:"));
            paramDialogPanel.add(massaGhiacciaiField);
            paramDialogPanel.add(new JLabel("Note:"));
            paramDialogPanel.add(new JScrollPane(noteArea));

            int option = JOptionPane.showConfirmDialog(frame, paramDialogPanel, "Inserisci Parametri Climatici", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Raccogli i dati
                String area = areaField.getText();
                int vento = Integer.parseInt(ventoField.getText());
                int umidita = Integer.parseInt(umiditaField.getText());
                int pressione = Integer.parseInt(pressioneField.getText());
                int temperatura = Integer.parseInt(temperaturaField.getText());
                int precipitazioni = Integer.parseInt(precipitazioniField.getText());
                int altitudineGhiacciai = Integer.parseInt(altitudineGhiacciaiField.getText());
                int massaGhiacciai = Integer.parseInt(massaGhiacciaiField.getText());
                String note = noteArea.getText();

                // Chiamata al metodo remoto per inserire i parametri climatici
                try {
                    String result = client.inserisciParametriClimatici(area, vento, umidita, pressione, temperatura, precipitazioni, altitudineGhiacciai, massaGhiacciai, note);
                    textArea.append(result + "\n");
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            } else {
                textArea.append("Inserimento dei parametri climatici annullato.\n");
            }
        });

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(outputPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
