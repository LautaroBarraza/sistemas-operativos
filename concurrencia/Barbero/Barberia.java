package Barbero;
import java.util.LinkedList;
import java.util.Queue;

public class Barberia {
    private int max_clientes;
    private int sillas_espera = 0;
    private Queue<Cliente> clientes;
    private Cliente cliente_atendido=null;

    public Barberia(int max_clientes) {
        this.max_clientes = max_clientes;
        this.clientes = new LinkedList<Cliente>();
    }



    public synchronized void atenderCliente() {
        while (sillas_espera == 0) {
            try {
                System.out.println("No hay clientes, el barbero se va a dormir.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sillas_espera--;
        this.cliente_atendido = this.clientes.poll();
        notifyAll();
        System.out.println("El barbero está atendiendo al cliente.");
    }

    public void sentarCliente(Cliente cliente) {
            synchronized(this){
                if (sillas_espera >= max_clientes){
                        System.out.println("La sala de espera está llena, el cliente se va.");
                        return;
                }
                this.clientes.offer(cliente);
                sillas_espera++;
                System.out.println("Un cliente ha llegado a la barbería.");
                notifyAll();
            }
            synchronized(cliente){
                while(!cliente.isAtendido()){
                    try{
                        cliente.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

            
    }

    public void terminarCorte() {
        synchronized(this.cliente_atendido){
            this.cliente_atendido.setAtendido(true);
            this.cliente_atendido.notify();
            this.cliente_atendido = null;
            System.out.println("El barbero ha terminado de atender al cliente.");
        }
    }
}