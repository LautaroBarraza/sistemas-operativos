package concurrencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



class Tarea extends Thread {
    private Recurso rA;
    private Recurso rB;
    private String nombre;

    public Tarea(Recurso rA, Recurso rB, String nombre) {
        this.rA = rA;
        this.rB = rB;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        rA.setId(1);
        rB.setId(2);
        rB.agregarDato(nombre);
        // Línea 23: T1 y T2 están pausados justo antes de imprimir
        System.out.println("Terminando tarea: " + nombre); 
    }
}
