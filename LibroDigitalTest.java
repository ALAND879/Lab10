

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas para LibroDigital.
 * 
 * @author Roberto Salazar
 * @version 1.0
 */
public class LibroDigitalTest
{
    private LibroDigital libroDigital1, libroDigital2;
    private Libro libro;

    /**
     * Método que se ejecuta antes de cada prueba.
     * Configura un libro digital de prueba.
     */
    @BeforeEach
    public void setUp() {
        libroDigital1 = new LibroDigital("Java Programming", "John Doe", "ISBN001029425", 425, "PDF", 15.5, "https://biblioteca.com/libro1.pdf");
        libro = new Libro("Cien años de soledad", "Gabriel García Márquez", "9780307474728", 417);
        libroDigital2 = new LibroDigital(libro, "EPUB", 22.6, "https://biblioteca.com/libro1.pdf");
    }
    
    /**
     * Prueba el constructor completo y los getters básicos.
     */
    @Test
    public void testConstructor() {
        assertEquals("PDF", libroDigital1.getFormato());
        assertEquals(15.5, libroDigital1.getTamanoMB(), 0.01);
        assertEquals("https://biblioteca.com/libro1.pdf", libroDigital1.getUrlDescarga());
    }
    
    /**
     * Prueba el constructor con argumento de objeto libro
     */
    @Test
    public void testConstructor2() {
        assertEquals("EPUB", libroDigital2.getFormato());
        assertEquals(22.6, libroDigital2.getTamanoMB(), 0.01);
        assertEquals("Gabriel García Márquez", libroDigital2.getAutor());
    }
    
    /**
     * Prueba el método descargar() cuando hay descargas disponibles.
     */
    @Test
    public void testDescargar() {
        assertTrue(libroDigital1.descargar());
        assertEquals(1, libroDigital1.getDescargasActuales());
    }
    
    /**
     * Prueba el método reiniciarDescargas().
     */
    @Test
    public void testLimiteDescargas() {
        for(int i = 0; i < 3; i++) {
            libroDigital2.descargar();
        }
        assertFalse(libroDigital2.descargar());
        assertEquals(3, libroDigital2.getDescargasActuales());
    }
    
    /**
     * Prueba el método prestarLibro() cuando no hay descargas disponibles.
     */
    @Test
    public void testResetearDescargas() {
        libroDigital2.descargar();
        libroDigital2.reiniciarDescargas();
        assertEquals(0, libroDigital2.getDescargasActuales());
    }
    
}
