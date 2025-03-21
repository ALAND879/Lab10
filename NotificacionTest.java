import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class NotificacionTest {
    private Usuario usuario;
    private ManejoNotificaciones manejoNotificaciones;
    private List<Prestamo> prestamos;
    private Libro libro;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Alan Jimenez", "879");
        manejoNotificaciones = new ManejoNotificaciones();
        prestamos = new ArrayList<>();
        libro = new Libro("Java", "Roberto", "92345134665991", 420, "Bueno");
        reserva = new Reserva(usuario, libro);
    }

    @Test
    void testCrearNotificacionValida() {
        Notificacion notificacion = new Notificacion("Tu préstamo vence mañana", usuario, Notificacion.PRESTAMO_PROXIMO_VENCER);

        assertEquals("Tu préstamo vence mañana", notificacion.getMensaje());
        assertEquals(Notificacion.PRESTAMO_PROXIMO_VENCER, notificacion.getTipo());
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
        Notificacion notificacion = new Notificacion("Mensaje de prueba", usuario, "Otro");

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


    @Test
    void testVerificarVencimientosPrestamos() {
        Prestamo prestamoProximoVencer = new Prestamo("P001", usuario, libro);
        prestamoProximoVencer.extenderPrestamo(ManejoNotificaciones.DIAS_ANTICIPACION - 1 - 14);
        prestamos.add(prestamoProximoVencer);

        prestamoProximoVencer.verificarEstado();

        manejoNotificaciones.verificarVencimientosPrestamos(prestamos);

        List<Notificacion> notificaciones = manejoNotificaciones.getNotificaciones();
        assertEquals(1, notificaciones.size());

        Notificacion notificacionProximoVencer = notificaciones.get(0);
        assertEquals(Notificacion.PRESTAMO_PROXIMO_VENCER, notificacionProximoVencer.getTipo());
        assertEquals(
                String.format("Su préstamo del libro '%s' vence en %d días.", libro.getTitulo(), ManejoNotificaciones.DIAS_ANTICIPACION - 1),
                notificacionProximoVencer.getMensaje()
        );
    }


}
