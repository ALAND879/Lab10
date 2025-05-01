import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class DialogoHistorialPrestamos extends JDialog {
    private Biblioteca biblioteca;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JButton btnCerrar;

    public DialogoHistorialPrestamos(JFrame parent, Biblioteca biblioteca) {
        super(parent, "Historial de Préstamos", true);
        this.biblioteca = biblioteca;

        // Configuración del diálogo
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Crear el modelo de tabla
        String[] columnas = {
            "ID Préstamo",
            "Título del Libro",
            "ISBN",
            "Usuario",
            "ID Usuario",
            "Fecha Préstamo",
            "Fecha Devolución",
            "Estado",
            "Multa"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };

        // Crear la tabla
        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botón de cerrar
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar los datos del historial
        cargarHistorialPrestamos();
    }

    private void cargarHistorialPrestamos() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        List<Prestamo> historialPrestamos = biblioteca.getPrestamosHist();

        // Iterar sobre cada préstamo y agregarlo a la tabla
        for (Prestamo prestamo : historialPrestamos) {
            Libro libro = prestamo.getLibro();
            Usuario usuario = prestamo.getUsuario();
            Multa multa = libro.getMulta();

            // Determinar estado del préstamo
            String estado = libro.isPrestado() ? "Pendiente" : "Devuelto";

            // Determinar información de la multa
            String infoMulta = "No";
            if (multa != null) {
                infoMulta = multa.getMonto() + " $ - " +
                        (multa.isVencido() ? "Vencida" : "Pendiente");
            }

            // Obtener solo el mes de las fechas o N/A cuando no hay fecha
            String mesPrestamo = prestamo.getFechaPrestamo() != null ?
                    prestamo.getFechaPrestamo().getMonth().toString() : "N/A";

            String mesDevolucion = prestamo.getFechaDevolucion() != null ?
                    prestamo.getFechaDevolucion().getMonth().toString() : "Pendiente";

            // Agregar fila a la tabla
            modeloTabla.addRow(new Object[] {
                prestamo.getId(),
                libro.getTitulo(),
                libro.getIsbn(),
                usuario.getNombre(),
                usuario.getId(),
                mesPrestamo,
                mesDevolucion,
                estado,
                infoMulta
            });
        }

        // Ajustar el ancho de las columnas
        tablaHistorial.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        tablaHistorial.getColumnModel().getColumn(1).setPreferredWidth(180); // Título
        tablaHistorial.getColumnModel().getColumn(2).setPreferredWidth(120); // ISBN
        tablaHistorial.getColumnModel().getColumn(3).setPreferredWidth(150); // Usuario
        tablaHistorial.getColumnModel().getColumn(4).setPreferredWidth(80);  // ID Usuario
        tablaHistorial.getColumnModel().getColumn(5).setPreferredWidth(120); // Fecha préstamo
        tablaHistorial.getColumnModel().getColumn(6).setPreferredWidth(120); // Fecha devolución
        tablaHistorial.getColumnModel().getColumn(7).setPreferredWidth(80);  // Estado
        tablaHistorial.getColumnModel().getColumn(8).setPreferredWidth(150); // Multa
    }
}