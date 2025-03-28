import java.awt.*;
import java.awt.geom.*;

public class GraficoRadar extends GraficoBase {

    public GraficoRadar(String titulo, int[] datos, String[] etiquetas) {
        super(titulo, datos, etiquetas);
    }

    @Override
    public void dibujar(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Márgenes y dimensiones
        int margenIzq = 60;
        int margenDer = 100;
        int margenSup = 60;
        int margenInf = 60;
        int anchoGrafico = 600 - margenIzq - margenDer;
        int altoGrafico = 400 - margenSup - margenInf;
        int radio = Math.min(anchoGrafico, altoGrafico) / 2;

        // Dibujar título
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(new Color(34, 40, 49));
        g2d.drawString(titulo, 300 - 80, 30);

        // Encontrar el valor máximo
        int maximo = calcularEscala();

        // Dibujar ejes
        g2d.setColor(new Color(57, 62, 70));
        g2d.setStroke(new BasicStroke(1.5f));
        int centroX = margenIzq + anchoGrafico / 2;
        int centroY = margenSup + altoGrafico / 2;

        for (int i = 0; i < datos.length; i++) {
            double angle = 2 * Math.PI * i / datos.length;
            int x = (int) (centroX + radio * Math.cos(angle));
            int y = (int) (centroY + radio * Math.sin(angle));
            g2d.draw(new Line2D.Double(centroX, centroY, x, y));
        }

        // Dibujar datos
        g2d.setColor(new Color(0, 123, 255)); // Azul
        g2d.setStroke(new BasicStroke(2f));
        for (int i = 0; i < datos.length; i++) {
            double angle = 2 * Math.PI * i / datos.length;
            int x = (int) (centroX + radio * datos[i] / maximo * Math.cos(angle));
            int y = (int) (centroY + radio * datos[i] / maximo * Math.sin(angle));
            if (i > 0) {
                double prevAngle = 2 * Math.PI * (i - 1) / datos.length;
                int prevX = (int) (centroX + radio * datos[i - 1] / maximo * Math.cos(prevAngle));
                int prevY = (int) (centroY + radio * datos[i - 1] / maximo * Math.sin(prevAngle));
                g2d.draw(new Line2D.Double(prevX, prevY, x, y));
            }
            g2d.fill(new Ellipse2D.Double(x - 3, y - 3, 6, 6));

            // Dibujar etiquetas
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            g2d.drawString(etiquetas[i], x + 5, y - 5);
        }

        double lastAngle = 2 * Math.PI * (datos.length - 1) / datos.length;
        int lastX = (int) (centroX + radio * datos[datos.length - 1] / maximo * Math.cos(lastAngle));
        int lastY = (int) (centroY + radio * datos[datos.length - 1] / maximo * Math.sin(lastAngle));
        g2d.draw(new Line2D.Double(lastX, lastY, centroX + radio * datos[0] / maximo * Math.cos(0), centroY + radio * datos[0] / maximo * Math.sin(0)));
    }
}