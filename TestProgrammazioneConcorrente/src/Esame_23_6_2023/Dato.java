package Esame_23_6_2023;

public class Dato {
	String ilDato;
	Dato(String s){
		ilDato=s;
	}
	public synchronized String getIlDato() {
		return ilDato;
	}
	public synchronized void setIlDato(String ilDato) {
		this.ilDato = ilDato;
	}
}
