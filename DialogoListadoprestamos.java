import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DialogoListadoprestamos extends JDialog {
    private JTable tablaUsuarios;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloPrestamos;
    private Biblioteca biblioteca;

    public DialogoListadoprestamos(JFrame parent, Biblioteca biblioteca) {
        super(parent, "Listado de Préstamos por Usuario", true);
        this.biblioteca = biblioteca;

        setLayout(new BorderLayout());
        setSize(800, 600);

        // Panel de usuarios
        JPanel panelUsuarios = new JPanel(new BorderLayout());
        modeloUsuarios = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoría"}, 0);
        tablaUsuarios = new JTable(modeloUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        panelUsuarios.add(new JLabel("Usuarios"), BorderLayout.NORTH);
        panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);

        // Panel de préstamos
        JPanel panelPrestamos = new JPanel(new BorderLayout());
        modeloPrestamos = new DefaultTableModel(new String[]{"Título", "Autor", "ISBN", "Fecha Préstamo"}, 0);
        tablaPrestamos = new JTable(modeloPrestamos);
        JScrollPane scrollPrestamos = new JScrollPane(tablaPrestamos);
        panelPrestamos.add(new JLabel("Préstamos del Usuario"), BorderLayout.NORTH);
        panelPrestamos.add(scrollPrestamos, BorderLayout.CENTER);

        // Botón para cargar préstamos
        JButton btnCargarPrestamos = new JButton("Cargar Préstamos");
        btnCargarPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPrestamos();
            }
        });

        // Agregar paneles y botón al diálogo
        add(panelUsuarios, BorderLayout.WEST);
        add(panelPrestamos, BorderLayout.CENTER);
        add(btnCargarPrestamos, BorderLayout.SOUTH);

        // Cargar usuarios al iniciar
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        modeloUsuarios.setRowCount(0);
        List<Usuario> usuarios = biblioteca.getUsuarios();
        for (Usuario usuario : usuarios) {
            modeloUsuarios.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNombre(),
                    obtenerNombreCategoria(usuario.getCategoria())
            });
        }
    }

    private void cargarPrestamos() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String idUsuario = (String) modeloUsuarios.getValueAt(filaSeleccionada, 0);
            Usuario usuario = biblioteca.buscarUsuarioPorId(idUsuario);

            if (usuario != null) {
                modeloPrestamos.setRowCount(0);
                List<Libro> librosPrestados = usuario.getLibrosPrestado();
                for (Libro libro : librosPrestados) {
                    modeloPrestamos.addRow(new Object[]{
                            libro.getTitulo(),
                            libro.getAutor(),
                            libro.getIsbn() // Asegúrate de que el método exista
                    });
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String obtenerNombreCategoria(int categoria) {
        switch (categoria) {
            case Usuario.USUARIO_REGULAR:
                return "Regular";
            case Usuario.USUARIO_PROFESOR:
                return "Profesor";
            case Usuario.USUARIO_INVESTIGADOR:
                return "Investigador";
            default:
                return "Desconocida";
        }
    }
}