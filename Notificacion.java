import java.time.LocalDate;

public class Notificacion {
    private String mensaje;
    private LocalDate fechaEnvio;
    private Usuario usuario;
    private String tipo;

    public static final String PRESTAMO_PROXIMO_VENCER = "Préstamo próximo a vencer";
    public static final String PRESTAMO_VENCIDO = "Préstamo vencido";
    public static final String RESERVA_DISPONIBLE = "Reserva disponible";

    public Notificacion(String mensaje, Usuario usuario, String tipo) {
        this.mensaje = validarMensaje(mensaje);
        this.fechaEnvio = LocalDate.now();
        this.usuario = usuario;
        this.tipo = validarTipo(tipo);
    }

    private String validarMensaje(String texto) {
        return (texto == null || texto.isEmpty()) ? "Mensaje vacío" : texto;
    }

    private String validarTipo(String type) {
        if (!type.equals("Vencimiento de prestamo próximo") && !type.equals("Prestamo vencido") && !type.equals("Nuevos libros disponibles")) {
            return "Notificación";
        }
        else{
            return type;
        }
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void enviarNotificacion() {
        System.out.println("Mensaje: " + mensaje);
        System.out.println("Fecha de envío: " + fechaEnvio);
        System.out.println("Usuario: " + usuario.getNombre());
        System.out.println("Tipo: " + tipo);
    }


}
