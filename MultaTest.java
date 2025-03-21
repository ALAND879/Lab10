
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class MultaTest {
    private Multa multa;
    private Usuario usuario;
    private Libro libro;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Juan Perez", "123456");
        libro = new Libro("Java Programming", "Roberto Salazar");
        multa = new Multa(usuario, libro);
    }

    @Test
    void testCalcularMultaSinRetraso() {
        //La multa debe ser 0 si no hay retraso
        double monto = multa.calcularMulta(multa.getFechaGeneracion(), 25.0);
        assertEquals(0.0, monto);
    }

    @Test
    void testCalcularMultaConRetraso() {
        //La multa debe ser correcta para 5 días de retraso a 25 por día
        LocalDate fechaDevolucion = multa.getFechaGeneracion().plusDays(5);
        double monto = multa.calcularMulta(fechaDevolucion, 25.0);
        assertEquals(125.0, monto);
    }

    @Test
    void testPagarMulta() {
        //La multa debe ser pagada correctamente, con un monto de 75 para 3 días de retraso
        LocalDate fechaPago = multa.getFechaGeneracion().plusDays(3);
        multa.pagarMulta(fechaPago);
        assertTrue(multa.isPagada());
        assertEquals(75.0, multa.getMontoPago());
    }
}