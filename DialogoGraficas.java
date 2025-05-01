import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class DialogoGraficas extends JDialog {
    private Biblioteca biblioteca;
    private int tipoGrafico;
    private JRadioButton optUsuarios, optLibros;
    private JButton btnGenerar, btnCancelar;
    private String tituloGrafico;

    public DialogoGraficas(JFrame parent, Biblioteca biblioteca, int tipoGrafico, String tituloGrafico) {
        super(parent, "Selección de Datos para Gráfico", true);
        this.biblioteca = biblioteca;
        this.tipoGrafico = tipoGrafico;
        this.tituloGrafico = tituloGrafico;

        // Configuración del diálogo
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Panel de opciones
        JPanel pnlOpciones = new JPanel(new GridLayout(2, 1));
        pnlOpciones.setBorder(BorderFactory.createTitledBorder("Seleccione tipo de datos"));

        optUsuarios = new JRadioButton("Usuarios y total de préstamos");
        optLibros = new JRadioButton("Libros y préstamos por libro");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(optUsuarios);
        grupo.add(optLibros);
        optUsuarios.setSelected(true);

        pnlOpciones.add(optUsuarios);
        pnlOpciones.add(optLibros);

        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGenerar = new JButton("Generar Gráfico");
        btnCancelar = new JButton("Cancelar");

        pnlBotones.add(btnGenerar);
        pnlBotones.add(btnCancelar);

        // Acciones
        btnGenerar.addActionListener(e -> generarGrafico());
        btnCancelar.addActionListener(e -> dispose());

        // Añadir paneles al diálogo
        add(pnlOpciones, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);
    }

    private void generarGrafico() {
        boolean mostrarUsuarios = optUsuarios.isSelected();

        if (mostrarUsuarios) {
            generarGraficoUsuarios();
        } else {
            generarGraficoLibros();
        }

        dispose();
    }

    private void generarGraficoUsuarios() {
        List<Usuario> usuarios = biblioteca.getUsuarios();
        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay usuarios registrados", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Preparar datos para el gráfico
        int[] valores = new int[usuarios.size()];
        String[] etiquetas = new String[usuarios.size()];

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            etiquetas[i] = usuario.getNombre();
            valores[i] = usuario.getPrestamosRealizados();
        }

        // Crear y mostrar el gráfico
        TestGrafico grafico = new TestGrafico(tituloGrafico + " - Préstamos por Usuario");
        grafico.tipoGrafico = tipoGrafico;
        grafico.valores = valores;
        grafico.etiquetas = etiquetas;
        grafico.crearGrafico();
        grafico.setSize(800, 600);
        grafico.setAlwaysOnTop(true);
        grafico.setVisible(true);
    }

    private void generarGraficoLibros() {
        List<Libro> libros = biblioteca.getLibros();
        List<Prestamo> prestamos = biblioteca.getPrestamos();
        List<Prestamo> historialPrestamos = biblioteca.getPrestamosHist();

        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay libros registrados", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear mapa para contar préstamos por libro
        Map<String, Integer> prestamosPorLibro = new HashMap<>();

        // Inicializar contador de préstamos para cada libro
        for (Libro libro : libros) {
            prestamosPorLibro.put(libro.getIsbn(), 0);
        }

        // Contar préstamos actuales y históricos
        for (Prestamo prestamo : prestamos) {
            String isbn = prestamo.getLibro().getIsbn();
            prestamosPorLibro.put(isbn, prestamosPorLibro.getOrDefault(isbn, 0) + 1);
        }

        for (Prestamo prestamo : historialPrestamos) {
            String isbn = prestamo.getLibro().getIsbn();
            prestamosPorLibro.put(isbn, prestamosPorLibro.getOrDefault(isbn, 0) + 1);
        }

        // Preparar datos para el gráfico
        List<String> etiquetasList = new ArrayList<>();
        List<Integer> valoresList = new ArrayList<>();

        for (Libro libro : libros) {
            int totalPrestamos = prestamosPorLibro.get(libro.getIsbn());
            if (totalPrestamos > 0) {
                etiquetasList.add(libro.getTitulo());
                valoresList.add(totalPrestamos);
            }
        }

        if (etiquetasList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay préstamos registrados para ningún libro",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Convertir listas a arrays
        String[] etiquetas = etiquetasList.toArray(new String[0]);
        int[] valores = new int[valoresList.size()];
        for (int i = 0; i < valoresList.size(); i++) {
            valores[i] = valoresList.get(i);
        }

        // Crear y mostrar el gráfico
        TestGrafico grafico = new TestGrafico(tituloGrafico + " - Préstamos por Libro");
        grafico.tipoGrafico = tipoGrafico;
        grafico.valores = valores;
        grafico.etiquetas = etiquetas;
        grafico.crearGrafico();
        grafico.setSize(800, 600);
        grafico.setAlwaysOnTop(true);
        grafico.setVisible(true);
    }
}