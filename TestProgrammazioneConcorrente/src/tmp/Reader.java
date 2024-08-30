package tmp;


public class Reader extends Thread {
	int myId;
	DataObject data;
	public Reader(int i, DataObject d) {
		myId=i;
		data=d;
		this.start();
	}

	public void run() {
		String s;
		while(true) {
			s=data.get();
			System.out.println("Reader "+myId+" ha letto "+s);
		}
	}
}

