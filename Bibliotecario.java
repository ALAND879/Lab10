import java.util.*;

public class Bibliotecario extends Empleado {
        private List<Prestamo> prestamosPendientes;
        private List<Libro> librosPorCatalogar;

        public Bibliotecario(String nombre, String id, double salario) {
            super(nombre, id, salario, "Bibliotecario");
            this.prestamosPendientes = new ArrayList<>();
            this.librosPorCatalogar = new ArrayList<>();
            setNivelAutorizacion(NIVEL_TECNICO);
        }

        public void gestionarPrestamos() {
            System.out.println("\nGestión de préstamos por " + getNombre());
            if (prestamosPendientes.isEmpty()) {
                System.out.println("No hay préstamos pendientes.");
                return;
            }

            for (Prestamo prestamo : prestamosPendientes) {
                prestamo.verificarEstado();
                System.out.println("Préstamo: " + prestamo.getId());
                System.out.println("Usuario: " + prestamo.getUsuario().getNombre());
                System.out.println("Libro: " + prestamo.getLibro().getTitulo());
                System.out.println("Estado: " +
                    (prestamo.getEstado() == Prestamo.ACTIVO ? "Activo" :
                     prestamo.getEstado() == Prestamo.VENCIDO ? "Vencido" : "Devuelto"));
            }
        }

        public void catalogarLibros() {
            System.out.println("\nCatalogación de libros por " + getNombre());
            if (librosPorCatalogar.isEmpty()) {
                System.out.println("No hay libros pendientes por catalogar.");
                return;
            }

            for (Libro libro : librosPorCatalogar) {
                System.out.println("Catalogando: " + libro.getTitulo());
                System.out.println("ISBN: " + libro.getIsbn());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("Estado: " + libro.getEstado());
            }
        }

        public void agregarPrestamoPendiente(Prestamo prestamo) {
            if (!prestamosPendientes.contains(prestamo)) {
                prestamosPendientes.add(prestamo);
                System.out.println("Préstamo agregado a la lista de pendientes.");
            }
        }

        public void agregarLibroPorCatalogar(Libro libro) {
            if (!librosPorCatalogar.contains(libro)) {
                librosPorCatalogar.add(libro);
                System.out.println("Libro añadido a la lista de catalogación.");
            }
        }

        public List<Prestamo> getPrestamosPendientes() {
            return new ArrayList<>(prestamosPendientes);
        }

        public List<Libro> getLibrosPorCatalogar() {
            return new ArrayList<>(librosPorCatalogar);
        }
    }