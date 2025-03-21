
/**
 * Clase que representa un libro en formato digital, heredando de la clase Libro.
 * Esta clase añade funcionalidades específicas para libros digitales como formato,
 * tamaño del archivo, URL de descarga y control de descargas.
 * 
 * @author Roberto Salazar
 * @version 1.0
 */
public class LibroDigital extends Libro {
    /** Formato del archivo (PDF, EPUB, MOBI, etc.) */
    private String formato; 
    /** Tamaño del archivo en megabytes */
    private double tamanoMB;
    /** URL donde está disponible el archivo para descarga */
    private String urlDescarga;
    /** Número máximo de descargas permitidas */
    private int descargasPermitidas;
    /** Contador de descargas realizadas */
    private int descargasActuales;
    
    /**
     * Constructor que crea un nuevo libro digital con todos sus atributos.
     * 
     * @param titulo Título del libro
     * @param autor Autor del libro
     * @param isbn ISBN del libro
     * @param numPaginas Número de páginas del libro
     * @param formato Formato del archivo digital
     * @param tamanoMB Tamaño del archivo en megabytes
     * @param urlDescarga URL para descargar el libro
     */
    public LibroDigital(String titulo, String autor, String isbn, int numPaginas, 
                       String formato, double tamanoMB, String urlDescarga) {
        super(titulo, autor, isbn, numPaginas);
        this.formato = formato;
        this.tamanoMB = tamanoMB;
        this.urlDescarga = urlDescarga;
        this.descargasPermitidas = 3; // Valor por defecto
        this.descargasActuales = 0;
    }
    
    /**
     * Constructor que crea un libro digital a partir de un libro existente.
     * 
     * @param libro Libro base del cual se creará la versión digital
     * @param formato Formato del archivo digital
     * @param tamanoMB Tamaño del archivo en megabytes
     * @param urlDescarga URL para descargar el libro
     */
    public LibroDigital(Libro libro, String formato, double tamanoMB, String urlDescarga) {
        super(libro);
        this.formato = formato;
        this.tamanoMB = tamanoMB;
        this.urlDescarga = urlDescarga;
        this.descargasPermitidas = 3; // Valor por defecto
        this.descargasActuales = 0;
    }
    
    /**
     * Obtiene el formato del archivo digital.
     * 
     * @return formato del archivo
     */
    public String getFormato() { 
        return formato; 
    }
    
    /**
     * Obtiene el tamaño del archivo en megabytes.
     * 
     * @return tamaño del archivo en MB
     */
    public double getTamanoMB() { 
        return tamanoMB; 
    
    }
    
    /**
     * Obtiene la URL de descarga del libro.
     * 
     * @return URL de descarga
     */
    public String getUrlDescarga() {
        return urlDescarga; 
    }
    
    /**
     * Obtiene el número máximo de descargas permitidas.
     * 
     * @return número máximo de descargas permitidas
     */
    public int getDescargasPermitidas() { 
        return descargasPermitidas; 
    }
    
    /**
     * Obtiene el número actual de descargas realizadas.
     * 
     * @return número de descargas actuales
     */
    public int getDescargasActuales() { 
        return descargasActuales; 
    }
    
    /**
     * Intenta realizar una descarga del libro digital.
     * 
     * @return true si la descarga fue exitosa, false si se alcanzó el límite
     */
    public boolean descargar() {
        if (descargasActuales < descargasPermitidas) {
            descargasActuales++;
            return true;
        }
        return false;
    }
    
    /**
     * Reinicia el contador de descargas a cero.
     */
    public void reiniciarDescargas() {
        descargasActuales = 0;
    }
 
    /**
     * Sobrescribe el método prestarLibro de la clase padre.
     * Verifica si hay descargas disponibles antes de realizar el préstamo.
     * 
     * @return true si el préstamo fue exitoso, false en caso contrario
     */
    public boolean prestarLibro() {
        if (descargasActuales < descargasPermitidas) {
            return super.prestarLibro();
        }
        return false;
    }
    
    /**
     * Genera una representación en texto del libro digital.
     * Incluye toda la información del libro más los detalles específicos
     * del formato digital.
     * 
     * @return String con la información completa del libro digital
     */
    public String toString() {
        return super.toString() + 
               "\nFormato: " + formato +
               "\nTamaño: " + tamanoMB + " MB" +
               "\nURL de descarga: " + urlDescarga +
               "\nDescargas realizadas: " + descargasActuales + "/" + descargasPermitidas;
    }
}