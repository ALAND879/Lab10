
/**
 * La clase Prestamo gestiona las operaciones de préstamo de libros en el sistema de biblioteca. 
 * Mantiene el registro de los préstamos, incluyendo información sobre el usuario, el libro,
 * las fechas relevantes y el estado actual del préstamo.
 * 
 * @author Roberto Salazar Márquez
 * @version 1.1
 */
import java.time.*;

public class Prestamo {
    private String id;
    private Usuario usuario;
    private Libro libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private int estado;

    /** Constante que representa un préstamo activo */
    public static final int ACTIVO = 0;
    /** Constante que representa un préstamo devuelto */
    public static final int DEVUELTO = 1;
    /** Constante que representa un préstamo vencido */
    public static final int VENCIDO = 2;
    
    /**
     * Constructor que inicializa un nuevo préstamo.
     * @param id Identificador único del préstamo
     * @param usuario Usuario que realiza el préstamo
     * @param libro Libro a prestar
     */
    
    public Prestamo(String id, Usuario usuario, Libro libro) {
        /** Identificador único del préstamo */
        this.id = id;
        this.usuario = usuario;
        this.libro = libro;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEsperada = fechaPrestamo.plusDays(14); // 14 días de préstamo por defecto
        this.fechaDevolucionReal = null;
        this.estado = ACTIVO;
    }

    /**
     * Obtiene el identificador del préstamo.
     * @return Identificador único del préstamo
     */
    public String getId() { 
        return id; 
    }

    /**
     * Obtiene una copia del usuario asociado al préstamo.
     * @return Copia del usuario que realizó el préstamo
     */
    public Usuario getUsuario() { 
        return new Usuario(usuario); 
    }
    
    /**
     * Obtiene una copia del libro prestado.
     * @return Copia del libro prestado
     */
    public Libro getLibro() { 
        return new Libro(libro); 
    }
    
    /**
     * Obtiene la fecha en que se realizó el préstamo.
     * @return Fecha del préstamo
     */
    public LocalDate getFechaPrestamo() { 
        return fechaPrestamo; 
    }
    
    /**
     * Obtiene la fecha esperada de devolución.
     * @return Fecha esperada de devolución
     */
    public LocalDate getFechaDevolucionEsperada() { 
        return fechaDevolucionEsperada; 
    }
    
    /**
     * Obtiene la fecha real de devolución.
     * @return Fecha real de devolución, null si aún no se ha devuelto
     */
    public LocalDate getFechaDevolucionReal() { 
        return fechaDevolucionReal; 
    }
    
    /**
     * Obtiene el estado actual del préstamo.
     * @return Estado del préstamo (ACTIVO, DEVUELTO o VENCIDO)
     */
    public int getEstado() { 
        return estado; 
    }
    
    /**
     * Registra un nuevo préstamo si las condiciones son válidas.
     * @return true si el préstamo se registró exitosamente, false en caso contrario
     */
    public boolean registrarPrestamo() {
        if (libro.isPrestado() || usuario.getLibrosPrestados() != null) {
            return false;
        }
        if (usuario.solicitarPrestamo(libro)) {
            estado = ACTIVO;
            return true;
        }
        return false;
    }

    /**
     * Procesa la devolución de un libro prestado.
     * @return true si la devolución se procesó exitosamente, false en caso contrario
     */
    public boolean procesarDevolucion() {
    if (estado == ACTIVO) {
        fechaDevolucionReal = LocalDate.now();
        libro.devolverLibro();  // Primero marcamos el libro como disponible
        if (usuario.devolverLibro(libro)) {  // Pasamos el libro como parámetro
            estado = DEVUELTO;
            return true;
        }
        // Si la devolución falla, revertimos el estado del libro
        libro.prestarLibro();
    }
    return false;
}
    
    /**
     * Verifica y actualiza el estado del préstamo.
     * Si la fecha actual supera la fecha de devolución esperada, 
     * el estado cambia a VENCIDO.
     */
    public void verificarEstado() {
        if (estado == ACTIVO && LocalDate.now().isAfter(fechaDevolucionEsperada)) {
            estado = VENCIDO;
        }
    }

    /**
     * Extiende el período de préstamo por un número específico de días.
     * @param dias Número de días a extender el préstamo
     * @return true si la extensión fue exitosa, false si el préstamo no está activo o está vencido
     */
    public boolean extenderPrestamo(int dias) {
        if (estado == ACTIVO && !LocalDate.now().isAfter(fechaDevolucionEsperada)) {
            fechaDevolucionEsperada = fechaDevolucionEsperada.plusDays(dias);
            return true;
        }
        return false;
    }
    
    /**
     * Retorna una representación en cadena del objeto Prestamo.
     * Incluye información sobre el ID del préstamo, usuario, libro,
     * fechas relevantes y estado actual.
     *
     * @return String con los detalles del préstamo
     */
    public String toString() {
        String estadoStr;
        switch(estado) {
            case ACTIVO:
                estadoStr = "ACTIVO";
                break;
            case DEVUELTO:
                estadoStr = "DEVUELTO";
                break;
            case VENCIDO:
                estadoStr = "VENCIDO";
                break;
            default:
                estadoStr = "DESCONOCIDO";
        }
        
        return "Prestamo [ID=" + id + 
               ", Usuario=" + usuario.getNombre() +
               ", Libro=" + libro.getTitulo() +
               ", Fecha Prestamo=" + fechaPrestamo +
               ", Fecha Devolución Esperada=" + fechaDevolucionEsperada +
               ", Fecha Devolución Real=" + (fechaDevolucionReal != null ? fechaDevolucionReal : "No devuelto") +
               ", Estado=" + estadoStr + "]";
    }
}