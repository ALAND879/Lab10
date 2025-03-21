import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Multa {
    private double montoPago;
    private final LocalDate fechaGeneracion;
    private LocalDate fechaPago;
    private final Usuario usuario;
    private final Libro libro;
    private boolean pagada;

    public Multa(Usuario usuario, Libro libro) {
        this.montoPago = 0.0;
        this.fechaGeneracion = LocalDate.now();
        this.fechaPago = null;
        this.usuario = usuario;
        this.libro = libro;
        this.pagada = false;
    }

    public double calcularMulta(LocalDate fechaDevolucion, double tarifaDiaria) {
        long diasDeRetraso = ChronoUnit.DAYS.between(fechaGeneracion, fechaDevolucion);
        if (diasDeRetraso > 0) {
            return diasDeRetraso * tarifaDiaria;
        }
        return 0.0;
    }

    public void generarRecibo() {
        System.out.println("Recibo de Multa: ");
        System.out.println("Usuario: " + usuario.getNombre());
        System.out.println("Libro: " + libro.getTitulo());
        System.out.println("Fecha de Generación: " + fechaGeneracion);
        System.out.println("Monto: " + montoPago);
        System.out.println("Fecha de Pago: " + (fechaPago != null ? fechaPago : "Pendiente"));
    }

    public void pagarMulta(LocalDate fechaPago) {
        this.montoPago = calcularMulta(fechaPago, 25.0);
        System.out.println("Multa a pagar: " + montoPago);
        this.fechaPago = fechaPago;
        pagada = true;
    }

    // Getters
    public boolean isPagada() {
        return pagada;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public double getMontoPago() {
        return montoPago;
    }
}
