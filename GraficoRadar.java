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
        int centroX = margenIzq + anchoGrafico / 2;
        int centroY = margenSup + altoGrafico / 2;

        // Dibujar círculos concéntricos
        g2d.setColor(new Color(200, 200, 200, 100));
        for (int i = 1; i <= 5; i++) {
            int r = (radio * i) / 5;
            g2d.draw(new Ellipse2D.Double(centroX - r, centroY - r, r * 2, r * 2));
        }

        // Dibujar ejes
        g2d.setColor(new Color(57, 62, 70));
        g2d.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < datos.length; i++) {
            double angle = 2 * Math.PI * i / datos.length;
            int x = (int) (centroX + radio * Math.cos(angle));
            int y = (int) (centroY + radio * Math.sin(angle));
            g2d.draw(new Line2D.Double(centroX, centroY, x, y));
            g2d.drawString(etiquetas[i], x + 5, y - 5);
        }

        // Dibujar datos con relleno
        Polygon polygon = new Polygon();
        g2d.setColor(new Color(62, 226, 187, 159));
        for (int i = 0; i < datos.length; i++) {
            double angle = 2 * Math.PI * i / datos.length;
            int x = (int) (centroX + radio * datos[i] / maximo * Math.cos(angle));
            int y = (int) (centroY + radio * datos[i] / maximo * Math.sin(angle));
            polygon.addPoint(x, y);
        }
        g2d.fillPolygon(polygon);

        // Dibujar contorno del radar
        g2d.setColor(new Color(0, 123, 255));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawPolygon(polygon);

        // Dibjar puntos de datos
        for (int i = 0; i < datos.length; i++) {
            double angle = 2 * Math.PI * i / datos.length;
            int x = (int) (centroX + radio * datos[i] / maximo * Math.cos(angle));
            int y = (int) (centroY + radio * datos[i] / maximo * Math.sin(angle));
            g2d.fill(new Ellipse2D.Double(x - 3, y - 3, 6, 6));
        }
    }
}
