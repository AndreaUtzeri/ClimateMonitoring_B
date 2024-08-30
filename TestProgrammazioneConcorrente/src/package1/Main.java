package package1;

public class Main {

	public static void main(String[] args) {
		Cella cella=new Cella();
		new Produttore(cella).start();
		new Consumatore(cella).start();
	}
}
