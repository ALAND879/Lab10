

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

/**
 * Clase de pruebas unitarias para verificar el funcionamiento de la clase Prestamo.
 * Implementa pruebas para todos los métodos principales de la clase Prestamo
 *
 * @author  Roberto Salazar Márquez
 * @version 1.1
 */
public class PrestamoTest
{
    /** Objeto Prestamo para las pruebas*/
    private Prestamo prestamo;
    /** Objeto Usuario para las pruebas */
    private Usuario usuario;
    /** Objeto Libro´para las pruebas*/
    private Libro libro;

    /**
     * Configura el ambiente de pruebas creando instancias nuevas de Usuario,
     * Libro y Prestamo antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        usuario = new Usuario("U001", "Juan Pérez");
        libro = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424922498", 863);
        prestamo = new Prestamo("P001", usuario, libro);
    }
    
    /**
     *  Verifica que el Constructor de un nuevo préstamo funcione correctamente
     *  que todos los atributos se establezcan correctamente.
     */
    @Test
    public void testConstructor() {
        assertEquals("P001", prestamo.getId());
        assertEquals(usuario.getId(), prestamo.getUsuario().getId());
        assertEquals(libro.getIsbn(), prestamo.getLibro().getIsbn());
        assertEquals(prestamo.getFechaPrestamo(), prestamo.getFechaPrestamo());
        assertEquals(Prestamo.ACTIVO, prestamo.getEstado());
    }
    
    /**
     *  Verifica que el registro de un nuevo préstamo funcione correctamente
     *  que todos los atributos se establezcan correctamente.
     */
    @Test
    public void testRegistrarPrestamoExitoso() {
        assertTrue(prestamo.registrarPrestamo());
        assertEquals(Prestamo.ACTIVO, prestamo.getEstado());
    }
    
    /**
     *  Verifica que el registro de un nuevo préstamo funcione correctamente
     *  cuando las condiciones son válidas.
     */
    @Test
    public void testRegistrarPrestamoLibroNoPrestado() {
        libro.prestarLibro(); // Libro ya prestado
        assertFalse(prestamo.registrarPrestamo());
    }
    
    /**
     *  Prueba completa del proceso de devolución verificando el estado del libro,
     *  el préstamo y la imposibilidad de devolver un préstamo ya devuelto.
     */
    @Test
    public void testProcesarDevolucionExitosa() {
        prestamo.registrarPrestamo();
        assertTrue(prestamo.procesarDevolucion());
        assertEquals(Prestamo.DEVUELTO, prestamo.getEstado());
        assertNotNull(prestamo.getFechaDevolucionReal());
    }
    
    /**
     *  Comprueba que el intento de devolución falle cuando el préstamo
     *  no está en estado activo.
     */
    @Test
    public void testProcesarDevolucionPrestamoNoActivo() {
        assertFalse(prestamo.procesarDevolucion());
    }
    
    /**
     *  Prueba completa del proceso de devolución verificando el estado del libro,
     *  el préstamo y la imposibilidad de devolver un préstamo ya devuelto.
     */
    @Test
    void testProcesarDevolucion() {
        prestamo.registrarPrestamo();
        assertTrue(prestamo.procesarDevolucion());
        assertEquals(Prestamo.DEVUELTO, prestamo.getEstado());
        assertNotNull(prestamo.getFechaDevolucionReal());
        assertFalse(libro.isPrestado());
        
        // Intentar devolver un préstamo ya devuelto
        assertFalse(prestamo.procesarDevolucion());
    }
    
    
    /**
     *  Verifica que la extensión del período de préstamo funcione correctamente
     *  para préstamos activos y actualice la fecha de devolución esperada.
     */
    @Test
    public void testExtenderPrestamoExitoso() {
        prestamo.registrarPrestamo();
        LocalDate fechaOriginal = prestamo.getFechaDevolucionEsperada();
        assertTrue(prestamo.extenderPrestamo(7));
        assertEquals(fechaOriginal.plusDays(7), prestamo.getFechaDevolucionEsperada());
    }
    
    
    /**
     *  Prueba que el método toString() genere una representación en cadena
     *  que contenga toda la información relevante del préstamo.
     */
    @Test
    public void testToString() {
        String resultado = prestamo.toString();
        assertTrue(resultado.contains("P001"));
        assertTrue(resultado.contains(usuario.getNombre()));
        assertTrue(resultado.contains(libro.getTitulo()));
        assertTrue(resultado.contains("ACTIVO"));
    }

    /**
     *  Limpia el ambiente de pruebas después de cada test.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
