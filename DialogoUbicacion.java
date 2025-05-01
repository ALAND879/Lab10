import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogoUbicacion extends JDialog {
    private DiagramaBiblioteca diagrama;
    private Libro libro;

    private class CierraDialogo implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    public DialogoUbicacion(JFrame parent, Libro libro) {
        super(parent, "Ubicación del Libro", true);
        this.libro = libro;

        // Configuración básica del diálogo
        setSize(800, 700);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Crear y agregar el diagrama
        diagrama = new DiagramaBiblioteca();
        add(diagrama, BorderLayout.CENTER);

        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        // Extraer últimos 3 dígitos del ISBN
        String isbn = libro.getIsbn();
        String ubicacion = isbn.substring(Math.max(0, isbn.length() - 3));
        int numLibrero = Integer.parseInt(ubicacion);
        diagrama.setLibroUbicacion(numLibrero);

        // Extraer últimos 3 dígitos del ISBN
        numLibrero = Integer.parseInt(ubicacion);
        diagrama.setLibroUbicacion(numLibrero);

        // Etiquetas de información
        JLabel titleLabel = new JLabel("Título: " + libro.getTitulo());
        JLabel isbnLabel = new JLabel("ISBN: " + libro.getIsbn());
        JLabel ubicacionLabel = new JLabel("Número de Librero: " + numLibrero);

        // Etiquetas de información
        infoPanel.add(titleLabel);
        infoPanel.add(isbnLabel);
        infoPanel.add(ubicacionLabel);
        // Etiquetas de información
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoPanel, BorderLayout.NORTH);

        // Botón de cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new CierraDialogo());

        // Botón de cerrar
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

}