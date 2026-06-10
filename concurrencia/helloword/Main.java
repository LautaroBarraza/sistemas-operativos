package concurrencia.helloword;
import concurrencia.helloword.tarea;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Dato d1 = new Dato("Dato1", 100);
        tarea t1 = new tarea("Alice", 1, d1,1);
        tarea t2 = new tarea("Bob", 2, d1, 60);
        for(int i=0;i<100;i++){
            Thread th = new Thread(new tarea("Thread-" + i, i, d1, i));
            th.start();

        }
        Thread th1 = new Thread(t1);
        Thread th2 = new Thread(t2);
        th1.start();
        th2.start();
        th1.join();
        th2.join();
    }
}
