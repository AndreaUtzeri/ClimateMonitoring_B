package Esercizio02;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Table gameTable = new Table(3);
		Thread player1 = new Player(1, gameTable);
		Thread player2 = new Player(2, gameTable);
		Thread player3 = new Player(0, gameTable);
		player1.join();
		player2.join();
		player3.join();		
		System.out.println("Game over");

	}

}
