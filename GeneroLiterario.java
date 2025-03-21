public class GeneroLiterario {
    private final String nombre;

    public static final GeneroLiterario FICCION = new GeneroLiterario("Ficción");
    public static final GeneroLiterario NO_FICCION = new GeneroLiterario("No Ficción");
    public static final GeneroLiterario POESIA = new GeneroLiterario("Poesía");
    public static final GeneroLiterario DRAMA = new GeneroLiterario("Drama");
    public static final GeneroLiterario OTRO = new GeneroLiterario("Otro");

    private GeneroLiterario(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static GeneroLiterario getDefault() {
        return OTRO;
    }

    public String toString() {
        return nombre;
    }
}