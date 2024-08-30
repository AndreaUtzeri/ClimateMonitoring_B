package Esercizio06_n2;

import java.io.*;
import java.net.*;
public class ClientAsta {
	public static void main(String[] args) throws IOException,InterruptedException{
		final int numClient=3;
		InetAddress addr = InetAddress.getByName(null);
		for(int i=0;i<numClient;i++) {
			String cliName= "cli"+i;
			Socket s= new Socket(addr,9999);
			new ClientAstaThread(s,cliName);
		}
	}
}
