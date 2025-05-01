import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogoPrestamo extends JDialog {
    private Biblioteca biblioteca;
    private Libro libro;
    private JComboBox<Usuario> cmbUsuarios;
    private boolean prestamoRealizado = false;

    public DialogoPrestamo(JFrame parent, Biblioteca biblioteca, Libro libro) {
        super(parent, "Registrar Préstamo", true);
        this.biblioteca = biblioteca;
        this.libro = libro;

        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(parent);

        // Panel de selección de usuario
        JPanel pnlSeleccion = new JPanel(new FlowLayout());
        pnlSeleccion.add(new JLabel("Seleccione el usuario:"));
        cmbUsuarios = new JComboBox<>(biblioteca.getUsuarios().toArray(new Usuario[0]));
        pnlSeleccion.add(cmbUsuarios);

        // Botones
        JPanel pnlBotones = new JPanel(new FlowLayout());
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        pnlBotones.add(btnAceptar);
        pnlBotones.add(btnCancelar);

        add(pnlSeleccion, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);

        // Listeners
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuarioSeleccionado = (Usuario) cmbUsuarios.getSelectedItem();
                if (usuarioSeleccionado != null) {
                    Prestamo prestamo = new Prestamo(String.valueOf(Prestamo.generarId()), usuarioSeleccionado, libro);
                    if (prestamo.registrarPrestamo()) {
                        biblioteca.agregarPrestamo(prestamo);
                        libro.setPrestado(true);
                        prestamoRealizado = true;
                        JOptionPane.showMessageDialog(DialogoPrestamo.this, "Préstamo registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(DialogoPrestamo.this, "No se pudo registrar el préstamo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    public boolean isPrestamoRealizado() {
        return prestamoRealizado;
    }
}