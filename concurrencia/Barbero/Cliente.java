package Barbero;
import Barbero.Barberia;
public class Cliente implements Runnable{
    private Barberia Barberia;
    private boolean atendido=false;
    public Cliente(Barberia barberia) {
        this.Barberia = barberia;
    }



    @Override
    public void run() {
        // si no encuentra lugar en la sala de espera se va, si encuentra lugar se sienta y espera a ser atendido
        Barberia.sentarCliente(this);
    }

    public boolean isAtendido() {
        return atendido;
    }
    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }
}