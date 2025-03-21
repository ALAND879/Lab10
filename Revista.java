import java.util.*;

public class Revista extends Libro {
    private String periodicidad;
    private int volumen;
    private int numero;
    private String issn;
    private List<String> categorias;

    public Revista(String titulo, String autor, String isbn, int numPaginas,
                  String periodicidad, int volumen, int numero) {
        super(titulo, autor, isbn, numPaginas);
        this.periodicidad = periodicidad;
        this.volumen = volumen;
        this.numero = numero;
        this.issn = "";
        this.categorias = new ArrayList<>();
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public void agregarCategoria(String categoria) {
        if (!categorias.contains(categoria)) {
            categorias.add(categoria);
        }
    }

    public List<String> getCategorias() {
        return new ArrayList<>(categorias);
    }

    @Override
    public String toString() {
        return super.toString() +
               "\nPeriodicidad: " + periodicidad +
               "\nVolumen: " + volumen +
               "\nNúmero: " + numero +
               "\nISSN: " + issn +
               "\nCategorías: " + String.join(", ", categorias);
    }
}