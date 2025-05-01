import javax.swing.*;
import java.awt.*;

public class DialogoIntegrantes extends JDialog {
    public DialogoIntegrantes(Frame parent) {
        super(parent, "Integrantes", true);

        // Configuración del layout
        setLayout(new BorderLayout());

        // Datos de los integrantes
        String[] columnas = {"Nombre", "Carrera"};
        Object[][] datos = {
                {"Alan David Jiménez Rodríguez", "Ingeniería en Ciencia de Datos"},
                {"José Eduardo Calderón Tiempo ", "Ingeniería en Sistemas"},
                {"Tomas Muñoz Cortes", "Ingeniería de Software"}
        };

        // Crear tabla con los datos
        JTable tablaIntegrantes = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tablaIntegrantes);

        // Agregar la tabla al diálogo
        add(scrollPane, BorderLayout.CENTER);

        // Botón de cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);

        // Configuración del diálogo
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
}