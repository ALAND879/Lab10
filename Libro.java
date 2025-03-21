import java.time.LocalDate;
import java.util.Vector;
import java.util.*;
/**
 * La clase Libro representa un libro en un sistema de gestión bibliotecaria.
 * Permite manejar y organizar la información básica de los libros, incluyendo
 * su título, autor, ISBN, número de páginas y estado de préstamo.
 * 
 * Esta clase proporciona funcionalidades para:
 * - Gestionar la información básica del libro
 * - Controlar el estado de préstamo
 * - Obtener información detallada del libro
 * 
 * @author Roberto SALAZAR MARQUEZ
 * @version 1.0
 */
public class Libro
{
   private String titulo;
   private String autor;
   private String isbn;
   private int numPaginas;
   private boolean prestado;
   private String estado;  // Bueno, Dañado, Irreparable
   private Vector<LocalDate> mantenimientos;
   private int contadorPrestamos = 0;
    private Queue<Reserva> reservas;
    private GeneroLiterario genero;

    /**
     * Constructor por defecto que inicializa un libro con valores predeterminados.
     * El título se establece como "Sin título", el autor como "Desconocido",
     * el ISBN como "0000000000000", número de páginas en 0 y el estado como no prestado.
     */
    public Libro() {
        this.titulo = "Sin título";
        this.autor = "Desconocido";
        this.isbn = "0000000000000";
        this.numPaginas = 0;
        this.prestado = false;
        this.estado = "Bueno";
        this.mantenimientos = new Vector<LocalDate>();
        this.reservas = new LinkedList<>();
        this.genero = GeneroLiterario.getDefault();
    }   
    
    /**
     * Constructor que inicializa un libro con título y autor.
     * Los demás atributos se inicializan con valores predeterminados.
     * 
     * @param titulo El título del libro
     * @param autor El autor del libro
     */
    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = "0000000000000";
        this.numPaginas = 0;
        this.prestado = false;
        this.estado = "Bueno";
        this.mantenimientos = new Vector<LocalDate>();
        this.reservas = new LinkedList<>();
        this.genero = GeneroLiterario.getDefault();
    }
    
    /**
     * Constructor que inicializa un libro con todos sus atributos.
     * 
     * @param titulo El título del libro
     * @param autor El autor del libro
     * @param isbn El ISBN del libro
     * @param numPaginas El número de páginas del libro
     */
    public Libro(String titulo, String autor, String isbn, int numPaginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.numPaginas = numPaginas;
        this.prestado = false;
        this.estado = "Bueno";
        this.mantenimientos = new Vector<LocalDate>();
        this.reservas = new LinkedList<>();
        this.genero = GeneroLiterario.getDefault();
    }

    public Libro(String titulo, String autor, String isbn, int numPaginas, GeneroLiterario genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.numPaginas = numPaginas;
        this.prestado = false;
        this.estado = "Bueno";
        this.mantenimientos = new Vector<LocalDate>();
        this.reservas = new LinkedList<>();
        this.genero = genero != null ? genero : GeneroLiterario.getDefault();
    }

    /**
     * Constructor de copia que crea un nuevo libro a partir de otro existente.
     * Crea una copia independiente del libro original, manteniendo todos sus datos
     * excepto el estado de préstamo que se inicializa como no prestado.
     * 
     * @param otroLibro El libro del cual se copiarán los datos
     */
    public Libro(Libro otroLibro) {
        this.titulo = otroLibro.titulo;
        this.autor = otroLibro.autor;
        this.isbn = otroLibro.isbn;
        this.numPaginas = otroLibro.numPaginas;
        this.prestado = false;  // El nuevo libro siempre inicia como no prestado
        this.estado = "Bueno";
        this.mantenimientos = new Vector<LocalDate>();
        this.reservas = new LinkedList<>();
        this.genero = GeneroLiterario.getDefault();
    }

    // En caso de que el libro no se reciba en buen estado
    public Libro(String titulo, String autor, String isbn, int numPaginas, String estado) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.numPaginas = numPaginas;
        this.prestado = false;
        setEstado(estado);
        this.mantenimientos = new Vector<LocalDate>();
        this.reservas = new LinkedList<>();
        this.genero = GeneroLiterario.getDefault();
    }
    
    /**
     * Obtiene el título del libro.
     * @return El título del libro
     */
    public String getTitulo() {
        return titulo;
    }
    
    /**
     * Establece el título del libro.
     * Valida que el autor no sea null ni esté vacío.
     * @param titulo El nuevo titulo del libro
     */
    public void setTitulo(String titulo) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            this.titulo = titulo;
        }
    }
    
    /**
     * Obtiene el autor del libro.
     * @return El nombre del autor del libro
     */
    public String getAutor() {
        return autor;
    }
    
    /**
     * Establece el autor del libro.
     * Valida que el autor no sea null ni esté vacío.
     * @param autor El nuevo autor del libro
     */
    public void setAutor(String autor) {
        if (autor != null && !autor.trim().isEmpty()) {
            this.autor = autor;
        }
    }
    
    /**
     * Obtiene el ISBN del libro.
     * @return El ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }
    
    /**
     * Establece el ISBN del libro.
     * Valida que el ISBN tenga el formato correcto (13 dígitos).
     * @param isbn El nuevo ISBN del libro
     */
    public void setIsbn(String isbn) {
        if (isbn != null && isbn.matches("\\d{13}")) {
            this.isbn = isbn;
        }
    }
    
    /**
     * Obtiene el número de páginas del libro.
     * @return El número de páginas del libro
     */
    public int getNumPaginas() {
        return numPaginas;
    }
    
    /**
     * Establece el número de páginas del libro.
     * Valida que el número de páginas sea mayor que cero.
     * @param numPaginas El nuevo número de páginas del libro
     */
    public void setNumPaginas(int numPaginas) {
        if (numPaginas > 0) {
            this.numPaginas = numPaginas;
        }
    }

    public void setEstado(String estado) {
        if (estado.equals("Bueno") || estado.equals("Dañado") || estado.equals("Irreparable")) {
            this.estado = estado;
        } else {
            this.estado = "Bueno";
        }
    }

    public String getEstado() {
        return estado;
    }

    /**
     * Obtiene el estado de préstamo del libro.
     * @return true si el libro está prestado, false si está disponible
     */
    public boolean isPrestado() {
        return prestado;
    }
    
    /**
     * Establece el estado de préstamo del libro.
     * @param prestado El nuevo estado de préstamo del libro
     */
    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }
    
    /**
     * Intenta prestar el libro si está disponible.
     * Este método verifica si el libro puede ser prestado y actualiza su estado.
     * 
     * @return true si el préstamo fue exitoso (el libro estaba disponible),
     *         false si el libro ya estaba prestado
     */
    public boolean prestarLibro() {
        if (!prestado) {
            prestado = true;
            return true;
        }
        return false;
    }
    
    /**
     * Devuelve el libro a la biblioteca.
     * Este método marca el libro como no prestado, haciéndolo disponible
     * para futuros préstamos.
     * 
     * No requiere validaciones ya que un libro siempre puede ser devuelto
     * independientemente de su estado actual.
     */
    public void devolverLibro() {
        prestado = false;
    }
    
    /**
     * Intenta prestar el libro si está disponible.
     * Este método verifica si el libro puede ser prestado y actualiza su estado.
     * 
     * @return true si el préstamo fue exitoso (el libro estaba disponible),
     *         false si el libro ya estaba prestado
     */
    public String toString() {
        return "Libro: " + titulo + " por " + autor +
               "\nISBN: " + isbn +
               "\nPáginas: " + numPaginas +
               "\nEstado: " + (prestado ? "Prestado" : "Disponible");
    }
    
    /**
     * Verifica si el libro está disponible para préstamo.
     * Un libro está disponible cuando no está prestado actualmente.
     * 
     * @return true si el libro está disponible para préstamo,
     *         false si el libro ya está prestado
     */
    public boolean verificarDisponibilidad() {
        return !prestado;
    }

    public void agregarMantenimiento(LocalDate fecha) {
        mantenimientos.add(fecha != null ? fecha : LocalDate.now());
        if (!estado.equals("Irreparable")) {
            estado = "Bueno";
        }
    }


    public Vector<LocalDate> getMantenimientos() {
        return mantenimientos;
    }

    public void incrementarPrestamos() {
        contadorPrestamos++;
    }

    public int getContadorPrestamos() {
        return contadorPrestamos;
    }

    public boolean agregarReserva(Reserva reserva) {
        if (reserva == null || reservas.contains(reserva) || !prestado) {
            return false;
        }
        return reservas.offer(reserva);
    }

    public Reserva obtenerSiguienteReserva() {
        return reservas.peek();
    }

    public boolean tieneReservas() {
        return !reservas.isEmpty();
    }

    public Queue<Reserva> getReservas() {
        return new LinkedList<>(reservas);
    }

    public GeneroLiterario getGenero() {
        return genero;
    }

    public void setGenero(GeneroLiterario genero) {
        this.genero = genero != null ? genero : GeneroLiterario.getDefault();
    }

}
