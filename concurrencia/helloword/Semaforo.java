package concurrencia.helloword;

public class Semaforo {
    private int valor;

    public Semaforo(int valor) {
        this.valor = valor;
    }
    
    public synchronized void toWait() throws InterruptedException{
        while(valor==0){
            this.wait();
        }
        valor--;
    }

    public synchronized void toNotify() throws InterruptedException{
        valor++;
        this.notify();
    }
}
