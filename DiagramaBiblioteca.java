import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;
import java.util.Random;

public class DiagramaBiblioteca extends Canvas implements MouseListener, MouseMotionListener {
    private boolean esModoOscuro = true;
    private Color[][] estadoCubiculos = new Color[4][4];
    private Color[] estadoMesas = new Color[6];
    private Rectangle botonTemaArea;


    public DiagramaBiblioteca() {
        botonTemaArea = new Rectangle(600, 550, 120, 30);
        addMouseListener(this);
        addMouseMotionListener(this);
        Random aleatorio = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                estadoCubiculos[i][j] = obtenerEstadoAleatorio(aleatorio);
            }
        }
        for (int i = 0; i < 6; i++) {
            estadoMesas[i] = obtenerEstadoAleatorio(aleatorio);
        }
    }




    private Color obtenerEstadoAleatorio(Random aleatorio) {
        int estado = aleatorio.nextInt(3);
        return estado == 0 ? Color.GREEN : estado == 1 ? Color.RED : Color.YELLOW;
    }

    public void alternarModoOscuro() {
        esModoOscuro = !esModoOscuro;
        repaint();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3.0f));

        // Fondo
        g2d.setColor(esModoOscuro ? Color.DARK_GRAY : Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Boton tema
        g2d.setColor(esModoOscuro ? Color.DARK_GRAY : Color.LIGHT_GRAY);
        g2d.fill(botonTemaArea);
        g2d.setColor(esModoOscuro ? Color.WHITE : Color.BLACK);
        g2d.drawString("Cambiar Tema", 615, 570);

        // Marco rojo
        if (esModoOscuro) {
            g2d.setColor(new Color(0, 180, 0));
        } else {
            g2d.setColor(Color.RED);
        }
        g2d.drawRect(40, 40, 720, 520);

        // Estantes
        GradientPaint gp;
        if (esModoOscuro) {
            gp = new GradientPaint(0, 0, new Color(100, 100, 255), 80, 0, new Color(0, 0, 255));
        } else {
            gp = new GradientPaint(0, 0, new Color(160, 160, 160), 80, 0, new Color(90, 120, 120));
        }
        g2d.setPaint(gp);

        int[] estX = {80, 600};
        int[] estY = {100, 200, 300, 400};
        for (int x : estX) {
            for (int y : estY) {
                g2d.fill(new Rectangle2D.Double(x, y, 120, 60));
            }
        }
        if (esModoOscuro){
            g2d.setColor(Color.WHITE);
        }
        else{
            g2d.setColor(Color.BLACK);
        }
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        String[] etiquetas = {"100-200", "201-300", "301-400", "401-500", "501-600", "601-700", "701-800", "801-900"};
        for (int i = 0; i < etiquetas.length; i++) {
            int x = (i < 4) ? 110 : 630;
            int y = 135 + (i % 4) * 100;
            g2d.drawString(etiquetas[i], x, y);
        }

        // Recepción
        GradientPaint gpMostrador;
        if (esModoOscuro) {
            // Color turquesa
            gpMostrador = new GradientPaint(200, 60, new Color(64, 224, 208), 600, 85, new Color(0, 128, 128));
        } else {
            // Color café
            gpMostrador = new GradientPaint(200, 60, new Color(139, 69, 19), 600, 85, new Color(101, 67, 33));
        }
        g2d.setPaint(gpMostrador);
        g2d.fill(new Rectangle2D.Double(200, 60, 400, 25));

        // Textos
        if (esModoOscuro){
            g2d.setColor(Color.WHITE);
        }
        else{
            g2d.setColor(Color.BLACK);
        }
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Entrada", 370, 35);
        g2d.drawString("Área de Lectura", 360, 520);

        if (esModoOscuro){
            g2d.setColor(Color.BLACK);
        }
        else{
            g2d.setColor(Color.WHITE);
        }
        g2d.drawString("Recepción", 365, 75);

        // Cubículos con colores
        int inicioX = 270, inicioY = 110;
        for (int fila = 0; fila < 4; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                int x = inicioX + (columna * 70);
                int y = inicioY + (fila * 70);
                g2d.setColor(estadoCubiculos[fila][columna]);
                g2d.fill(new Rectangle2D.Float(x, y, 50, 50));
                g2d.setColor(Color.BLACK);
                g2d.drawString("C" + (fila * 4 + columna + 1), x + 18, y + 30);
            }
        }

        // Mesas con estados
        int[][] posicionesMesas = {{278, 400}, {378, 400}, {478, 400}, {278, 450}, {378, 450}, {478, 450}};
        for (int i = 0; i < 6; i++) {
            g2d.setColor(estadoMesas[i]);
            g2d.fill(new Ellipse2D.Float(posicionesMesas[i][0], posicionesMesas[i][1], 40, 40));
            g2d.setColor(Color.BLACK);
            g2d.drawString("M" + (i + 1), posicionesMesas[i][0] + 12, posicionesMesas[i][1] + 25);
        }


        g2d.setColor(Color.GREEN);
        g2d.fillRect(120, 590, 20, 20);

        g2d.setColor(Color.RED);
        g2d.fillRect(220, 590, 20, 20);

        g2d.setColor(Color.YELLOW);
        g2d.fillRect(320, 590, 20, 20);

        if (esModoOscuro){
            g2d.setColor(Color.WHITE);
        }
        else{
            g2d.setColor(Color.BLACK);
        }

        g2d.drawString("Ocupado", 250, 605);
        g2d.drawString("Reservado", 350, 605);
        g2d.drawString("Leyenda:", 50, 600);
        g2d.drawString("Disponible", 150, 605);
    }

    private boolean isDentroCubiculo(int x, int y) {
        int inicioX = 270, inicioY = 110;
        for (int fila = 0; fila < 4; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                int cubiculoX = inicioX + (columna * 70);
                int cubiculoY = inicioY + (fila * 70);
                if (x >= cubiculoX && x <= cubiculoX + 50 && y >= cubiculoY && y <= cubiculoY + 50) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDentroMesa(int x, int y) {
        int[][] posicionesMesas = {{278, 400}, {378, 400}, {478, 400}, {278, 450}, {378, 450}, {478, 450}};
        for (int i = 0; i < 6; i++) {
            int mesaX = posicionesMesas[i][0];
            int mesaY = posicionesMesas[i][1];
            if (x >= mesaX && x <= mesaX + 40 && y >= mesaY && y <= mesaY + 40) {
                return true;
            }
        }
        return false;
    }

    private void cambiarEstadoCubiculo(int x, int y) {
        int inicioX = 270, inicioY = 110;
        for (int fila = 0; fila < 4; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                int cubiculoX = inicioX + (columna * 70);
                int cubiculoY = inicioY + (fila * 70);
                if (x >= cubiculoX && x <= cubiculoX + 50 && y >= cubiculoY && y <= cubiculoY + 50) {
                    estadoCubiculos[fila][columna] = obtenerEstadoAleatorio(new Random());
                    repaint();
                    return;
                }
            }
        }
    }

    private void cambiarEstadoMesa(int x, int y) {
        int[][] posicionesMesas = {{278, 400}, {378, 400}, {478, 400}, {278, 450}, {378, 450}, {478, 450}};
        for (int i = 0; i < 6; i++) {
            int mesaX = posicionesMesas[i][0];
            int mesaY = posicionesMesas[i][1];
            if (x >= mesaX && x <= mesaX + 40 && y >= mesaY && y <= mesaY + 40) {
                estadoMesas[i] = obtenerEstadoAleatorio(new Random());
                repaint();
                return;
            }
        }
    }

    private void mostrarInformacion(int x, int y) {
        int inicioX = 270, inicioY = 110;
        for (int fila = 0; fila < 4; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                int cubiculoX = inicioX + (columna * 70);
                int cubiculoY = inicioY + (fila * 70);
                if (x >= cubiculoX && x <= cubiculoX + 50 && y >= cubiculoY && y <= cubiculoY + 50) {
                    Color estado = estadoCubiculos[fila][columna];
                    String mensaje = "Cubículo C" + (fila * 4 + columna + 1) + " está " +
                            (estado == Color.GREEN ? "Disponible" : estado == Color.RED ? "Ocupado" : "Reservado");
                    JOptionPane.showMessageDialog(this, mensaje, "Estado del Cubículo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (isDentroCubiculo(x, y)) {
            mostrarInformacion(x, y); // Mostrar "Cubículo Ocupado/Desocupado/ Apartado"
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (botonTemaArea.contains(e.getPoint())) {
            alternarModoOscuro();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}