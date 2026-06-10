package concurrencia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
class Recurso {
    private int id;
    private List<String> lista;

    public Recurso(List<String> lista) {
        this.lista = lista;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void agregarDato(String dato) {
        lista.add(dato);
    }
}
