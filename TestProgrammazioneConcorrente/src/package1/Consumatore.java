package package1;

import java.util.concurrent.ThreadLocalRandom;
class Consumatore extends Thread{
Cella cellaCondivisa;
public Consumatore(Cella c){
this.cellaCondivisa=c;
}
public void run(){
int v;
for(int i=1; i<=10; ++i){
v=cellaCondivisa.getValore();
try {
Thread.sleep(ThreadLocalRandom.current().
nextInt(10, 100));
} catch (InterruptedException e) { }
}
}
}
