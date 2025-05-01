import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogoNuevoLibro extends JDialog {
    private JTextField txtISBN, txtTitulo, txtAutor, txtEditorial;
    private JSpinner spnPaginas;
    private JButton btnGuardar, btnCancelar;
    private boolean guardadoExitoso = false;
    private JPanel pnlBotones;
    private Libro nuevoLibro;

    public DialogoNuevoLibro(Frame parent) {
        super(parent, "Nuevo Libro", true);

        inicializarComponentes();

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

    public Libro getLibro() {
        return nuevoLibro;
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }

    private class BotonGuardar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            guardarLibro();
        }
    }

    private class BotonCancelar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private void guardarLibro() {
        if (validarCampos()) {
            nuevoLibro = new Libro( txtTitulo.getText().trim(), txtAutor.getText().trim(),
                    txtISBN.getText().trim(), (int) spnPaginas.getValue());
            JOptionPane.showMessageDialog(this, "Libro guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            guardadoExitoso = true;
            dispose();
        }
    }

    private boolean validarCampos() {
        if (txtISBN.getText().trim().isEmpty() || txtTitulo.getText().trim().isEmpty() ||
                txtAutor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos requeridos", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Validar formato básico ISBN
        String isbn = txtISBN.getText().trim().replace("-", ""); // Eliminar guiones
        if (isbn.length() != 10 && isbn.length() != 13) {
            JOptionPane.showMessageDialog(this, "El ISBN debe tener 10 o 13 dígitos", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Validar que el número de páginas sea mayor a 0
        if ((int) spnPaginas.getValue() <= 0) {
            JOptionPane.showMessageDialog(this, "El número de páginas debe ser mayor a 0", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        txtISBN = new JTextField(20);
        txtTitulo = new JTextField(20);
        txtAutor = new JTextField(20);
        txtEditorial = new JTextField(20);
        spnPaginas = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));

        // Panel de botones
        pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);

        agregarComponente("Título:", txtTitulo, gbc, 0);
        agregarComponente("Autor:", txtAutor, gbc, 1);
        agregarComponente("ISBN:", txtISBN, gbc, 2);
        agregarComponente("Páginas:", spnPaginas, gbc, 3);

        // Agregar panel de botones al diálogo
        gbc.gridx = 0;
        gbc.gridy = 7;
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
        DialogoNuevoLibro dialogo = new DialogoNuevoLibro(null);
        System.exit(0);
    }

}