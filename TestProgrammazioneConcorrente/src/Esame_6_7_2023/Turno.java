package Esame_6_7_2023;

public class Turno {
	private int player=0;
	
	public synchronized boolean aChiTocca(int giocatore) {
		if(giocatore != player)
			return false;
		return true;
	}
	public synchronized void nextTurno(int giocatore) {
		this.player=1-giocatore;
		//System.out.println("giocatore"+giocatore);
		notify();
	}

}
