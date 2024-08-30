package Esercizio02;

public class myMain {
	final int NUMPLAYERS=3;
	public void exec() throws InterruptedException {
		Table gameTable = new Table(NUMPLAYERS);
		Thread[] thePlayers= new Thread[NUMPLAYERS];
		for(int i=0; i<NUMPLAYERS; i++) {
			thePlayers[i]= new Player(i, gameTable);
		}
		for(int i=0; i<NUMPLAYERS; i++) {
			thePlayers[i].join();
		}
		System.out.println("Game over");
	}
	public static void main(String[] args) throws InterruptedException {
		new myMain().exec();
	}
}
