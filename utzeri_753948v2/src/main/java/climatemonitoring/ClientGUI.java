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

        JButton buttonzero = new JButton("Insert DBMS credentials");
        JButton buttonone = new JButton("Research an Area");
        JButton buttontwo = new JButton("Register as an operator");
        JButton buttonthree = new JButton("Login as an operator");
        JButton buttonfour = new JButton("Logout");
        JButton buttonfive = new JButton("Register a Monitoring Center");
        JButton buttonsix = new JButton("Associate to a Monitoring Center");
        JButton buttonseven = new JButton("Insert new climate parameters");
        JButton buttoneight = new JButton("View climate parameters registered for an area");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        textArea = new JTextArea(10, 60);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        textFieldArea = new JTextField(20);
        textFieldVento = new JTextField(20);
        textFieldUmidita = new JTextField(20);
        textFieldPressione = new JTextField(20);
        textFieldTemperatura = new JTextField(20);
        textFieldPrecipitazioni = new JTextField(20);
        textFieldAltitudineGhiacciai = new JTextField(20);
        textFieldMassaGhiacciai = new JTextField(20);
        textAreaNote = new JTextArea(5, 20);

        JPanel paramPanel = new JPanel();
        paramPanel.setLayout(new GridLayout(9, 2));
        paramPanel.add(new JLabel("Area:"));
        paramPanel.add(textFieldArea);
        paramPanel.add(new JLabel("Vento:"));
        paramPanel.add(textFieldVento);
        paramPanel.add(new JLabel("Umidita:"));
        paramPanel.add(textFieldUmidita);
        paramPanel.add(new JLabel("Pressione:"));
        paramPanel.add(textFieldPressione);
        paramPanel.add(new JLabel("Temperatura:"));
        paramPanel.add(textFieldTemperatura);
        paramPanel.add(new JLabel("Precipitazioni:"));
        paramPanel.add(textFieldPrecipitazioni);
        paramPanel.add(new JLabel("Altitudine Ghiacciai:"));
        paramPanel.add(textFieldAltitudineGhiacciai);
        paramPanel.add(new JLabel("Massa Ghiacciai:"));
        paramPanel.add(textFieldMassaGhiacciai);
        paramPanel.add(new JLabel("Note:"));
        paramPanel.add(new JScrollPane(textAreaNote));

        panel.add(buttonzero);
        panel.add(buttonone);
        panel.add(buttontwo);
        panel.add(buttonthree);
        panel.add(buttonfour);
        panel.add(buttonfive);
        panel.add(buttonsix);
        panel.add(paramPanel); // Aggiungi il pannello dei parametri
        panel.add(buttonseven);
        panel.add(buttoneight);
        panel.add(scrollPane);

        buttonsix.addActionListener(e -> {
            JTextField centerNameField = new JTextField();
            Object[] message = {
                "Inserisci il nome del centro di monitoraggio:", centerNameField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Associa Centro", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String associatecenter = centerNameField.getText();
                try {
                    String result = client.associaCentro(associatecenter);
                    appendToTextArea(result);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                    appendToTextArea("\nErrore durante l'associazione al centro.");
                }
            }
        });

        buttonzero.addActionListener(e -> {
            try {
                client.getDbmsCredential();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

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

        buttonfive.addActionListener(e -> {
            JTextField centroField = new JTextField();
            JTextField indirizzoField = new JTextField();
            JPanel panel2 = new JPanel(new GridLayout(0, 1));
            panel2.add(new JLabel("Nome Centro:"));
            panel2.add(centroField);
            panel2.add(new JLabel("Indirizzo:"));
            panel2.add(indirizzoField);

            int result = JOptionPane.showConfirmDialog(null, panel2, "Registrazione Centro", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String nomeCentro = centroField.getText();
                String indirizzo = indirizzoField.getText();
                
                // Raccolta delle aree di interesse
                List<String> areeDiInteresse = new ArrayList<>();
                boolean continua = true;

                while (continua) {
                    JTextField areaField = new JTextField();
                    JPanel areaPanel = new JPanel(new GridLayout(0, 1));
                    areaPanel.add(new JLabel("Inserisci un'area di interesse:"));
                    areaPanel.add(areaField);

                    int areaOption = JOptionPane.showConfirmDialog(null, areaPanel, "Aree di Interesse", JOptionPane.OK_CANCEL_OPTION);
                    if (areaOption == JOptionPane.OK_OPTION) {
                        String area = areaField.getText();
                        if (area != null && !area.trim().isEmpty()) {
                            areeDiInteresse.add(area);
                        }
                    }

                    int risposta = JOptionPane.showConfirmDialog(null, "Vuoi inserire un'altra area?", "Aree di Interesse", JOptionPane.YES_NO_OPTION);
                    if (risposta == JOptionPane.NO_OPTION) {
                        continua = false;
                    }
                }

                try {
                    String resultMessage = client.registraCentro(nomeCentro, indirizzo, areeDiInteresse);
                    appendToTextArea(resultMessage);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    appendToTextArea("Errore nella registrazione del centro.");
                }
            }
        });


        buttonseven.addActionListener(e -> {
            try {
                // Raccogli i dati dalla GUI
                String area = textFieldArea.getText();
                int vento = Integer.parseInt(textFieldVento.getText());
                int umidita = Integer.parseInt(textFieldUmidita.getText());
                int pressione = Integer.parseInt(textFieldPressione.getText());
                int temperatura = Integer.parseInt(textFieldTemperatura.getText());
                int precipitazioni = Integer.parseInt(textFieldPrecipitazioni.getText());
                int altitudineGhiacciai = Integer.parseInt(textFieldAltitudineGhiacciai.getText());
                int massaGhiacciai = Integer.parseInt(textFieldMassaGhiacciai.getText());
                String note = textAreaNote.getText();

                // Chiama il metodo remoto passando tutti i parametri
                String result = client.inserisciParametriClimatici(area, vento, umidita, pressione, temperatura, precipitazioni, altitudineGhiacciai, massaGhiacciai, note);
                textArea.append(result + "\n");
            } catch (RemoteException ex) {
                ex.printStackTrace();
                textArea.append("Errore nell'inserimento dei parametri climatici.\n");
            }
        });

        buttoneight.addActionListener(e -> {
            try {
                String result = client.visualizzaAreaGeografica();
                textArea.append("\n" + result);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void appendToTextArea(String message) {
        textArea.append(message + "\n");
    }
}
