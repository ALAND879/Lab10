/**
 * Clase Usuario - Representa un usuario del sistema de biblioteca
 * 
 * Esta clase maneja las operaciones relacionadas con los usuarios de la biblioteca,
 * incluyendo el préstamo y devolución de libros.
 * 
 * @author Roberto SALAZAR MARQUEZ
 * @version 1.1
 */
import java.util.*;

public class Usuario extends Persona {
    private List<Libro> librosPrestados;
    private Set<String> historialPrestamos;
    private Vector<String> generosFavoritos;
    private int limitePrestamos;
    private int prestamosRealizados;
    private int categoria;


    public static final int USUARIO_REGULAR = 0;
    public static final int USUARIO_PROFESOR = 1;
    public static final int USUARIO_INVESTIGADOR = 2;


    /**
     * Constructor para objetos de la clase Usuario
     * 
     * @param id El identificador único del usuario
     * @param nombre El nombre completo del usuario
     */
    public Usuario(String nombre, String id, int categoria) {
        super(nombre, id);
        this.librosPrestados = new ArrayList<>();
        this.historialPrestamos = new HashSet<>();
        this.generosFavoritos = new Vector<String>();
        this.categoria = categoria;
        setLimitesPorCategoria();
        this.prestamosRealizados = 0;
    }


    public Usuario(String nombre, String id) {
        super(nombre, id);
        this.librosPrestados = new ArrayList<>();
        this.historialPrestamos = new HashSet<>();
        this.generosFavoritos = new Vector<String>();
        this.limitePrestamos = 3;
        this.prestamosRealizados = 0;
    }
    
    /**
     * Constructor para objetos de la clase Usuario
     * 
     * @param otro Manejador al objeto  usuario
     */
    public Usuario(Usuario usuario) {
        super(usuario.getNombre(), usuario.getId());
        this.librosPrestados = usuario.getLibrosPrestado();
        this.generosFavoritos = new Vector<>(usuario.getGenerosFavoritos());
        this.limitePrestamos = usuario.limitePrestamos;
        this.prestamosRealizados = usuario.prestamosRealizados;
    }
    
    
    /**
     * Solicita el préstamo de un libro
     * 
     * @param libro El libro que se desea pedir prestado
     * @return true si el préstamo fue exitoso, false en caso contrario
     */
    public boolean solicitarPrestamo(Libro libro) {
        if (prestamosRealizados < limitePrestamos && !libro.isPrestado() && libro.prestarLibro()) {
            librosPrestados.add(libro);
            historialPrestamos.add(libro.getIsbn());
            prestamosRealizados++;
            return true;
        }
        return false;
    }
    
    
    /**
     * Devuelve el libro actualmente prestado
     * 
     * @return true si la devolución fue exitosa, false si no hay libro prestado
     */
    public boolean devolverLibroUsr(Libro libro) {
        if (librosPrestados.contains(libro)) {
            libro.devolverLibro();
            librosPrestados.remove(libro);
            prestamosRealizados--;
            return true;
        }
        return false;
    }
    
    /**
     * Deprecado, para eliminación: Este elemento de la API será eliminado en una versión futura.
     * Obtiene una copia del libro prestado actualmente 
     * 
     * @return Una copia del libro prestado o null si no hay préstamos activos
     */

    public Libro getLibrosPrestados() {
        if(librosPrestados.size() > 0 )
            return new Libro(librosPrestados.get(0)); // Retorna una copia de la lista
        else
            return null;
    }
    
    /**
     * Obtiene una copia de la lista de libros prestados.
     * @return una nueva ArrayList conteniendo los libros prestados
     */
    public List<Libro> getLibrosPrestado() {
        return new ArrayList<>(librosPrestados);
    }
    
    /**
     * Retorna el tipo específico de esta clase.
     * Este método sobreescribe el método obtenerTipo() de la clase padre Persona
     * para proporcionar una identificación específica para los objetos Usuario.
     * 
     * @return "Usuario" - Una cadena que identifica esta clase como tipo Usuario
     */
    public String obtenerTipo() {
        return "Usuario";
    }
    
    /**
     * Genera una representación en texto del usuario
     * 
     * @return String con la información del usuario y su préstamo actual
     */
    public String toString() {
        String cad = "ID: " + getId() + ", " + "Nombre: " + getNombre() + ". ";
        if (librosPrestados.size() > 0)
            cad += "Tiene en préstamo" + librosPrestados.toString() +  " libros.";
        else
            cad += "No tiene en préstamo un libro.";
        return cad;
    }

    public void agregarGeneroFavorito(String genero) {
        if (!generosFavoritos.contains(genero)) {
            generosFavoritos.add(genero);
        }
    }

    public void eliminarGeneroFavorito(String genero) {
        if (generosFavoritos.contains(genero)) {
            generosFavoritos.remove(genero);
        }
    }

    public Vector<String> getGenerosFavoritos() {
        return generosFavoritos;
    }

    public int getLimitePrestamos() {
        return limitePrestamos;
    }

    public void setLimitePrestamos(int limitePrestamos) {
        this.limitePrestamos = limitePrestamos <= 0 ? 3 : limitePrestamos;
    }

    public int getPrestamosRealizados() {
        return prestamosRealizados;
    }

    public int getCategoria() {
        return this.categoria;
    }

    public void eliminarPrestamo(Prestamo prestamo) {
        prestamosRealizados -= 1; // Asumiendo que `prestamosRealizados` es una lista de préstamos del usuario
    }

    private void setLimitesPorCategoria() {
        switch (categoria) {
            case USUARIO_REGULAR:
                this.limitePrestamos = 3;
                break;
            case USUARIO_PROFESOR:
                this.limitePrestamos = 5;
                break;
            case USUARIO_INVESTIGADOR:
                this.limitePrestamos = 10;
                break;
            default:
                this.limitePrestamos = 3;
        }
    }
}