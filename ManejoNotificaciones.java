import java.time.temporal.ChronoUnit;
    import java.util.*;
    import java.time.*;

    public class ManejoNotificaciones {
        private List<Notificacion> notificaciones;
        public static final int DIAS_ANTICIPACION = 3;

        public ManejoNotificaciones() {
            this.notificaciones = new ArrayList<>();
        }

        public void verificarVencimientosPrestamos(List<Prestamo> prestamos) {
            LocalDate hoy = LocalDate.now();

            for (Prestamo prestamo : prestamos) {
                if (prestamo.getEstado() == Prestamo.ACTIVO) {
                    LocalDate fechaDevolucion = prestamo.getFechaDevolucionEsperada();
                    long diasRestantes = ChronoUnit.DAYS.between(hoy, fechaDevolucion);

                    if (diasRestantes <= DIAS_ANTICIPACION && diasRestantes > 0) {
                        crearNotificacionVencimientoProximo(prestamo);
                    } else if (diasRestantes < 0) {
                        crearNotificacionVencido(prestamo);
                    }
                }
            }
        }

        public List<Notificacion> getNotificaciones() {
            return notificaciones;
        }

        public void verificarReservasDisponibles(Libro libro) {
            if (!libro.isPrestado() && libro.tieneReservas()) {
                Reserva reserva = libro.obtenerSiguienteReserva();
                if (reserva != null && reserva.isActiva()) {
                    crearNotificacionReservaDisponible(reserva);
                }
            }
        }

        private void crearNotificacionVencimientoProximo(Prestamo prestamo) {
            String mensaje = String.format("Su préstamo del libro '%s' vence en %d días.",
                    prestamo.getLibro().getTitulo(),
                    ChronoUnit.DAYS.between(LocalDate.now(), prestamo.getFechaDevolucionEsperada()));

            Notificacion notificacion = new Notificacion(
                    mensaje,
                    prestamo.getUsuario(),
                    Notificacion.PRESTAMO_PROXIMO_VENCER
            );
            notificaciones.add(notificacion);
            notificacion.enviarNotificacion();
        }

        private void crearNotificacionVencido(Prestamo prestamo) {
            String mensaje = String.format("Su préstamo del libro '%s' está vencido por %d días.",
                    prestamo.getLibro().getTitulo(),
                    ChronoUnit.DAYS.between(prestamo.getFechaDevolucionEsperada(), LocalDate.now()));

            Notificacion notificacion = new Notificacion(
                    mensaje,
                    prestamo.getUsuario(),
                    Notificacion.PRESTAMO_VENCIDO
            );
            notificaciones.add(notificacion);
            notificacion.enviarNotificacion();
        }

        private void crearNotificacionReservaDisponible(Reserva reserva) {
            String mensaje = String.format("El libro '%s' que reservó ya está disponible.",
                    reserva.getLibro().getTitulo());

            Notificacion notificacion = new Notificacion(
                    mensaje,
                    reserva.getUsuario(),
                    Notificacion.RESERVA_DISPONIBLE
            );
            notificaciones.add(notificacion);
            notificacion.enviarNotificacion();
        }
    }