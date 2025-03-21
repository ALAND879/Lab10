public class LibroAcademico extends Libro {
    private String disciplina;
    private String nivel;
    private boolean tieneEjercicios;
    private String edicion;

    public LibroAcademico(String titulo, String autor, String isbn, int numPaginas,
                         String disciplina, String nivel) {
        super(titulo, autor, isbn, numPaginas);
        this.disciplina = disciplina;
        this.nivel = nivel;
        this.tieneEjercicios = false;
        this.edicion = "Primera";
    }

    public void setTieneEjercicios(boolean tieneEjercicios) {
        this.tieneEjercicios = tieneEjercicios;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    @Override
    public String toString() {
        return super.toString() +
               "\nDisciplina: " + disciplina +
               "\nNivel: " + nivel +
               "\nEdición: " + edicion +
               "\nTiene ejercicios: " + tieneEjercicios;
    }
}