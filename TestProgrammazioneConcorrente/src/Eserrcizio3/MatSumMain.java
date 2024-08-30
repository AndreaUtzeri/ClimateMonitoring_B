package Eserrcizio3;

public class MatSumMain {
	final boolean POLLING=false;
	int matrix[][]=
		{
				{ 1, 2, 3, 4, 5} ,
				{ 2, 2, 2, 2, 2 } ,
				{ 3, 3, 3, 3, 3 } ,
				{ 4, 4, 4, 4, 3 } ,
				{ 5, 5, 5, 5, 5 } } ;
	int rows = matrix.length;
	int cols = matrix[0].length;
	int results[];
	void printMatrix() {
		for(int i=0; i<rows; i++){
			printVector(matrix[i]);
		}
	}
	void printVector(int[] vec) {
		System.out.print("[");
		for(int i=0; i<vec.length; i++){
			System.out.print(vec[i]+" ");
		}
		System.out.println("]");
	}
	public void exec() {
		results=new int[rows];
		Result res=new Result(results, rows);
		printMatrix();
		for(int i=0; i<rows; i++) {
			new Summer(matrix[i], i, res).start();
		}
		if(POLLING) {
			while(!res.isCompleted()){
				System.out.println("Main: waiting...");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {	}
			}
		} else {
			res.waitCompletion();
		}
		printVector(results);
	}
	public static void main(String[] args) {
		MatSumMain msm = new MatSumMain();
		msm.exec();
		System.out.println("Main: finito");
	}

}

