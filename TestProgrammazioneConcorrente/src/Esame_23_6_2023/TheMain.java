package Esame_23_6_2023;

import java.util.Random;

public class TheMain {
	Dato ilDatoCondiviso;
	TheMain(){
		ilDatoCondiviso=new Dato("default");
	}
	void exec(int numThread) {
		Random rnd=new Random();
		Thread[] iThread;
		iThread = new Thread[numThread];
		for(int j=0; j<numThread; j++) {
			iThread[j]=new UtenteDato(j, ilDatoCondiviso);
		}
		while(true) {
			try { Thread.sleep(10); } catch (InterruptedException e) { }
			ilDatoCondiviso.setIlDato("info_"+rnd.nextInt(1000));
			// qui bisogna informare i thread UtenteDato che il dato condiviso e` stato aggiornato
		for(int j=0; j<numThread ;j++) {
			iThread[j].interrupt();
		}
		}
	}
	public static void main(String[] args) {
		new TheMain().exec(5);
	}
}
