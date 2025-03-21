import java.time.LocalDate;
import java.util.*;

public class Auxiliar extends Empleado {
    private List<String> eventosProximos;
    private int usuariosAsistidos;

    public Auxiliar(String nombre, String id, double salario) {
        super(nombre, id, salario, "Auxiliar");
        this.eventosProximos = new ArrayList<>();
        this.usuariosAsistidos = 0;
    }

    public void asistirUsuarios() {
        usuariosAsistidos++;
        System.out.println("Asistencia proporcionada por " + getNombre());
        System.out.println("Total de usuarios asistidos hoy: " + usuariosAsistidos);
    }

    public void organizarEventos() {
        String nuevoEvento = "Evento " + (eventosProximos.size() + 1) +
                           " - Fecha: " + LocalDate.now().plusDays(7);
        eventosProximos.add(nuevoEvento);
        System.out.println("Nuevo evento organizado: " + nuevoEvento);
        System.out.println("Total eventos próximos: " + eventosProximos.size());
    }

    public List<String> getEventosProximos() {
        return new ArrayList<>(eventosProximos);
    }

    public int getUsuariosAsistidos() {
        return usuariosAsistidos;
    }
}