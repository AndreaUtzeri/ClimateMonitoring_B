package Esercizio06_n3;

import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread{
	private String myName;
	private Socket mySocket;
	private BufferedReader in;
	private PrintWriter out;
	public Consumer(InetAddress addr, String n) throws IOException{
		this.mySocket=new Socket(addr, 9999);
		this.myName=n;
		this.in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
		this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream())), true);
		start();
	}
	public void run(){
		String str;
		for(int i=0; i<8; i++) {	
			try {		
				out.println("get");
				str=in.readLine();
				System.out.println(myName+" consumes "+str);
				Thread.sleep(ThreadLocalRandom.current().nextInt(100, 200));
			} catch (IOException | InterruptedException e) {
				return;
			}
		}  // fine ciclo
		out.println("END");
	}
}
