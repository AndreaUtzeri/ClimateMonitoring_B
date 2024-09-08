package climatemonitoring;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClientGUI {

    private static JTextArea textArea;

    public static void main(String[] args) throws RemoteException, NotBoundException {

        JFrame frame = new JFrame("Climate Monitoring");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Operatore client = new Operatore();
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

        // Create a JTextArea and a JScrollPane
        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add components to the panel
        panel.add(buttonzero);
        panel.add(buttonone);
        panel.add(buttontwo);
        panel.add(buttonthree);
        panel.add(buttonfour);
        panel.add(buttonfive);
        panel.add(buttonsix);
        panel.add(buttonseven);
        panel.add(buttoneight);
        panel.add(scrollPane); // Add the scrollPane with JTextArea to the panel

        buttonzero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    client.getDbmsCredential();
                    //textArea.append(result + "\n");
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    client.cercaAreaGeografica();
                    textArea.append("\n");
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttontwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.registerUser();
                    textArea.append("\n");
                } catch (RemoteException | NotBoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonthree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.loginUser();
                    textArea.append("\nLogin avvenuto con successo");
                } catch (RemoteException | NotBoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonfour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.logout();
                textArea.append("Logged out\n");
            }
        });

        buttonfive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.registraCentro();
                    textArea.append("\n");
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonsix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.associaCentro();
                    textArea.append("\n");
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonseven.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.inserisciParametriClimatici();
                    textArea.append("\n");
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttoneight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String result = client.visualizzaAreaGeografica();
                    textArea.append("\n" + result);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
