package Esame_6_7_2023;

public class IlMain {
	private void exec() {
		Turno turno=new Turno();
		TavoloGioco tavolo=new TavoloGioco();
		Thread g1 =  new Giocatore(0, tavolo,turno); 
		Thread g2 =  new Giocatore(1, tavolo,turno); 
		g1.start();
		g2.start();
	}
	public static void main(String[] args) {
		new IlMain().exec();
	}
}
