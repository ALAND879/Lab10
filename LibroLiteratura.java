public class LibroLiteratura extends Libro {
    private String genero;
    private String movimientoLiterario;
    private String idioma;
    private boolean esTraduccion;

    public LibroLiteratura(String titulo, String autor, String isbn, int numPaginas,
                          String genero, String movimientoLiterario) {
        super(titulo, autor, isbn, numPaginas);
        this.genero = genero;
        this.movimientoLiterario = movimientoLiterario;
        this.idioma = "Español";
        this.esTraduccion = false;
    }

    public void marcarComoTraduccion(String idiomaOriginal) {
        this.esTraduccion = true;
        this.idioma = idiomaOriginal;
    }

    @Override
    public String toString() {
        return super.toString() +
               "\nGénero: " + genero +
               "\nMovimiento literario: " + movimientoLiterario +
               "\nIdioma: " + idioma +
               (esTraduccion ? " (Traducción)" : " (Original)");
    }
}