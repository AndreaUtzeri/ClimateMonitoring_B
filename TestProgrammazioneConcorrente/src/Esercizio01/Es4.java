package Esercizio01;

public class Es4 implements Runnable{
	public void run() {
		for(int i=0;i<3;i++)
			System.out.println(Thread.currentThread().getName());
	}
}
