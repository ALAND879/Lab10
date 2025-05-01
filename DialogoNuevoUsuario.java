import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogoNuevoUsuario extends JDialog {
    private JTextField txtNombre, txtID, txtCorreo;
    private JComboBox<String> cmbCategoria;
    private JButton btnGuardar, btnCancelar;
    private boolean guardadoExitoso = false;
    private Usuario nuevoUsuario;

    public DialogoNuevoUsuario(Frame parent) {
        super(parent, "Nuevo Usuario", true);

        inicializarComponentes();

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

    public Usuario getUsuario() {
        return nuevoUsuario;
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }

    private class BotonGuardar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            guardarUsuario();
        }
    }

    private class BotonCancelar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private void guardarUsuario() {
        if (validarCampos()) {
            int categoria = cmbCategoria.getSelectedIndex();
            nuevoUsuario = new Usuario(txtNombre.getText().trim(), txtID.getText().trim(), categoria);
            JOptionPane.showMessageDialog(this, "Usuario guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            guardadoExitoso = true;
            dispose();
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || txtID.getText().trim().isEmpty() || txtCorreo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos requeridos", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtCorreo.getText().trim().matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico no es válido", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        txtNombre = new JTextField(20);
        txtID = new JTextField(20);
        txtCorreo = new JTextField(20);
        cmbCategoria = new JComboBox<>(new String[]{"Regular", "Profesor", "Investigador"});

        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);

        agregarComponente("Nombre:", txtNombre, gbc, 0);
        agregarComponente("ID:", txtID, gbc, 1);
        agregarComponente("Correo:", txtCorreo, gbc, 2);
        agregarComponente("Categoría:", cmbCategoria, gbc, 3);

        // Agregar panel de botones al diálogo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pnlBotones, gbc);

        // Agregar ActionListener a los botones
        btnGuardar.addActionListener(new BotonGuardar());
        btnCancelar.addActionListener(new BotonCancelar());
    }

    private void agregarComponente(String etiqueta, JComponent componente, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(componente, gbc);
    }

    public static void main(String[] args) {
        DialogoNuevoUsuario dialogo = new DialogoNuevoUsuario(null);
        System.exit(0);
    }
}