package tmp;


public class TheMain {
	final int  numRec=3;
	void exec() {
		DataObject data=new DataObject();
		Provider theProvider = new Provider(data);
		theProvider.start();
		Reader receivers[];
		receivers= new Reader[numRec];
		for(int i=0; i<numRec; i++) {
			receivers[i]=new Reader(i, data);
		}
	}
	public static void main(String[] args) {
		new TheMain().exec();
	}
}

