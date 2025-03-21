import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class ReservaTest {
        private Usuario usuario;
        private Libro libro;
        private Reserva reserva;

        @BeforeEach
        public void setUp() {
            usuario = new Usuario("John Doe", "U878");
            libro = new Libro("Java", "Roberto", "92345134665991", 420, "Bueno");
            reserva = new Reserva(usuario, libro);
        }

        @Test
        public void testReservaCreation() {
            assertNotNull(reserva.getId());
            assertEquals(usuario, reserva.getUsuario());
            assertEquals(libro, reserva.getLibro());
            assertEquals(LocalDate.now(), reserva.getFechaReserva());
            assertEquals(LocalDate.now().plusDays(7), reserva.getFechaExpiracion());
            assertTrue(reserva.isActiva());
        }

        @Test
        public void testCancelarReserva() {
            reserva.cancelar();
            assertFalse(reserva.isActiva());
        }

        @Test
        public void testToString() {
            String expected = "Reserva: [id=" + reserva.getId() +
                              ", usuario=" + usuario.getNombre() +
                              ", libro=" + libro.getTitulo() +
                              ", fechaReserva=" + reserva.getFechaReserva() +
                              ", fechaExpiracion=" + reserva.getFechaExpiracion() +
                              ", activa=" + reserva.isActiva() + "]";
            assertEquals(expected, reserva.toString());
        }
}