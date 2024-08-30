package tmp;
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.random.*;
public class ServerThread {
	
	private DataObject data;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ServerThread(Socket s,DataObject d) throws IOException {
		data=d;
		socket=s;
		in= new ObjectInputStream(socket.getInputStream());
		out= new ObjectOutputStream(socket.getOutputStream());
	}
	
	public void run() {
		String str;
		String info;
		Random rnd=new Random();
		while(true) {
			try {
				str=(String)in.readObject();
				if(str.equals("END"))
					break;
				else{
					data.set("info_"+rnd.nextInt(1000));
					info=data.get();
					out.writeObject(info);
					}
			
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
