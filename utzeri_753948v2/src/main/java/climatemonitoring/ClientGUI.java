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
	
	public static void main(String[] args) throws RemoteException, NotBoundException {
		
		JFrame frame = new JFrame("Climate Monitoring");
		frame.setSize(400, 300);
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
		
		 buttonzero.addActionListener(new ActionListener() {
	            
	            public void actionPerformed(ActionEvent e) {
	                try {
	                	client.getDbmsCredential();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
	            }
	        });
		
		 buttonone.addActionListener(new ActionListener() {
	            
	            public void actionPerformed(ActionEvent e) {
	                try {
						client.cercaAreaGeografica();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
	            }
	        });
		 
		 buttontwo.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
						client.registerUser();
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
	            }
	        });
		 
		 buttonthree.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
						client.loginUser();
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
		 
		 buttonfour.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                client.logout();
	            }
	        });
		 
		 buttonfive.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
						client.registraCentro();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
		 
		 buttonsix.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
						client.associaCentro();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
	            }
	        });
		 
		 buttonseven.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
						client.inserisciParametriClimatici();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
		 
		 buttoneight.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
						client.visualizzaAreaGeografica();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
		
		panel.add(buttonzero);
		panel.add(buttonone);
		panel.add(buttontwo);
		panel.add(buttonthree);
		panel.add(buttonfour);
		panel.add(buttonfive);
		panel.add(buttonsix);
		panel.add(buttonseven);
		panel.add(buttoneight);
		
		frame.add(panel);
		
		frame.setVisible(true);
		
		
	}

}
