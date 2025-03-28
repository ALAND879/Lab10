import java.awt.*;
import java.awt.geom.Line2D;

public class GraficoLineal extends GraficoBase {
    private static final int DIVISIONES_Y = 5;

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

        // Encontrar el valor máximo y redondear a la siguiente decena
        int maximo = calcularEscala();

        // Dibujar líneas de referencia horizontales y valores del eje Y
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{5}, 0));
        for (int i = 0; i <= DIVISIONES_Y; i++) {
            int y = 400 - margenInf - (i * altoGrafico / DIVISIONES_Y);
            g2d.draw(new Line2D.Double(margenIzq, y, 600 - margenDer, y));

            // Valores del eje Y
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            String valor = String.valueOf(i * maximo / DIVISIONES_Y);
            g2d.drawString(valor, margenIzq - 40, y + 5);
            g2d.setColor(Color.LIGHT_GRAY);
        }

        // Dibujar ejes
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(new Line2D.Double(margenIzq, margenSup, margenIzq, 400 - margenInf));
        g2d.draw(new Line2D.Double(margenIzq, 400 - margenInf, 600 - margenDer, 400 - margenInf));

        // Etiquetas de los ejes
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Valores", margenIzq - 50, margenSup - 10);
        g2d.drawString("Meses", 600 - margenDer - 30, 400 - margenInf + 40);

        // Dibujar líneas y puntos
        for (int i = 0; i < datos.length - 1; i++) {
            int x1 = margenIzq + i * (anchoGrafico / (datos.length - 1));
            int y1 = 400 - margenInf - (datos[i] * altoGrafico / maximo);
            int x2 = margenIzq + (i + 1) * (anchoGrafico / (datos.length - 1));
            int y2 = 400 - margenInf - (datos[i + 1] * altoGrafico / maximo);

            // Línea de conexión
            g2d.setColor(COLORES_BARRAS[i % COLORES_BARRAS.length]);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));

            // Puntos y valores
            g2d.fillOval(x1 - 4, y1 - 4, 8, 8);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(String.valueOf(datos[i]), x1 - 10, y1 - 10);

            // Etiquetas del eje X
            g2d.drawString(etiquetas[i], x1 - 20, 400 - margenInf + 20);
        }

        // Último punto y etiqueta
        int xLast = margenIzq + (datos.length - 1) * (anchoGrafico / (datos.length - 1));
        int yLast = 400 - margenInf - (datos[datos.length - 1] * altoGrafico / maximo);
        g2d.setColor(COLORES_BARRAS[(datos.length - 1) % COLORES_BARRAS.length]);
        g2d.fillOval(xLast - 4, yLast - 4, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(datos[datos.length - 1]), xLast - 10, yLast - 10);
        g2d.drawString(etiquetas[datos.length - 1], xLast - 20, 400 - margenInf + 20);
    }
}