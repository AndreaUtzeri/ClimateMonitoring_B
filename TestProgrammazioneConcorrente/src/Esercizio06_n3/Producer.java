package Esercizio06_n3;

import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread{
	private String myName;
	private Socket mySocket;
	private BufferedReader in;
	private PrintWriter out;
	public Producer(InetAddress addr, String n) throws IOException{
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
				Thread.sleep(ThreadLocalRandom.current().nextInt(100, 200));
			} catch (InterruptedException e) {	}
			out.println("put");
			System.out.println("Producing "+myName+"_"+i);
			out.println(myName+"_"+i);
		}  // fine ciclo
		out.println("END");
	}
}
