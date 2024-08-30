package Esercizio02;

public class Table {
	private boolean isFinita=false;
	private int numPlayers;
	private int aChiTocca;
	Table(int n){
		isFinita=false;
		numPlayers=n;
		aChiTocca=1;
	}
	synchronized void mossa(int playerId, int dado1, int dado2){
		while(!isFinita && aChiTocca!=playerId) {
			try { wait(); } catch (InterruptedException e) { }
		}
		if(!isFinita) {
			// qui si dovrebbero implementare gli effetti della mossa
			// ma non ci interessa
			System.out.println("Giocatore "+playerId+" ha giocato ["+dado1+" "+dado2+"]");
			isFinita=(Math.random()<0.1);
			if(isFinita){
				System.out.println("il giocatore "+playerId+" ha vinto!");
			}
		}
		if(dado1!=dado2 || isFinita){
			aChiTocca=(aChiTocca+1)%numPlayers;
		}
		notifyAll();
	}
	boolean finita(){
		return isFinita;
	}
}

