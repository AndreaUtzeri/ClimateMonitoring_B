package Esercizio06_n1a;

import java.net.*;
import java.io.*;
public class SegmentServer extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Segment seg;
	
	public SegmentServer(Socket s) throws IOException {
		socket=s;
		in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out= new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		start();
	}
	public void run() {
		try {
			while(true) {
				String str = in.readLine();
				System.out.println("Received : " + str);
				if(str.equals("END"))
					break;
				if(str.equals("NewSegment")) {
					String s1 = in.readLine();
					String s2 = in.readLine();
					Point p1 = new Point(Double.parseDouble(s1),Double.parseDouble(s2));
					s1 = in.readLine();
					s2=in.readLine();
					Point p2 = new Point(Double.parseDouble(s1),Double.parseDouble(s2));
					seg= new Segment();
					boolean res = seg.set(p1, p2);
					if(res) {
						out.println("OK");
						System.out.println("Server : segment created");
					}
					else {
						out.println("KO");
						System.out.println("Server : segment failed");
					}
				}
				
				if(str.equals("Simmetric")) {
					String s1 = in.readLine();
					String s2 = in.readLine();
					Point p1 = new Point(Double.parseDouble(s1),Double.parseDouble(s2));
					Point ps = seg.simmetric(p1);
					out.println(Double.toString(ps.getX()));
					out.println(Double.toString(ps.getY()));
					System.out.println("Server : sent simmetrici");
							
				}
			
			}
		
			System.out.println("closing...");
			
			
			
			
		} catch(IOException e) {
			System.err.println("muah");
		} finally {
			try {
				socket.close();
			} catch(IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}
	
}
