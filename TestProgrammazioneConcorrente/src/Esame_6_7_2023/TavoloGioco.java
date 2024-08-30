package Esame_6_7_2023;

public class TavoloGioco {
	TavoloGioco(){
	}
	public synchronized void mossa(int chi, String m) {
		System.out.println("Giocatore "+chi+" ha fatto mossa "+m);
	}
}
