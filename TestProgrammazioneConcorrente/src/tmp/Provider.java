package tmp;

import java.util.Random;

public class Provider extends Thread {
	Random rnd;
	DataObject data;
	Provider(DataObject d){
		rnd=new Random();
		data=d;
	}
	public void run() {
		boolean decision;
		while(true) {
			decision=rnd.nextBoolean();
			if(decision) {
				data.set("info_"+rnd.nextInt(1000));
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {		}
		}
	}
}

