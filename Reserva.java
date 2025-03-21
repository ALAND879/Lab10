import java.time.LocalDate;

public class Reserva {
    private String id;
    private Usuario usuario;
    private Libro libro;
    private LocalDate fechaReserva;
    private LocalDate fechaExpiracion;
    private boolean activa;
    private static int contadorId = 0;

    public Reserva(Usuario usuario, Libro libro) {
        this.id = generarId();
        this.usuario = usuario;
        this.libro = libro;
        this.fechaReserva = LocalDate.now();
        this.fechaExpiracion = fechaReserva.plusDays(7); // La reserva expira en 7 días
        this.activa = true;
    }

    private static String generarId() {
        contadorId++;
        return "R" + String.format("%04d", contadorId);
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void cancelar() {
        this.activa = false;
    }

    @Override
    public String toString() {
        return "Reserva [id=" + id +
               ", usuario=" + usuario.getNombre() +
               ", libro=" + libro.getTitulo() +
               ", fechaReserva=" + fechaReserva +
               ", fechaExpiracion=" + fechaExpiracion +
               ", activa=" + activa + "]";
    }
}