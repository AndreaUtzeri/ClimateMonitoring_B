package Eserrcizio3;

public class Summer extends Thread {
	int vIn[];      // riferimento al vettore su cui operare
	Result resRef;  // riferimento al vettore in cui scrivere il risultato
	int row;        // indice della riga di resRef in cui scrivere il risultato
	Summer(int v[], int row, Result rr) {
		this.row=row;
		this.resRef=rr;
		this.vIn=v;
	}
	public void run() {
		int tSum=0;
		for(int j=0; j<vIn.length; j++) {
			tSum += vIn[j];
		}
		resRef.setSums(row, tSum);
		System.out.println("Result for row "+row+" is: " + tSum);
	}
}

