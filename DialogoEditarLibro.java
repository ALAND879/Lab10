import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogoEditarLibro extends JDialog {
    private Biblioteca biblioteca;
    private Libro libro;
    private JTextField txtTitulo, txtAutor, txtISBN, txtNumPaginas;
    private JComboBox<String> cmbEstado;
    private boolean editadoExitoso = false;

    public DialogoEditarLibro(JFrame parent, Biblioteca biblioteca, Libro libro) {
        super(parent, "Editar Libro", true);
        this.biblioteca = biblioteca;
        this.libro = libro;

        // Configuración del diálogo
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Panel de campos
        JPanel pnlCampos = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos para editar
        pnlCampos.add(new JLabel("Título:"));
        txtTitulo = new JTextField(libro.getTitulo());
        pnlCampos.add(txtTitulo);

        pnlCampos.add(new JLabel("Autor:"));
        txtAutor = new JTextField(libro.getAutor());
        pnlCampos.add(txtAutor);

        pnlCampos.add(new JLabel("ISBN:"));
        txtISBN = new JTextField(libro.getIsbn());
        pnlCampos.add(txtISBN);

        pnlCampos.add(new JLabel("Número de páginas:"));
        txtNumPaginas = new JTextField(String.valueOf(libro.getNumPaginas()));
        pnlCampos.add(txtNumPaginas);

        pnlCampos.add(new JLabel("Estado:"));
        String[] estados = {"Disponible", "Prestado"};
        cmbEstado = new JComboBox<>(estados);
        cmbEstado.setSelectedIndex(libro.isPrestado() ? 1 : 0);
        pnlCampos.add(cmbEstado);

        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);

        // Acción para guardar cambios
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    guardarCambios();
                    editadoExitoso = true;
                    dispose();
                }
            }
        });

        // Acción para cancelar
        btnCancelar.addActionListener(e -> dispose());

        // Añadir paneles al diálogo
        add(pnlCampos, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);
    }

    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty() ||
                txtAutor.getText().trim().isEmpty() ||
                txtISBN.getText().trim().isEmpty() ||
                txtNumPaginas.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int numPaginas = Integer.parseInt(txtNumPaginas.getText().trim());
            if (numPaginas <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El número de páginas debe ser un número positivo",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El número de páginas debe ser un número entero",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void guardarCambios() {
        // Guardar los cambios en el objeto libro
        libro.setTitulo(txtTitulo.getText().trim());
        libro.setAutor(txtAutor.getText().trim());
        libro.setIsbn(txtISBN.getText().trim());
        libro.setNumPaginas(Integer.parseInt(txtNumPaginas.getText().trim()));
        libro.setPrestado(cmbEstado.getSelectedIndex() == 1);

        JOptionPane.showMessageDialog(this,
                "Libro actualizado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isEditadoExitoso() {
        return editadoExitoso;
    }

    public Libro getLibro() {
        return libro;
    }
}