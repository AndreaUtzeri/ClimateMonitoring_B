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

        // bottoni (inizialmente nascosti)
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

        // Pannello  output
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        textArea = new JTextArea(10, 60);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        // Pannello per i parametri climatici (inizialmente nascosto)
        JPanel paramPanel = new JPanel();
        paramPanel.setLayout(new GridLayout(9, 2));

        // Questo è perrimuovere i parametri climatici dalla GUI iniziale
        textFieldArea = new JTextField(20);
        textFieldVento = new JTextField(20);
        textFieldUmidita = new JTextField(20);
        textFieldPressione = new JTextField(20);
        textFieldTemperatura = new JTextField(20);
        textFieldPrecipitazioni = new JTextField(20);
        textFieldAltitudineGhiacciai = new JTextField(20);
        textFieldMassaGhiacciai = new JTextField(20);
        textAreaNote = new JTextArea(5, 20);
        

        // Layout principale
        frame.setLayout(new BorderLayout());

        // All'inizio così si aggiunge solo il bottone delle credenziali
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
            // Chiede all'utente se vuole cercare per Nome, Stato o Coordinate
            Object[] options = {"Nome", "Stato", "Coordinate"};
            int tipoRicerca = JOptionPane.showOptionDialog(null,
                    "Come vuoi cercare?",
                    "Seleziona metodo di ricerca",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            // Se l'utente annulla la selezione
            if (tipoRicerca == JOptionPane.CLOSED_OPTION) {
                textArea.append("Ricerca annullata.\n");
                return;
            }

            // Imposta il messaggio del prompt in base alla scelta
            String promptMessage;
            switch (tipoRicerca) {
                case 0: promptMessage = "Inserisci il nome:"; break;
                case 1: promptMessage = "Inserisci il codice dello stato:"; break;
                case 2: promptMessage = "Inserisci le coordinate (latitudine,longitudine):"; break;
                default: return; // Caso improbabile
            }

            // Chiede all'utente di inserire la stringa di ricerca
            JTextField inputField = new JTextField();
            Object[] message = {promptMessage, inputField};
            int option = JOptionPane.showConfirmDialog(null, message, "Ricerca", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String valoreRicerca = inputField.getText().trim();

                if (!valoreRicerca.isEmpty()) {
                    try {
                        // Chiama il metodo cercaAreaGeografica passando il tipo di ricerca e il valore
                        String result = client.cercaAreaGeografica(tipoRicerca, valoreRicerca);
                        textArea.append(result + "\n");
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                        textArea.append("Errore durante la ricerca.\n");
                    }
                } else {
                    textArea.append("Ricerca annullata o valore non valido.\n");
                }
            } else {
                textArea.append("Ricerca annullata.\n");
            }
        });




        buttontwo.addActionListener(e -> {
            // Campi di input
            JTextField usernameField = new JTextField();
            JTextField passwordField = new JPasswordField();
            JTextField nomeField = new JTextField();
            JTextField cognomeField = new JTextField();
            JTextField codiceFiscaleField = new JTextField();
            JTextField emailField = new JTextField();

            // Messaggio di input
            Object[] message = {
                "Inserisci il tuo nome utente:", usernameField,
                "Inserisci la tua password:", passwordField,
                "Inserisci il tuo nome:", nomeField,
                "Inserisci il tuo cognome:", cognomeField,
                "Inserisci il tuo codice fiscale:", codiceFiscaleField,
                "Inserisci la tua email:", emailField
            };

            // Finestra di dialogo
            int option = JOptionPane.showConfirmDialog(null, message, "Registrazione", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String codiceFiscale = codiceFiscaleField.getText();
                String email = emailField.getText();

                // Controlla che i campi non siano vuoti
                if (username.isEmpty() || password.isEmpty() || nome.isEmpty() || cognome.isEmpty() || codiceFiscale.isEmpty() || email.isEmpty()) {
                    textArea.append("Tutti i campi sono obbligatori.\n");
                } else {
                    try {
                        // Chiamata al metodo remoto
                        String result = client.registerUser(username, password, nome, cognome, codiceFiscale, email);
                        textArea.append(result + "\n");
                    } catch (RemoteException | NotBoundException e1) {
                        e1.printStackTrace();
                        textArea.append("Errore durante la registrazione.\n");
                    }
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

                    if (result.equalsIgnoreCase("Login avvenuto con successo!")) { // Controlla se il login è riuscito
                        textArea.append(result + "\n");

                        // Mostra i bottoni aggiuntivi dopo il login
                        buttonPanel.add(buttonseven); // Insert new climate parameters
                        buttonPanel.add(buttonfive);  // Register a Monitoring Center
                        buttonPanel.add(buttonsix);   // Associate to a Monitoring Center
                        frame.revalidate();    // Rendi visibili i bottoni aggiornati
                        frame.repaint();
                    } else {
                        textArea.append("Login fallito. Controlla username e password.\n");
                    }
                } catch (RemoteException | NotBoundException e1) {
                    e1.printStackTrace();
                    textArea.append("Errore durante il tentativo di login.\n");
                }
            } else {
                textArea.append("Login annullato.\n");
            }
        });


        buttonfour.addActionListener(e -> {
            
            client.logout();
            textArea.append("Logged out\n");

            // Rimuovi tutti i bottoni attualmente visibili
            buttonPanel.removeAll();

            // Aggiungi solo i bottoni disponibili prima del login(così da nascondere ai non registrati le opzioni)
            buttonPanel.add(buttonzero);  // Insert DBMS credentials
            buttonPanel.add(buttonone);   // Research an Area
            buttonPanel.add(buttontwo);   // Register as an operator
            buttonPanel.add(buttonthree); // Login as an operator
            buttonPanel.add(buttonfour);  // Logout
            buttonPanel.add(buttoneight); // View Climate Parameters registered for an Area

            // Aggiorna l'interfaccia
            frame.revalidate();
            frame.repaint();
        });

        
        buttonfive.addActionListener(e -> {
            // Pannello per raccogliere il nome del centro e l'indirizzo
            JTextField centerNameField = new JTextField(20);
            JTextField addressField = new JTextField(20);
            JPanel centerPanel = new JPanel(new GridLayout(0, 1));
            centerPanel.add(new JLabel("Nome del Centro:"));
            centerPanel.add(centerNameField);
            centerPanel.add(new JLabel("Indirizzo del Centro:"));
            centerPanel.add(addressField);

            // Primo dialogo per nome e indirizzo del centro
            int option = JOptionPane.showConfirmDialog(
                frame,
                centerPanel,
                "Inserisci i dettagli del centro di monitoraggio",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (option == JOptionPane.OK_OPTION) {
                String centerName = centerNameField.getText().trim();
                String address = addressField.getText().trim();

                if (centerName.isEmpty() || address.isEmpty()) {
                    textArea.append("Nome o indirizzo del centro mancanti.\n");
                    return;
                }

                // Lista per raccogliere le aree di interesse
                List<String> areeDiInteresse = new ArrayList<>();
                boolean addMore = true;

                while (addMore) {
                    JTextField areaField = new JTextField(20);
                    JPanel areaPanel = new JPanel(new GridLayout(0, 1));
                    areaPanel.add(new JLabel("Inserisci un'area di interesse:"));
                    areaPanel.add(areaField);

                    int areaOption = JOptionPane.showConfirmDialog(
                        frame,
                        areaPanel,
                        "Aggiungi Area di Interesse",
                        JOptionPane.OK_CANCEL_OPTION
                    );

                    if (areaOption == JOptionPane.OK_OPTION) {
                        String area = areaField.getText().trim();
                        if (!area.isEmpty()) {
                            areeDiInteresse.add(area);
                        } else {
                            textArea.append("Area di interesse non valida.\n");
                        }
                    } else {
                        addMore = false;
                    }

                    // Richiesta se aggiungere un'altra area
                    int continueOption = JOptionPane.showConfirmDialog(
                        frame,
                        "Vuoi aggiungere un'altra area di interesse?",
                        "Continuare?",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (continueOption == JOptionPane.NO_OPTION) {
                        addMore = false;
                    }
                }

                // Verifica che ci siano aree di interesse
                if (areeDiInteresse.isEmpty()) {
                    textArea.append("Nessuna area di interesse inserita.\n");
                    return;
                }

                // Passa i dati al metodo remoto
                try {
                    String result = client.registraCentro(centerName, address, areeDiInteresse);
                    textArea.append(result + "\n");
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    textArea.append("Errore durante la registrazione del centro di monitoraggio.\n");
                }
            } else {
                textArea.append("Registrazione del centro annullata.\n");
            }
        });



        
        buttonsix.addActionListener(e -> {
            
            String associateCenter = JOptionPane.showInputDialog(frame, "Inserisci il nome del centro di monitoraggio a cui vuoi associarti:");

            if (associateCenter != null && !associateCenter.trim().isEmpty()) {
                try {
                    // Passa il nome del centro al metodo associaCentro
                    String result = client.associaCentro(associateCenter.trim());
                    textArea.append(result + "\n"); // Mostra il risultato nell'area di testo
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    textArea.append("Errore durante l'associazione al centro.\n");
                }
            } else {
                textArea.append("Nome del centro non valido o annullato.\n");
            }
        });



        // bottone "Inserisci nuovi parametri climatici"
        buttonseven.addActionListener(e -> {
            // Pannello per inserire nput
            JPanel paramDialogPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Margini interni
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;

            
            paramDialogPanel.add(new JLabel("Area:"), gbc);
            gbc.gridx = 1;
            JTextField areaField = new JTextField(15);
            paramDialogPanel.add(areaField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            paramDialogPanel.add(new JLabel("Vento:"), gbc);
            gbc.gridx = 1;
            JTextField ventoField = new JTextField(10);
            paramDialogPanel.add(ventoField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            paramDialogPanel.add(new JLabel("Umidita:"), gbc);
            gbc.gridx = 1;
            JTextField umiditaField = new JTextField(10);
            paramDialogPanel.add(umiditaField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            paramDialogPanel.add(new JLabel("Pressione:"), gbc);
            gbc.gridx = 1;
            JTextField pressioneField = new JTextField(10);
            paramDialogPanel.add(pressioneField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            paramDialogPanel.add(new JLabel("Temperatura:"), gbc);
            gbc.gridx = 1;
            JTextField temperaturaField = new JTextField(10);
            paramDialogPanel.add(temperaturaField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            paramDialogPanel.add(new JLabel("Precipitazioni:"), gbc);
            gbc.gridx = 1;
            JTextField precipitazioniField = new JTextField(10);
            paramDialogPanel.add(precipitazioniField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            paramDialogPanel.add(new JLabel("Altitudine Ghiacciai:"), gbc);
            gbc.gridx = 1;
            JTextField altitudineGhiacciaiField = new JTextField(10);
            paramDialogPanel.add(altitudineGhiacciaiField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            paramDialogPanel.add(new JLabel("Massa Ghiacciai:"), gbc);
            gbc.gridx = 1;
            JTextField massaGhiacciaiField = new JTextField(10);
            paramDialogPanel.add(massaGhiacciaiField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            paramDialogPanel.add(new JLabel("Note:"), gbc);
            gbc.gridy++;
            JTextArea noteArea = new JTextArea(3, 20);
            JScrollPane notesScrollPane = new JScrollPane(noteArea); // Nome modificato
            paramDialogPanel.add(notesScrollPane, gbc);

            
            int option = JOptionPane.showConfirmDialog(frame, paramDialogPanel, "Inserisci Parametri Climatici", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
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
                    String result = client.inserisciParametriClimatici(area, vento, umidita, pressione, temperatura, precipitazioni, altitudineGhiacciai, massaGhiacciai, note);
                    textArea.append(result + "\n");
                } catch (RemoteException | NumberFormatException e1) {
                    e1.printStackTrace();
                    textArea.append("Errore durante l'inserimento dei parametri climatici.\n");
                }
            } else {
                textArea.append("Inserimento dei parametri climatici annullato.\n");
            }
        });


        
     // Implementazione per il bottone "View climate parameters registered for an area"
        buttoneight.addActionListener(e -> {
            // Mostra una finestra di dialogo per chiedere il nome dell'area
            JTextField areaField = new JTextField(20);
            Object[] message = {
                "Inserisci il nome dell'area:",
                areaField
            };

            int option = JOptionPane.showConfirmDialog(frame, message, "Visualizza Area Geografica", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String area = areaField.getText().trim();
                if (!area.isEmpty()) {
                    try {
                        // Chiamata al metodo visualizzaAreaGeografica dell'oggetto client
                        String result = client.visualizzaAreaGeografica(area);
                        textArea.append(result + "\n");
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                        textArea.append("Errore durante la visualizzazione dell'area.\n");
                    }
                } else {
                    textArea.append("Nome dell'area non valido.\n");
                }
            } else {
                textArea.append("Visualizzazione area annullata.\n");
            }
        });

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(outputPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}