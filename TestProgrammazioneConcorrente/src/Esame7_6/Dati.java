package Esame7_6;

import java.util.Hashtable;

public class Dati implements DatiInterface{
	Hashtable<String, String> iDati;
	public Dati(){
		iDati = new Hashtable<String, String>();
	}
	public synchronized void aggiungiDato(String key, String info) {
		if (!iDati.containsKey(key)) {
			iDati.put(key, info);
			System.out.println("Deposito dati: aggiunto " + key + "  " + info);
		} else {
			System.out.println("Deposito dati: NON aggiungo " + key + "gia` presente.");
		}
	}
	public synchronized void eliminaDato(String key) {
		if (iDati.containsKey(key)) {
			iDati.remove(key);
			System.out.println("Deposito dati: rimosso " + key);
		} else {
			System.out.println("Deposito dati: NON rimosso " + key + "gia` assente.");
		}
	}
	public synchronized boolean esisteDato(String key) {
		return iDati.containsKey(key);
	}
	public synchronized String trovaDato(String key){
		return iDati.get(key);
	}
}
