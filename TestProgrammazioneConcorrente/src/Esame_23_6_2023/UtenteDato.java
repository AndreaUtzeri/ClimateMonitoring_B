package Esame_23_6_2023;

public class UtenteDato extends Thread {
	Dato ilDato;
	UtenteDato(int i, Dato d){
		super("utente_"+i);	
		ilDato=d;
		start();
	}
	private boolean elaboro() {
		int a=0;
		System.out.println(this.getName()+": elaboro ... ");
		for(int i=0; i<10; i++) {
			if(this.interrupted())
				return true;
			for(int j=0; j<10; j++) {
				a=1-a;
			}
		}
	return false;
	}
	public void run() {
		String str;
		while(true){
			if(elaboro()) {
			// modificare: il dato va letto solo se e` cambiato
			str=ilDato.getIlDato();
			System.out.println(this.getName()+": letto "+str);
			}
		}
	}
}
