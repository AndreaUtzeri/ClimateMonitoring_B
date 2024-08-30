package Esercizio06_n2;
import java.io.*;
import java.net.*;
public class ClientAstaThread extends Thread{
	private int maxTries = 3+(int)(Math.random()*4);
	private int numTries=0;
	private int currentOffer=0;      //campo chiave
	private int myOffer;				//
	private String currentWinner;
	private final double myIncrease=1.06;
	private String myName;			//
	private Socket mySocket;		//
	private BufferedReader in;		//
	private PrintWriter out;		//
	
	public ClientAstaThread(Socket s,String n) throws IOException {
		this.mySocket=s;
		this.myName=n;
		this.in=new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
		this.out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream())),true);
		start();
	}
	
	public void run(){
		String str;
		boolean finito=false;
		while(!finito && numTries<maxTries) {
			try {
				out.println("read");
				str=in.readLine();
				currentOffer=Integer.parseInt(str);
				currentWinner=in.readLine();
				if(!currentWinner.equals(myName)) {
					myOffer=(int) (currentOffer*myIncrease);
					str=String.valueOf(myOffer);
					out.println("offer "+ str +" " + myName);
					str=in.readLine();
					if(str.equals("KO")) {
						Thread.sleep(100);
					}
				}
				else
					Thread.sleep(1500);
			} catch ( IOException | InterruptedException e1) {break;}
			numTries++;
		}
		out.println("END");
	}






}
