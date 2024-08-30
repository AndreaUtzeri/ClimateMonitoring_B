package Esercizio02_n5;

public class Table {
	private boolean isFinita=false;
	Table(){
		isFinita=false;
	}
	void mossa(int playerId, int dado1, int dado2){
		// qui si potrebbero implementare gli effetti della mossa
		// ma non ci interessa
		isFinita=(Math.random()<0.2);
		if(isFinita){
			System.out.println("il giocatore "+playerId+" ha vinto!");
		}
	}
	boolean finita(){
		return isFinita;
	}
}

