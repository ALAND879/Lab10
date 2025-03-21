import java.util.*;

public class Administrador extends Empleado {
    private Map<String, Double> presupuestoAreas;
    private List<Empleado> empleadosGestionados;

    public Administrador(String nombre, String id, double salario) {
        super(nombre, id, salario, "Administrador");
        this.presupuestoAreas = new HashMap<>();
        this.empleadosGestionados = new ArrayList<>();
        setNivelAutorizacion(NIVEL_ADMINISTRADOR);
        inicializarPresupuestos();
    }

    private void inicializarPresupuestos() {
        presupuestoAreas.put("Adquisiciones", 50000.0);
        presupuestoAreas.put("Mantenimiento", 25000.0);
        presupuestoAreas.put("Recursos Humanos", 35000.0);
    }

    public void gestionarEmpleados() {
        // Simula la gestión de empleados
        System.out.println("Gestión de personal por " + getNombre());
        System.out.println("Total empleados gestionados: " + empleadosGestionados.size());
        for (Empleado emp : empleadosGestionados) {
            System.out.println("- " + emp.getNombre() + " (" + emp.getPuesto() + ")");
        }
    }

    public void gestionarFinanzas() {
        double total = 0;
        System.out.println("\nGestión financiera - Presupuestos por área:");
        for (Map.Entry<String, Double> entry : presupuestoAreas.entrySet()) {
            System.out.printf("%s: $%.2f%n", entry.getKey(), entry.getValue());
            total += entry.getValue();
        }
        System.out.printf("Presupuesto total: $%.2f%n", total);
    }

    public boolean asignarPresupuesto(String area, double monto) {
        if (monto > 0 && presupuestoAreas.containsKey(area)) {
            presupuestoAreas.put(area, monto);
            return true;
        }
        return false;
    }

    public void agregarEmpleado(Empleado empleado) {
        if (!empleadosGestionados.contains(empleado)) {
            empleadosGestionados.add(empleado);
            System.out.println("Empleado " + empleado.getNombre() + " agregado exitosamente.");
        }
    }

    public boolean removerEmpleado(String id) {
        return empleadosGestionados.removeIf(emp -> emp.getId().equals(id));
    }

    public List<Empleado> getEmpleadosGestionados() {
        return new ArrayList<>(empleadosGestionados);
    }

    public Map<String, Double> getPresupuestoAreas() {
        return new HashMap<>(presupuestoAreas);
    }
}