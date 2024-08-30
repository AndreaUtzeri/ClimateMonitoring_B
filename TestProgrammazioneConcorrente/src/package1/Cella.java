package package1;
	
	public class Cella { 
		static final int BUFFERSIZE = 1;
		private int numItems = 0;
		private int valore;
		public int getCurrentSize(){
		return numItems;
		}
		public synchronized int getValore() {
			while(numItems==0) {
				try {
					notify();
					wait();
					} catch (InterruptedException e) { }
			}
				numItems--;
				System.out.println("Letto "+valore);
				notify();
				return valore;
			
		}
		public synchronized void setValore(int v) {
			while(numItems==BUFFERSIZE){try {
				notify();
				wait();
			} 
			catch (InterruptedException e) { }
			}
			valore=v;
			System.out.println("Scritto "+valore);
			numItems++;
			notify();
		
		}
	}
