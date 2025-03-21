
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

/**
 * Clase de pruebas unitarias para la clase Libro.
 * Implementa pruebas exhaustivas para todos los métodos y constructores de la clase Libro.
 * 
 * @author Roberto SALAZAR MARQUEZ
 * @version 1.0
 */
public class LibroTest
{
    private Libro libro1, libro2, libro3, libro4, libro5;
    
    /**
     * Configura el entorno de prueba.
     * Se ejecuta antes de cada método de prueba para garantizar un estado inicial consistente.
     * Inicializa cuatro libros con diferentes datos para las pruebas.
     */
    @BeforeEach
    public void setUp()
    {
        libro1 = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424922498", 863);
        libro2 = new Libro("Cien años de soledad", "Gabriel García Márquez", "9780307474728", 417);
        libro3 = new Libro("El Principito", "Antoine de Saint-Exupéry", "9788498381498", 96);
        libro4 = new Libro("1984", "George Orwell", "9788499890944", 326);
    }
    
    /**
     * Prueba el constructor por defecto de Libro.
     * Verifica que se cree un libro con valores predeterminados correctos.
     */
    @Test
    public void testConstructorDefault()
    {
        libro5 = new Libro();
        assertEquals("Libro: Sin título por Desconocido\nISBN: 0000000000000\nPáginas: 0\nEstado: Disponible", libro5.toString());
    }
    
    /**
     * Prueba el constructor base que acepta título y autor.
     * Verifica la correcta inicialización con valores parciales.
     */
    @Test
    public void testConstructorBase()
    {
        libro5 = new Libro("La Odisea", "Homero");
        assertEquals("Libro: La Odisea por Homero\nISBN: 0000000000000\nPáginas: 0\nEstado: Disponible", libro5.toString());
    }
    
    /**
     * Prueba el constructor completo con todos los parámetros.
     * Verifica que todos los atributos se inicialicen correctamente.
     */
    @Test
    public void testConstructorCompleto()
    {
        assertEquals("Libro: Don Quijote de la Mancha por Miguel de Cervantes\nISBN: 9788424922498\nPáginas: 863\nEstado: Disponible", libro1.toString());
    }
    
    /**
     * Prueba el constructor de copia.
     * Verifica que se realice una copia exacta de otro libro.
     */
    @Test
    public void testConstructorCopia()
    {
        libro5 = new Libro(libro1);
        assertEquals("Libro: Don Quijote de la Mancha por Miguel de Cervantes\nISBN: 9788424922498\nPáginas: 863\nEstado: Disponible", libro1.toString());
    }
    
    /**
     * Prueba los métodos setters de la clase Libro.
     * Verifica que cada setter modifique correctamente su atributo correspondiente.
     */
    @Test
    public void testSetters() 
    {
        libro5 = new Libro();
        libro5.setTitulo("El Arte de la Guerra");
        libro5.setAutor("Szun Tzu");
        libro5.setIsbn("8365957639001");
        libro5.setNumPaginas(211);
        assertEquals("Libro: El Arte de la Guerra por Szun Tzu\nISBN: 8365957639001\nPáginas: 211\nEstado: Disponible", libro5.toString());
    }
    
    /**
     * Prueba los métodos getters de la clase Libro.
     * Verifica que cada getter retorne el valor correcto de su atributo.
     */
    @Test
    public void testGetters()
    {
        assertEquals("El Principito", libro3.getTitulo());
        assertEquals("Antoine de Saint-Exupéry", libro3.getAutor());
        assertEquals("9788498381498", libro3.getIsbn());
        assertEquals(96, libro3.getNumPaginas());
    }
    
    /**
     * Primera prueba de funcionalidad de préstamos.
     * Verifica el estado de préstamo de varios libros después de diferentes operaciones.
     */
    @Test
    public void testPrestamos01() 
    {
        libro1.prestarLibro();
        libro3.setPrestado(false);
        assertEquals(true,libro1.isPrestado());
        assertEquals(false,libro2.isPrestado());
        assertEquals(false,libro3.isPrestado());
        assertEquals(false,libro4.isPrestado());
        
    }
    
    /**
     * Segunda prueba de funcionalidad de préstamos.
     * Prueba el ciclo completo de préstamo y devolución de un libro.
     */
    @Test
    public void testPrestamos02() 
    {
        libro1.prestarLibro();
        assertEquals(true,libro1.isPrestado());
        libro1.devolverLibro();
        assertEquals(false,libro1.isPrestado());
        
    }

    @Test
    public void testAgregarReserva() {
        libro1.prestarLibro();  // El libro debe estar prestado para poder reservarlo
        Usuario usuario = new Usuario("Ana López", "U001");
        Reserva reserva = new Reserva(usuario, libro1);

        assertTrue(libro1.agregarReserva(reserva));
        assertEquals(1, libro1.getReservas().size());
    }

    @Test
    public void testAgregarReservaCuandoLibroDisponible() {
        Usuario usuario = new Usuario("Ana López", "U001");
        Reserva reserva = new Reserva(usuario, libro1);

        assertFalse(libro1.agregarReserva(reserva));
        assertEquals(0, libro1.getReservas().size());
    }

    @Test
    public void testMultiplesReservas() {
        libro1.prestarLibro();
        Usuario usuario1 = new Usuario("Ana López", "U001");
        Usuario usuario2 = new Usuario("Juan Pérez", "U002");
        Reserva reserva1 = new Reserva(usuario1, libro1);
        Reserva reserva2 = new Reserva(usuario2, libro1);

        libro1.agregarReserva(reserva1);
        libro1.agregarReserva(reserva2);

        assertEquals(2, libro1.getReservas().size());
        assertEquals(reserva1, libro1.obtenerSiguienteReserva());
    }

    @Test
    public void testTieneReservas() {
        libro1.prestarLibro();
        Usuario usuario = new Usuario("Ana López", "U001");
        Reserva reserva = new Reserva(usuario, libro1);

        assertFalse(libro1.tieneReservas());
        libro1.agregarReserva(reserva);
        assertTrue(libro1.tieneReservas());
    }

    @Test
    public void testObtenerSiguienteReserva() {
        libro1.prestarLibro();
        Usuario usuario = new Usuario("Ana López", "U001");
        Reserva reserva = new Reserva(usuario, libro1);

        assertNull(libro1.obtenerSiguienteReserva());
        libro1.agregarReserva(reserva);
        assertEquals(reserva, libro1.obtenerSiguienteReserva());
    }

    @Test
    public void testGeneroDefault() {
        Libro libro = new Libro();
        assertEquals(GeneroLiterario.getDefault(), libro.getGenero());
    }

    @Test
    public void testAsignarGenero() {
        Libro libro = new Libro();
        libro.setGenero(GeneroLiterario.FICCION);
        assertEquals(GeneroLiterario.FICCION, libro.getGenero());
    }

    @Test
    public void testConstructorConGenero() {
        Libro libro = new Libro("El Hobbit", "J.R.R. Tolkien", "9780261102217", 310, GeneroLiterario.FICCION);
        assertEquals(GeneroLiterario.FICCION, libro.getGenero());
    }

    @Test
    public void testGeneroNoNulo() {
        Libro libro = new Libro();
        libro.setGenero(null);
        assertEquals(GeneroLiterario.getDefault(), libro.getGenero());
    }

    @Test
    public void testAgregarMantenimiento() {
        libro1.agregarMantenimiento(LocalDate.of(2023, 10, 1));
        assertEquals(1, libro1.getMantenimientos().size());
        assertEquals("Bueno", libro1.getEstado());
    }

    @Test
    public void testIncrementarPrestamos() {
        libro1.incrementarPrestamos();
        assertEquals(1, libro1.getContadorPrestamos());
    }

    @Test
    public void testVerificarDisponibilidad() {
        assertTrue(libro1.verificarDisponibilidad());
        libro1.prestarLibro();
        assertFalse(libro1.verificarDisponibilidad());
    }

    @Test
    public void testLibroEnMalEstado() {
        libro5 = new Libro("El Arte de la Guerra", "Szun Tzu", "8365957639001", 211, "Dañado");
        assertEquals("Dañado", libro5.getEstado());
    }

    @Test
    public void testSetEstado() {
        libro1.setEstado("Irreparable");
        assertEquals("Irreparable", libro1.getEstado());
        libro1.setEstado("Invalido");
        assertEquals("Bueno", libro1.getEstado()); // Estado inválido debe establecerse a "Bueno"
    }

    /**
     * Limpia el entorno después de cada prueba.
     * Libera las referencias a los objetos utilizados en las pruebas.
     */
    @AfterEach
    public void tearDown()
    {
        libro1 = libro2 = libro3 = libro4 = libro5 = null;
    }

}




