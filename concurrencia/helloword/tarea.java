package concurrencia.helloword;

public class tarea implements Runnable{
    private String name;
    private int ID;
    private Dato d;
    private int i;

    public tarea(String name, int ID, Dato d, int i) {
        this.name = name;
        this.ID = ID;
        this.d = d;
        ///this.d.setValor(i);
        this.i=i;
    }

    @Override
    public void run() {
        synchronized (d) {
        this.d.setValor(i);
        System.out.println("Hola Mundo! Soy " + name + " con ID: " + ID);
        System.out.println("Dato: " + d.getName() + " - Valor: " + d.getValor());
        System.out.println(Thread.currentThread().getName());
        }
    }
}
