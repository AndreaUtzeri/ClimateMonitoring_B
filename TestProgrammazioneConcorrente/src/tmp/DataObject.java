package tmp;


public class DataObject {
	String content;
	DataObject(){
		content="";
	}
	public synchronized String get() {
		while(content.equals("")) {
			try {
				wait();
			} catch (InterruptedException e) {		}
		}
		String t=content;
		content="";
		return t;
	}
	public synchronized void set(String s) {
		content=s;
		notifyAll();
	}
}
