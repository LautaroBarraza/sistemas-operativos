package concurrencia.helloword;

public class Dato {
    private String name;
    private int valor;
    public Dato(String name, int valor) {
        this.name = name;
        this.valor = valor;
    }

    public String getName() {
        return name;
    }

    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
}
