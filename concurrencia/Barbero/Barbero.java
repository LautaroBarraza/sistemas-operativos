package Barbero;
import Barbero.Barberia;

public class Barbero implements Runnable{

    private Barberia Barberia;
    public Barbero(Barberia barberia) {
        this.Barberia = barberia;

    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub

        while(true){
            
               Barberia.atenderCliente();
            try {
                Thread.sleep(1000);
                Barberia.terminarCorte();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // Simula el tiempo que tarda en atender a un cliente
        }

    }
    
}
