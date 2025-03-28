import java.awt.*;
import java.awt.geom.Line2D;

public class GraficoLineal extends GraficoBase {

    public GraficoLineal(String titulo, int[] datos, String[] etiquetas) {
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

        // Dibujar título
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(titulo, 300 - 80, 30);

        // Encontrar el valor máximo
        int maximo = calcularEscala();

        // Dibujar ejes
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(new Line2D.Double(margenIzq, margenSup, margenIzq, 400 - margenInf));
        g2d.draw(new Line2D.Double(margenIzq, 400 - margenInf, 600 - margenDer, 400 - margenInf));

        // Dibujar líneas y etiquetas
        for (int i = 0; i < datos.length - 1; i++) {
            int x1 = margenIzq + i * (anchoGrafico / (datos.length - 1));
            int y1 = 400 - margenInf - (datos[i] * altoGrafico / maximo);
            int x2 = margenIzq + (i + 1) * (anchoGrafico / (datos.length - 1));
            int y2 = 400 - margenInf - (datos[i + 1] * altoGrafico / maximo);
            g2d.setColor(COLORES_BARRAS[i % COLORES_BARRAS.length]);
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));

            // Dibujar etiquetas
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            g2d.drawString(etiquetas[i], x1 + 5, y1 - 5);
        }

        // Dibujar la última etiqueta
        int xLast = margenIzq + (datos.length - 1) * (anchoGrafico / (datos.length - 1));
        int yLast = 400 - margenInf - (datos[datos.length - 1] * altoGrafico / maximo);
        g2d.drawString(etiquetas[datos.length - 1], xLast + 5, yLast - 5);
    }
}