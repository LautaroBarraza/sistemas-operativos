package concurrencia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<String> listaSincronizada = Collections.synchronizedList(new ArrayList<String>());
        
        Recurso r1 = new Recurso(new ArrayList<String>());
        Recurso r2 = new Recurso(listaSincronizada);
        Recurso r3 = new Recurso(listaSincronizada);

        Tarea t1 = new Tarea(r1, r2, "T1");
        Tarea t2 = new Tarea(r2, r3, "T2");

        t1.start();
        t2.start();

        // Línea 16: Main se encuentra pausado aquí
        t1.join(); 
        // Línea 17:
        t2.join(); 
    }
}
