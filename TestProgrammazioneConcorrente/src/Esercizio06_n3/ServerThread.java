package Esercizio06_n3;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

public class ServerThread extends Thread {
	private BlockingQueue data;
	private Socket cliSocket;
	private BufferedReader in;
	private PrintWriter out;
	public ServerThread(BlockingQueue d, Socket s) throws IOException{
		this.data=d;
		this.cliSocket=s;
		this.in= new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));
		this.out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(cliSocket.getOutputStream())),true);
		start();
	}
	public void run(){
		int off;
		while(true) {
			String str;
			try {
				str = in.readLine();
			} catch (IOException e) { str="END";
			}
			System.out.println("Server received "+str);
			if(str.equals("END")) break;
			if(str.equals("put")){
				try {
					data.put(in.readLine());
				} catch (InterruptedException | IOException e) {	}
			}
			if(str.equals("get")){
				try {
					out.println(data.take());
				} catch (InterruptedException e) {}
			} 
		}
		try {
			cliSocket.close();
		} catch (IOException e) { e.printStackTrace(); }	
	}
}
