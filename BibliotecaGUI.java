import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaGUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu mnuEstadistica, mnuCatalogo, mnuPrestamo, mnuUsuarios;
    private JTextField txtBusqueda;
    private JButton btnBuscar, btnNuevoLibro, btnBuscarLibro, btnEliminarLibro, btnListarLibros,btnUbicarLibro;
    private JPanel pnlPrincipal, pnlBusqueda, pnlAcciones;
    private JTable tablaLibros;
    private JScrollPane scrollTabla;
    private JLabel lblEstado;
    private Biblioteca biblioteca;
    private JMenu mnuInfo;
    private JButton btnEditarLibro;


    private void realizaBusquedaRapida() {
        String termino = txtBusqueda.getText().trim().toLowerCase();
        if (!termino.isEmpty()) {
            DefaultTableModel modelo = (DefaultTableModel) tablaLibros.getModel();
            List<Object[]> resultados = new ArrayList<>();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String titulo = modelo.getValueAt(i, 0).toString().toLowerCase();
                String autor = modelo.getValueAt(i, 1).toString().toLowerCase();
                String isbn = modelo.getValueAt(i, 2).toString().toLowerCase();
                if (titulo.contains(termino) || autor.contains(termino) || isbn.contains(termino)) {
                    resultados.add(new Object[]{
                        modelo.getValueAt(i, 0),
                        modelo.getValueAt(i, 1),
                        modelo.getValueAt(i, 2),
                        modelo.getValueAt(i, 3),
                        modelo.getValueAt(i, 4)
                    });
                }
            }
            modelo.setRowCount(0);
            for (Object[] fila : resultados) {
                modelo.addRow(fila);
            }
            actualizarEstado("Búsqueda rápida completada: " + resultados.size() + " libros encontrados");
        }
    }

    private void actualizarTablaLibros(List<Libro> libros) {
        DefaultTableModel modelo = (DefaultTableModel) tablaLibros.getModel();
        modelo.setRowCount(0);
        String estado;
        for (Libro libro : libros) {
            if(libro.isPrestado())
                estado = new String ("Prestado");
            else
                estado = new String ("Disponible");
            Object[] fila = {
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getNumPaginas(),
                    estado
            };
            modelo.addRow(fila);
        }
    }

    private void mostrarDialogoNuevoLibro() {
        DialogoNuevoLibro dialogo = new DialogoNuevoLibro(this);
        if (dialogo.isGuardadoExitoso()) {
            Libro nuevoLibro = dialogo.getLibro();
            biblioteca.agregarLibro(nuevoLibro);
            actualizarEstado("Catalogo Actualizado");
            actualizarTablaLibros(biblioteca.getLibros());
        }
    }

    public void mostrarDialogoUbicacion() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String titulo = (String) tablaLibros.getValueAt(filaSeleccionada, 0);
            String autor = (String) tablaLibros.getValueAt(filaSeleccionada, 1);
            String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
            // Crear un nuevo objeto Libro con los datos
            Libro libroSeleccionado = new Libro();
            libroSeleccionado.setTitulo(titulo);
            libroSeleccionado.setAutor(autor);
            libroSeleccionado.setIsbn(isbn);
            DialogoUbicacion dialog = new DialogoUbicacion(this, libroSeleccionado);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un libro para ubicar",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private class MostrarUbicacion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarDialogoUbicacion();
        }
    }

    private void crearPanelPrincipal() {
        pnlBusqueda = new JPanel(new FlowLayout());
        txtBusqueda = new JTextField(30);
        btnBuscar = new JButton("Buscar");
        pnlBusqueda.add(txtBusqueda);
        pnlBusqueda.add(btnBuscar);



        // Tabla de libros
        String[] columnas = {"Título", "Autor", "ISBN", "Páginas", "Estado"};
        Object[][] datos = {};
        DefaultTableModel modelo = new DefaultTableModel(datos,columnas);
        tablaLibros = new JTable(modelo);
        scrollTabla = new JScrollPane(tablaLibros);
        // Panel de acciones
        pnlAcciones = new JPanel();
        btnNuevoLibro = new JButton("Nuevo Libros");
        btnBuscarLibro = new JButton("Buscar");
        btnEditarLibro = new JButton("Editar");
        btnEliminarLibro = new JButton("Eliminar");
        btnUbicarLibro = new JButton("Ubicar Libro");
        btnListarLibros = new JButton("Listar Libros");

        // Panel de acciones
        pnlAcciones.add(btnNuevoLibro);
        pnlAcciones.add(btnEditarLibro);
        pnlAcciones.add(btnEliminarLibro);
        pnlAcciones.add(btnUbicarLibro);
        pnlAcciones.add(btnListarLibros);

        // Panel principal
        pnlPrincipal = new JPanel(new BorderLayout());
        // Panel principal
        pnlPrincipal.add(pnlBusqueda, BorderLayout.NORTH);
        pnlPrincipal.add(scrollTabla, BorderLayout.CENTER);
        pnlPrincipal.add(pnlAcciones, BorderLayout.SOUTH);

        // Asignación de Listeners
        btnNuevoLibro.addActionListener(e -> mostrarDialogoNuevoLibro());
        btnBuscar.addActionListener(e -> realizaBusquedaRapida());
        btnUbicarLibro.addActionListener(new MostrarUbicacion());
        btnListarLibros.addActionListener(e -> {
            actualizarTablaLibros(biblioteca.getLibros());
            actualizarEstado("Se listaron todos los libros disponibles en la biblioteca");
        });
        btnEditarLibro.addActionListener(e -> mostrarDialogoEditarLibro());
        btnEliminarLibro.addActionListener(e -> eliminarLibro());

        //  Barra de Estado
        lblEstado = new JLabel(" Listo");
        lblEstado.setBorder(BorderFactory.createLoweredBevelBorder());
        // Integración Final
        this.add(pnlPrincipal, BorderLayout.CENTER);
        this.add(lblEstado,BorderLayout.SOUTH);

    }

    private void mostrarDialogoEditarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
            Libro libroSeleccionado = biblioteca.buscarLibroPorISBN(isbn);

            if (libroSeleccionado != null) {
                DialogoEditarLibro dialogo = new DialogoEditarLibro(this, biblioteca, libroSeleccionado);
                dialogo.setVisible(true);

                if (dialogo.isEditadoExitoso()) {
                    actualizarTablaLibros(biblioteca.getLibros());
                    actualizarEstado("Libro editado exitosamente: " + libroSeleccionado.getTitulo());
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo encontrar el libro seleccionado",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un libro para editar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
            Libro libroSeleccionado = biblioteca.buscarLibroPorISBN(isbn);

            if (libroSeleccionado != null) {
                int confirmacion = JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro que desea eliminar el libro \"" + libroSeleccionado.getTitulo() + "\"?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    biblioteca.eliminarLibro(libroSeleccionado);
                    actualizarTablaLibros(biblioteca.getLibros());
                    actualizarEstado("Libro eliminado: " + libroSeleccionado.getTitulo());
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo encontrar el libro seleccionado",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, seleccione un libro para eliminar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void actualizarEstado(String mensaje) {
        lblEstado.setText(" " + mensaje);
    }

    private void crearMenu() {
        menuBar = new JMenuBar();
        mnuEstadistica = new JMenu("Estadísticas");
        mnuCatalogo = new JMenu("Catalogo");
        mnuPrestamo = new JMenu("Préstamo");
        mnuUsuarios = new JMenu("Usuarios");
        mnuInfo = new JMenu("Acerca de");

        menuBar.add(mnuEstadistica);
        menuBar.add(mnuCatalogo);
        menuBar.add(mnuPrestamo);
        menuBar.add(mnuUsuarios);
        menuBar.add(mnuInfo);

        // Creación del lis Items de menu Estadisticas
        JMenuItem mnuBarras = new JMenuItem("Grafica de Barras");
        JMenuItem mnuPastel = new JMenuItem("Grafica de Pastel");
        JMenuItem mnuLineal = new JMenuItem("Grafica de Lineal");
        JMenuItem mnuDispersion = new JMenuItem("Grafica de Dispersion");
        JMenuItem mnuRadar = new JMenuItem("Grafica de Radar");
        JMenuItem mnuHistorial = new JMenuItem("Historial de Prestamos");

        mnuEstadistica.add(mnuBarras);
        mnuEstadistica.add(mnuPastel);
        mnuEstadistica.add(mnuLineal);
        mnuEstadistica.add(mnuDispersion);
        mnuEstadistica.add(mnuRadar);
        mnuEstadistica.add(mnuHistorial);

        mnuHistorial.addActionListener(e -> mostrarHistorialPrestamos());

        // Hacer dialogo
        mnuBarras.addActionListener(e -> mostrarDialogoGraficas(1, "Gráfica de Barras"));
        mnuPastel.addActionListener(e -> mostrarDialogoGraficas(2, "Gráfica de Pastel"));
        mnuLineal.addActionListener(e -> mostrarDialogoGraficas(3, "Gráfica Lineal"));
        mnuDispersion.addActionListener(e -> mostrarDialogoGraficas(4, "Gráfica de Dispersión"));
        mnuRadar.addActionListener(e -> mostrarDialogoGraficas(5, "Gráfica de Radar"));


        // Creación del lis Items de menu catalogo
        JMenuItem mnuNuevoLibro = new JMenuItem("Agregar Libro");
        mnuCatalogo.add(mnuNuevoLibro);
        mnuNuevoLibro.addActionListener(e -> mostrarDialogoNuevoLibro());

        // Creación del lis Items de menu prestamo
        JMenuItem mnuPres = new JMenuItem("Agregar Prestamo (Seleccione libro)");
        JMenuItem mnuDev = new JMenuItem("Devolver Libro (Seleccione libro)");
        JMenuItem mnuListarPrestamos = new JMenuItem("Listar Prestamos por Usuario");
        mnuPrestamo.add(mnuPres);
        mnuPrestamo.add(mnuDev);
        mnuPrestamo.add(mnuListarPrestamos);
        mnuPres.addActionListener(e -> realizarPrestamo());
        mnuDev.addActionListener(e -> devolverLibro());
        mnuListarPrestamos.addActionListener(e -> mostrarDialogoListadoPrestamos());

        // Creación del lis Items de menu Usuarios
        JMenuItem mnuRegUsr = new JMenuItem("Registrar usuario");
        JMenuItem mnuBusUsr = new JMenuItem("Buscar usuario");
        JMenuItem mnuListaUsr = new JMenuItem("Listar usuarios");
        mnuUsuarios.add(mnuRegUsr);
        mnuUsuarios.add(mnuBusUsr);
        mnuUsuarios.add(mnuListaUsr);
        mnuRegUsr.addActionListener(e -> registrarUsuario());
        mnuBusUsr.addActionListener(e -> buscarUsuario());
        mnuListaUsr.addActionListener(e -> listarUsuarios());

        // Creación del lis Items de menu Acerca de
        JMenu mnuIntegrantes = new JMenu("Version 10");
        mnuInfo.add(mnuIntegrantes);
        JMenuItem mnuIntegrantesItem = new JMenuItem("Ver Integrantes");
        mnuIntegrantes.add(mnuIntegrantesItem);
        mnuIntegrantesItem.addActionListener(e -> {
            DialogoIntegrantes dialogo = new DialogoIntegrantes(this);
            dialogo.setVisible(true);
        });

        // Establecer la barra de menús en el frame
        setJMenuBar(menuBar);
    }

    private void mostrarDialogoGraficas(int tipoGrafico, String titulo) {
        DialogoGraficas dialogo = new DialogoGraficas(this, biblioteca, tipoGrafico, titulo);
        dialogo.setVisible(true);
    }

    private void mostrarHistorialPrestamos() {
        DialogoHistorialPrestamos dialogo = new DialogoHistorialPrestamos(this, biblioteca);
        dialogo.setVisible(true);
        actualizarEstado("Historial de préstamos consultado");
    }

    private void mostrarDialogoListadoPrestamos() {
        DialogoListadoprestamos dialogo = new DialogoListadoprestamos(this, biblioteca);
        dialogo.setVisible(true);
    }

    private void registrarUsuario() {
        DialogoNuevoUsuario dialogo = new DialogoNuevoUsuario(this);
        if (dialogo.isGuardadoExitoso()) {
            Usuario nuevoUsuario = dialogo.getUsuario();
            biblioteca.agregarUsuario(nuevoUsuario);
            actualizarEstado("Usuario registrado exitosamente: " + nuevoUsuario.getNombre());
        }
    }

    private void buscarUsuario() {
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario a buscar:", "Buscar Usuario", JOptionPane.QUESTION_MESSAGE);
        if (idUsuario != null && !idUsuario.trim().isEmpty()) {
            Usuario usuario = biblioteca.buscarUsuarioPorID(idUsuario.trim());
            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Usuario encontrado:\n" + usuario, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un usuario con el ID proporcionado.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = biblioteca.getUsuarios();
        if (!usuarios.isEmpty()) {
            // Crear encabezados de la tabla
            String[] columnas = {"ID", "Nombre", "Categoría", "Préstamos Realizados"};
            Object[][] datos = new Object[usuarios.size()][columnas.length];

            // Llenar los datos de la tabla
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario usuario = usuarios.get(i);
                datos[i][0] = usuario.getId();
                datos[i][1] = usuario.getNombre();
                datos[i][2] = obtenerNombreCategoria(usuario.getCategoria());
                datos[i][3] = usuario.getPrestamosRealizados();
            }

            JTable tablaUsuarios = new JTable(datos, columnas);
            JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
            JOptionPane.showMessageDialog(this, scrollPane, "Lista de Usuarios", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No hay usuarios registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
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

   private void devolverLibro() {
       int filaSeleccionada = tablaLibros.getSelectedRow();
       if (filaSeleccionada >= 0) {
           // Obtener el libro seleccionado de la biblioteca
           String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
           String estadoStr = (String) tablaLibros.getValueAt(filaSeleccionada, 4);
           Libro libroSeleccionado = biblioteca.buscarLibroPorISBN(isbn);

            // Apoyo para depuracion de error
           //System.out.println("Libro seleccionado: " + (libroSeleccionado != null ? libroSeleccionado.getTitulo() : "null"));
           //System.out.println("Estado según tabla: " + estadoStr);
           //System.out.println("Estado según objeto: " + (libroSeleccionado != null ? (libroSeleccionado.isPrestado() ? "Prestado" : "No prestado") : "N/A"));

           // Verificamos si el estado en la tabla es "Prestado" incluso si el objeto dice lo contrario
           boolean deberiaEstarPrestado = "Prestado".equals(estadoStr);

           if (libroSeleccionado != null && (libroSeleccionado.isPrestado() || deberiaEstarPrestado)) {
               // Forzar el estado a prestado si hay inconsistencia
               if (deberiaEstarPrestado && !libroSeleccionado.isPrestado()) {
                   libroSeleccionado.setPrestado(true);
                   System.out.println("Corrigiendo inconsistencia: libro marcado como prestado");
               }

               // Buscar el préstamo asociado al libro
               Prestamo prestamo = biblioteca.buscarPrestamoPorLibro(libroSeleccionado);
               System.out.println("Resultado de búsqueda de préstamo: " + (prestamo != null ? "Encontrado" : "No encontrado"));

               // Si no se encuentra, intentar buscar por ISBN
               if (prestamo == null) {
                   System.out.println("Intentando buscar préstamo por ISBN: " + isbn);
                   for (Prestamo p : biblioteca.getPrestamos()) {
                       if (p.getLibro().getIsbn().equals(isbn)) {
                           prestamo = p;
                           System.out.println("Préstamo encontrado por ISBN alternativo");
                           break;
                       }
                   }
               }

               if (prestamo != null) {
                   // Procesar la devolución del préstamo
                   Usuario usuarioPrestamo = prestamo.getUsuario();
                   System.out.println("Usuario del préstamo: " + usuarioPrestamo.getNombre());

                   if (prestamo.procesarDevolucion()) {
                       // Una vez devuelto, actualizar el libro y eliminar el préstamo
                       libroSeleccionado.setPrestado(false);
                       biblioteca.eliminarPrestamo(prestamo);
                       System.out.println("Préstamo procesado correctamente");

                       // Revisar si tiene multa
                       Multa multa = libroSeleccionado.getMulta();
                       if (multa != null) {
                           if (multa.isVencido()) {
                               JOptionPane.showMessageDialog(this,
                                   "El libro tiene una multa vencida de: $" + multa.getMonto() +
                                   "\nUsuario: " + usuarioPrestamo.getNombre(),
                                   "Multa", JOptionPane.WARNING_MESSAGE);
                           } else {
                               JOptionPane.showMessageDialog(this,
                                   "El libro tiene una multa pendiente de: $" + multa.getMonto() +
                                   "\nUsuario: " + usuarioPrestamo.getNombre(),
                                   "Multa", JOptionPane.INFORMATION_MESSAGE);
                           }
                       }

                       actualizarTablaLibros(biblioteca.getLibros());
                       actualizarEstado("Préstamo devuelto exitosamente: " + libroSeleccionado.getTitulo() +
                                       " por " + usuarioPrestamo.getNombre());
                   } else {
                       System.out.println("Error al procesar la devolución");
                       JOptionPane.showMessageDialog(this,
                           "No se pudo procesar la devolución del préstamo.\nVerifique el estado del usuario y del libro.",
                           "Error", JOptionPane.ERROR_MESSAGE);
                   }
               } else {
                   System.out.println("No se encontró préstamo para el libro: " + isbn);
                   // Mostrar información de depuración
                   List<Prestamo> prestamos = biblioteca.getPrestamos();
                   System.out.println("Total de préstamos en la biblioteca: " + prestamos.size());
                   for (Prestamo p : prestamos) {
                       System.out.println("Préstamo ID: " + p.getId() + ", Libro: " + p.getLibro().getTitulo() + ", ISBN: " + p.getLibro().getIsbn());
                   }

                   JOptionPane.showMessageDialog(this, "No se encontró un préstamo asociado al libro.",
                       "Error", JOptionPane.WARNING_MESSAGE);
               }
           } else {
               JOptionPane.showMessageDialog(this, "El libro no está prestado o no se encontró.",
                   "Error", JOptionPane.WARNING_MESSAGE);
           }
       } else {
           JOptionPane.showMessageDialog(this, "Por favor, seleccione un libro para devolver.",
               "Error", JOptionPane.WARNING_MESSAGE);
       }
   }

    private void realizarPrestamo() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 2);

            // Buscar el libro en la biblioteca
            Libro libroSeleccionado = biblioteca.buscarLibroPorISBN(isbn);

            if (libroSeleccionado != null && !libroSeleccionado.isPrestado()) {
                DialogoPrestamo dialogo = new DialogoPrestamo(this, biblioteca, libroSeleccionado);
                dialogo.setVisible(true);

                if (dialogo.isPrestamoRealizado()) {
                    actualizarTablaLibros(biblioteca.getLibros());
                    actualizarEstado("Préstamo registrado exitosamente para el libro: " + libroSeleccionado.getTitulo());
                    // Añadir prestamo al historial de prestamos
                    // Añadir prestamo al historial de prestamos
                                        Prestamo nuevoPrestamo = dialogo.getPrestamo();
                                        if (nuevoPrestamo != null) {
                                            biblioteca.agregarPrestamoHist(nuevoPrestamo);
                                        }
                }
            } else {
                JOptionPane.showMessageDialog(this, "El libro ya está prestado o no se encontró", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un libro para realizar el préstamo", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generarHistorialPrestamos() {
        // Crear historial de prestamos para las graficas
        Libro[] librosHistorial = {
                new Libro("El Hobbit", "J.R.R. Tolkien", "9788445073803", 310),
                new Libro("La Odisea", "Homero", "9788491050742", 448),
                new Libro("El retrato de Dorian Gray", "Oscar Wilde", "9788491052005", 304),
                new Libro("Moby Dick", "Herman Melville", "9788491051572", 752)
        };

        for (Libro libro : librosHistorial) {
            biblioteca.agregarLibro(libro);
        }

        List<Usuario> usuarios = biblioteca.getUsuarios();

        for (int i = 0; i < 30; i++) {
            Libro libroSeleccionado = null;
            while (libroSeleccionado == null || libroSeleccionado.isPrestado()) {
                int indiceLibro = (int) (Math.random() * librosHistorial.length);
                libroSeleccionado = librosHistorial[indiceLibro];
            }

            int indiceUsuario = (int) (Math.random() * usuarios.size());
            Usuario usuarioSeleccionado = usuarios.get(indiceUsuario);

            if (usuarioSeleccionado.solicitarPrestamo(libroSeleccionado)) {
                libroSeleccionado.setPrestado(true);

                String idPrestamo = String.valueOf(Prestamo.generarId());
                Prestamo prestamo = new Prestamo(idPrestamo, usuarioSeleccionado, libroSeleccionado);

                biblioteca.agregarPrestamoHist(prestamo);

                if (i < 29) {
                    if (prestamo.procesarDevolucion()) {
                        libroSeleccionado.setPrestado(false);
                    }
                }
            }
        }

        System.out.println("Se generaron 30 préstamos y 29 devoluciones para el historial");
    }

    private void llenaBase() {
        biblioteca.agregarLibro(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424922498", 863));
        biblioteca.agregarLibro(new Libro("Cien años de soledad", "Gabriel García Márquez", "9780307474728", 417));
        biblioteca.agregarLibro(new Libro("El Principito", "Antoine de Saint-Exupéry", "9788498381498", 96));

        // añadir usuarios para pruebas
        biblioteca.agregarUsuario(new Usuario("Juan Pérez", "JP123", Usuario.USUARIO_REGULAR));
        biblioteca.agregarUsuario(new Usuario("María López", "ML456", Usuario.USUARIO_PROFESOR));
        biblioteca.agregarUsuario(new Usuario("Carlos Gómez", "CG789", Usuario.USUARIO_INVESTIGADOR));


        // Agregar un libro prestado y con multa para pruebas
        Libro libro1984 = new Libro("1984", "George Orwell", "9788499390944", 326);
        //Prestar libro
       Usuario usuario = biblioteca.buscarUsuarioPorID("JP123"); // Cambia "JP123" por el ID del usuario deseado
            if (usuario != null && usuario.solicitarPrestamo(libro1984)) {
                libro1984.setPrestado(true);
                Prestamo prestamo = new Prestamo(String.valueOf(Prestamo.generarId()), usuario, libro1984);
                biblioteca.agregarPrestamo(prestamo);
                // Crear y asignar una multa al libro
                Multa multa = new Multa(usuario, libro1984);
                multa.setMonto(500.0); // Supongamos que es de $500 la multa
                libro1984.setMulta(multa);
                biblioteca.agregarLibro(libro1984);
            }


            // Generar Historial para graficas
        generarHistorialPrestamos();

        actualizarTablaLibros(biblioteca.getLibros());
    }

    public BibliotecaGUI() {
        biblioteca = new Biblioteca("Biblioteca Central", "Puebla Puebla");
        setTitle("Sistema de Biblioteca - " + biblioteca.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearMenu();
        crearPanelPrincipal();
        llenaBase();
        setVisible(true);
    }

    public static void main(String[] args) {
        BibliotecaGUI gui = new BibliotecaGUI();
    }

}