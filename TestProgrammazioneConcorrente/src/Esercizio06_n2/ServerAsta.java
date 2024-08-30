package Esercizio06_n2;
import java.io.*;
import java.net.*;
public class ServerAsta {
	public static final int PORT = 9999;
	final static int MAXWAIT=1000;
	final static int MAXINACTIVITY=5000;
	public static void main(String[] args) throws IOException {
		Asta lAsta = new Asta(10000,50);
		ServerSocket s = new ServerSocket(PORT);
		s.setSoTimeout(MAXWAIT);
		while(true) {
			Socket cliSocket=null;
			try {
				cliSocket=s.accept();
				new ServerAstaThread(lAsta,cliSocket);
			} catch (SocketTimeoutException e) {
				long tNow=System.currentTimeMillis();
				if(tNow-lAsta.latestChange()>MAXINACTIVITY) {
					System.out.println("Max inactivity detected");
					break;
				}
			}
		}
		System.out.println("Aggiudicato a " + lAsta.leggi_titolare() + lAsta.leggi_offerta());
		s.close();
	}

}
