package Esercizio01;

public class Main {

	public static void main(String[] args) {
		/*   ESERCIZIO 1
		Es1 x = new Es1();
		x.start();
		for(int i=0;i<3;i++)
			System.out.println("Main");
		*/

		//ESERCIZIO 2
		//quasi uguale es 1
		
		//ESERCIZIO 3
		/*Es3 x = new Es3("gabibbo");
		x.start();
		for(int i=0;i<3;i++)
			System.out.println("Main"); 
			*/
	//ESERCIZIO 4
		Es4 x =new Es4();
		new Thread(x,"gabibbo");
		for(int i=0;i<3;i++)
			System.out.println("Main");
	}
	//esercizio 5 it's the same as the previous exercise but need to write "this.start" in the constructor
}
