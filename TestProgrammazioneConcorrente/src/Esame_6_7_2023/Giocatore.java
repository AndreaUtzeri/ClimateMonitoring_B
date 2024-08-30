package Esame_6_7_2023;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Giocatore extends Thread {
	int identificatore;
	TavoloGioco tavolo;
	Turno turno;
	Giocatore(int n, TavoloGioco t,Turno turno){
		identificatore=n;
		tavolo=t;
		this.turno=turno;
		this.setName("giocatore_"+n);
	}
	private void pensa() {
		while(true) {
			System.out.println(this.getName()+": pensa ... ");
			try { 
				Thread.sleep(ThreadLocalRandom.current().nextInt(2000)); 
			} catch (InterruptedException e) { }
			if(this.turno.aChiTocca(identificatore)) {
				break;
			}
		}
		System.out.println(this.getName()+": ha finito di pensare.");
	}
	public void run() {
		while(true) {
			//this.turno.aChiTocca(this.identificatore);
			pensa(); // simula il tempo passato a pensare alla prossima mossa
			tavolo.mossa(identificatore, "mossa_"+identificatore);
			this.turno.nextTurno(identificatore);
		}
	}
}
