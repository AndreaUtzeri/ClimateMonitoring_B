package Esercizio01;

public class Es3 extends Thread{
	public Es3(String nome){
		super(nome);
	}
	public void run() {
		for(int i=0;i<3;i++)
			System.out.println(getName());
	}
}

