import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class GraficoBarras extends GraficoBase {

    public GraficoBarras(String titulo, int[] datos, String[] etiquetas) {
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

        // Dibujar escalas en eje Y
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        int numDivisiones = 5;
        for (int i = 0; i <= numDivisiones; i++) {
            int y = 400 - margenInf - (i * altoGrafico / numDivisiones);
            int valor = i * maximo / numDivisiones;
            g2d.drawString(String.valueOf(valor), margenIzq - 30, y + 5);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.draw(new Line2D.Double(margenIzq, y, 600 - margenDer, y));
            g2d.setColor(Color.BLACK);
        }

        // Dibujar barras
        int anchoBarra = anchoGrafico / datos.length;
        for (int i = 0; i < datos.length; i++) {
            int altoBarra = (int) (datos[i] * altoGrafico / maximo);
            int x = margenIzq + i * anchoBarra + 10;
            int y = 400 - margenInf - altoBarra;

            g2d.setColor(COLORES_BARRAS[i % COLORES_BARRAS.length]);
            g2d.fill(new Rectangle2D.Double(x, y, anchoBarra - 20, altoBarra));

            g2d.setColor(Color.BLACK);
            g2d.drawString(etiquetas[i], x, 400 - margenInf + 20);
            g2d.drawString(String.valueOf(datos[i]), x + (anchoBarra - 20) / 2 - 10, y - 5);
        }

        // Dibujar leyenda
        int leyendaX = 600 - margenDer + 10;
        int leyendaY = margenSup;
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int i = 0; i < etiquetas.length; i++) {
            g2d.setColor(COLORES_BARRAS[i % COLORES_BARRAS.length]);
            g2d.fill(new Rectangle2D.Double(leyendaX, leyendaY + i * 20, 15, 15));
            g2d.setColor(Color.BLACK);
            g2d.drawString(etiquetas[i], leyendaX + 20, leyendaY + i * 20 + 12);
        }
    }
}