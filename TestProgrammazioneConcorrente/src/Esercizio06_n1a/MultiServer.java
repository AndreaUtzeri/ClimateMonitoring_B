package Esercizio06_n1a;

import java.net.*;
import java.io.*;
public class MultiServer {
	static final int PORT = 8080;    //iniziamo con la variabile per la porta del server
	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server started");
		try {
			while(true) {
				Socket socket = s.accept();
				System.out.println("Server acppets connection");
				try {
					new SegmentServer(socket);
				} catch (IOException e) {
					socket.close();
				}
			}
	
		} finally {
			s.close();
		}
	
	}
	
}
