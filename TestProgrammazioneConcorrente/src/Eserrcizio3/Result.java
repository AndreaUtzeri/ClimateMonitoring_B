package Eserrcizio3;

//classe che registra lo stato dei calcoli
//ciascun thread ci scrive quando ha completato il suo compito
//il main la consulta per vedere quando tutti hanno terminato
public class Result {
	private int expectedSums;
	private int completedSums=0;
	int resultsRef[];
	public Result(int r[], int exp) {
		this.expectedSums=exp;
		this.completedSums=0;
		this.resultsRef=r;
	}
	// setSums viene chiamato da ogni thread che ha finito il suo compito
	public synchronized void setSums(int i, int v){
		resultsRef[i]=v;
		this.completedSums++;
		if(completedSums==expectedSums) {
			notify();
		}
	}
	// metodo per aspettare che la somma sia completa
	public synchronized void waitCompletion(){
		while(completedSums<expectedSums) {
			try {
				wait();
			} catch (InterruptedException e) { }
		}
	}
	// isCompleted viene chiamato dal main per sapere se i thread hanno TUTTI
	// completato il loro compito
	public boolean isCompleted(){
		return (completedSums==expectedSums);
	}
}

