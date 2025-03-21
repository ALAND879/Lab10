import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class DiagramaBiblioteca extends Canvas{
    private Button btnModoOscuro;
    private boolean esModoOscuro = true;
    private Color[][] estadoCubiculos = new Color[4][4];
    private Color[] estadoMesas = new Color[6];

    public DiagramaBiblioteca() {

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

        // Marco rojo
        g2d.setColor(new Color(180, 0, 0));
        g2d.drawRect(40, 40, 720, 520);

        // Laterales
        GradientPaint gp = new GradientPaint(0, 0, new Color(160, 160, 160), 80, 0, new Color(90, 120, 120));
        g2d.setPaint(gp);

        int[] estX = {80, 600};
        int[] estY = {100, 200, 300, 400};
        for (int x : estX) {
            for (int y : estY) {
                g2d.fill(new Rectangle2D.Double(x, y, 120, 60));
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        String[] etiquetas = {"100-200", "201-300", "301-400", "401-500", "501-600", "601-700", "701-800", "801-900"};
        for (int i = 0; i < etiquetas.length; i++) {
            int x = (i < 4) ? 110 : 630;
            int y = 135 + (i % 4) * 100;
            g2d.drawString(etiquetas[i], x, y);
        }

        // Recepción
        GradientPaint gpMostrador = new GradientPaint(200, 60, new Color(220, 0, 0), 600, 85, new Color(180, 0, 0));
        g2d.setPaint(gpMostrador);
        g2d.fill(new Rectangle2D.Double(200, 60, 400, 25));

        // Textos
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Entrada", 370, 35);
        g2d.drawString("Recepción", 365, 75);
        g2d.drawString("Área de Lectura", 360, 520);


        // Dibujar cubículos con colores de ocupación
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

        // Dibujar mesas con estados
        int[][] posicionesMesas = {{278, 400}, {378, 400}, {478, 400}, {278, 450}, {378, 450}, {478, 450}};
        for (int i = 0; i < 6; i++) {
            g2d.setColor(estadoMesas[i]);
            g2d.fill(new Ellipse2D.Float(posicionesMesas[i][0], posicionesMesas[i][1], 40, 40));
            g2d.setColor(Color.BLACK);
            g2d.drawString("M" + (i + 1), posicionesMesas[i][0] + 12, posicionesMesas[i][1] + 25);
        }

        // Leyenda
        g2d.setColor(Color.BLACK);
        g2d.drawString("Leyenda:", 50, 600);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(120, 590, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Disponible", 150, 605);
        g2d.setColor(Color.RED);
        g2d.fillRect(220, 590, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Ocupado", 250, 605);
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(320, 590, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Reservado", 350, 605);

    }


}