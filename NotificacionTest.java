import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class NotificacionTest {
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Alan Jimenez", "879");
    }

    @Test
    void testCrearNotificacionValida() {
        Notificacion notificacion = new Notificacion("Tu préstamo vence mañana", usuario, "Vencimiento de prestamo próximo");

        assertEquals("Tu préstamo vence mañana", notificacion.getMensaje());
        assertEquals("Vencimiento de prestamo próximo", notificacion.getTipo());
        assertEquals(usuario, notificacion.getUsuario());
        assertEquals(LocalDate.now(), notificacion.getFechaEnvio());
    }

    @Test
    void testCrearNotificacionConMensajeVacio() {
        Notificacion notificacion = new Notificacion("", usuario, "Prestamo vencido");

        assertEquals("Mensaje vacío", notificacion.getMensaje());
    }

    @Test
    void testCrearNotificacionConTipoInvalido() {
        Notificacion notificacion = new Notificacion("Mensaje de prueba", usuario, "Otro tipo desconocido");

        assertEquals("Notificación", notificacion.getTipo());
    }

    @Test
    void testFechaDeEnvioEsHoy() {
        Notificacion notificacion = new Notificacion("Mensaje de prueba", usuario, "Nuevos libros disponibles");

        assertEquals(LocalDate.now(), notificacion.getFechaEnvio());
    }

    @Test
    void testEnviarNotificacion() {
        Notificacion notificacion = new Notificacion("Recordatorio de devolución", usuario, "Vencimiento de prestamo próximo");

        notificacion.enviarNotificacion();
    }

}
