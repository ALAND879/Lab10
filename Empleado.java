
/**
 * Clase que representa a un empleado de la biblioteca.
 * Extiende de la clase Persona y añade funcionalidades específicas para la gestión de préstamos.
 * 
 * @author Roberto Salazar Márquez
 * @version 1.1
 */
import java.util.*;

public class Empleado extends Persona {
    /** Número identificador único del empleado */
    private String numeroEmpleado;
    /** Puesto que ocupa el empleado en la biblioteca */
    private String puesto;
    /** Salario del empleado */
    private double salario;
    /** Turno de trabajo del empleado (MATUTINO, VESPERTINO o MIXTO) */
    private int turno;
    /** Cola de Préstamos pendientes de ser atendidos */
    private Queue<Prestamo> prestamosEnProceso;
    /** Historial de prestamos asignados por el empleado*/
    private List<Prestamo> historialPrestamos;
    /** Contador para generar IDs únicos */
    private static int contadorId = 0;

    private int nivelAutorizacion; // (Técnico, Administrador, Supervisor, Director)
    private Vector<String> especialidades;
    private Vector<Integer> horarios;

    // Costantes de horarios (Por dia)
    public static final int Lunes = 0;
    public static final int Martes = 1;
    public static final int Miercoles = 2;
    public static final int Jueves = 3;
    public static final int Viernes = 4;
    public static final int Sabado = 5;

    // Costantes de niveles de autorización
    public static final int NIVEL_INICIAL = 0;
    public static final int NIVEL_TECNICO = 1;
    public static final int NIVEL_ADMINISTRADOR = 2;
    public static final int NIVEL_SUPERVISOR = 3;
    public static final int NIVEL_DIRECTOR = 4;

    /** Constante que representa el turno matutino */
    public static final int MATUTINO = 0;
    /** Constante que representa el turno vespertino */
    public static final int VESPERTINO = 1;
    /** Constante que representa el turno mixto */
    public static final int MIXTO = 2;

    /**
     * Constructor de la clase Empleado.
     * @param nombre Nombre del empleado
     * @param id Identificador único del empleado
     * @param numeroEmpleado Número de empleado asignado
     * @param puesto Puesto que ocupará el empleado
     */
    public Empleado(String nombre, String id, double salario, String puesto) {
        super(nombre, id);
        this.salario = salario;
        this.puesto = puesto;
        this.prestamosEnProceso = new LinkedList<>();
        this.historialPrestamos = new ArrayList<>();
        this.nivelAutorizacion = 0;
        this.especialidades = new Vector<String>();
        this.horarios = new Vector<Integer>();
    }

    public Empleado(String nombre, String id, double salario, String puesto, int nivelAutorizacion) {
        super(nombre, id);
        this.salario = salario;
        this.puesto = puesto;
        this.prestamosEnProceso = new LinkedList<>();
        this.historialPrestamos = new ArrayList<>();
        this.nivelAutorizacion = nivelAutorizacion;
        this.especialidades = new Vector<String>();
        this.horarios = new Vector<Integer>();
    }

    /**
     * Obtiene el puesto del empleado.
     * @return String con el puesto actual
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * Establece el puesto del empleado.
     * @param puesto Nuevo puesto a asignar
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    /**
     * Obtiene el salario del empleado.
     * @return double con el salario actual
     */
    public double getSalario() {
        return salario;
    }

    /**
     * Establece el salario del empleado.
     * @param salario Nuevo salario a asignar
     */
    public void setSalario(double salario) {
        this.salario = salario > 0 ? salario : 0;
    }

    /**
     * Obtiene el turno del empleado.
     * @return int que representa el turno (MATUTINO, VESPERTINO o MIXTO)
     */
    public int getTurno() {
        return turno;
    }

    /**
     * Establece el turno del empleado.
     * @param turno Nuevo turno a asignar
     */
    public void setTurno(int turno) {
        this.turno = turno;
    }
    
    /**
     * Obtiene una copia defensiva de la cola de préstamos en proceso
     * @return Una nueva Queue con los préstamos en proceso actuales
     */
    public Queue<Prestamo> getPrestamosEnProceso() {
        return new LinkedList<>(prestamosEnProceso);
    }
    
    /**
     * Obtiene una copia defensiva del historial de préstamos
     * @return Una nueva List con todo el historial de préstamos
     */
    public List<Prestamo> getHistorialPrestamos() {
        return new ArrayList<>(historialPrestamos);
    }

    /**
     * Obtiene el tipo de persona.
     * @return String "Empleado"
     */
    public String obtenerTipo() {
        return "Empleado";
    }

    /**
     * Genera un nuevo ID único para préstamos.
     * @return String con formato "P" seguido de 4 dígitos
     */
    public static String generarId() {
        contadorId++;
        return "P" + String.format("%04d", contadorId);
    }

    public void agregarEspecialidad(String especialidad) {
        if (!especialidades.contains(especialidad)) {
            especialidades.add(especialidad);
        }
    }

    public void eliminarEspecialidad(String especialidad) {
        if (especialidades.contains(especialidad)) {
            especialidades.remove(especialidad);
        }
    }

    public Vector<String> getEspecialidades() {
        return especialidades;
    }

    public void agregarHorario(int horario) {
        if (!horarios.contains(horario)) {
            horarios.add(horario);
        }
    }

    public void eliminarHorario(int horario) {
        if (horarios.contains(horario)) {
            horarios.remove(horario);
        }
    }

    public Vector<Integer> getHorarios() {
        return horarios;
    }

    public int getNivelAutorizacion() {
        return nivelAutorizacion;
    }

    public void setNivelAutorizacion(int nivelAutorizacion) {
        this.nivelAutorizacion = nivelAutorizacion;
    }

    /**
     * Procesa una solicitud de préstamo de libro.
     * @param libro Libro que se desea prestar
     * @param usuario Usuario que solicita el préstamo
     * @return true si el préstamo se realizó exitosamente, false en caso contrario
     */
    public boolean procesarPrestamo(Libro libro, Usuario usuario) {
        if (libro != null && usuario != null && !libro.isPrestado()) {
            if (usuario.solicitarPrestamo(libro)) {
                Prestamo nuevoPrestamo = new Prestamo(generarId(), usuario, libro);
                prestamosEnProceso.offer(nuevoPrestamo);
                historialPrestamos.add(nuevoPrestamo);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Procesa la devolución de un préstamo, liberando el préstamo gestionado
     * por el empleado.
     * 
     * @return true si se procesó la devolución exitosamente, false en caso contrario
     */
    public boolean devolverPrestamo() {
        Prestamo prestamo = prestamosEnProceso.poll();
        return prestamo != null;
    }
    
    /**
     * Devuelve una representación en cadena del empleado.
     * @return String con los datos del empleado
     */
    public String toString() {
        return "Empleado [puesto=" + puesto + 
               ", salario=" + salario + 
               ", turno=" + turno + 
               ", prestamos activos=" + prestamosEnProceso.size() + 
               ", nombre=" + getNombre() + 
               ", id=" + getId() + "]";
    }
}