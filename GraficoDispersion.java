import java.awt.*;
import java.awt.geom.*;

public class GraficoDispersion extends GraficoBase {
    public GraficoDispersion(String titulo, int[] datos, String[] etiquetas) {
        super(titulo, datos, etiquetas);
    }

    @Override
    public void dibujar(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Márgenes y dimensiones
        int margenIzq = 60;
        int margenDer = 60;
        int margenSup = 60;
        int margenInf = 60;
        int anchoGrafico = 600 - margenIzq - margenDer;
        int altoGrafico = 400 - margenSup - margenInf;

        // Dibujar título
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(new Color(34, 40, 49));
        g2d.drawString(titulo, 300 - g2d.getFontMetrics().stringWidth(titulo)/2, 30);

        // Encontrar el valor máximo y escala
        int maximo = calcularEscala();
        int numDivisiones = 5;
        int escala = (maximo + numDivisiones - 1) / numDivisiones * numDivisiones;

        // Dibujar líneas de referencia y etiquetas del eje Y
        g2d.setColor(new Color(200, 200, 200));
        g2d.setStroke(new BasicStroke(1.0f));
        for (int i = 0; i <= numDivisiones; i++) {
            int y = 400 - margenInf - (i * altoGrafico / numDivisiones);
            int valor = (i * escala) / numDivisiones;

            // Línea horizontal
            g2d.draw(new Line2D.Double(margenIzq, y, 600 - margenDer, y));

            // Etiqueta del eje Y
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(String.valueOf(valor), margenIzq - 25, y + 5);
            g2d.setColor(new Color(200, 200, 200));
        }

        // Dibujar ejes principales
        g2d.setColor(new Color(57, 62, 70));
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(new Line2D.Double(margenIzq, margenSup, margenIzq, 400 - margenInf));
        g2d.draw(new Line2D.Double(margenIzq, 400 - margenInf, 600 - margenDer, 400 - margenInf));

        // Etiqueta del eje Y
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.rotate(-Math.PI/2);
        g2d.drawString("Valores", -250, margenIzq - 40);
        g2d.rotate(Math.PI/2);

        // Etiqueta del eje X
        g2d.drawString("Meses", 300 - 25, 380);

        // Dibujar puntos y etiquetas
        for (int i = 0; i < datos.length; i++) {
            int x = margenIzq + i * (anchoGrafico / (datos.length - 1));
            int y = 400 - margenInf - (datos[i] * altoGrafico / escala);

            // Línea vertical punteada
            g2d.setColor(new Color(200, 200, 200));
            g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                        10.0f, new float[]{2.0f}, 0.0f));
            g2d.draw(new Line2D.Double(x, 400 - margenInf, x, y));

            // Punto de datos
            g2d.setColor(COLORES_BARRAS[i % COLORES_BARRAS.length]);
            g2d.setStroke(new BasicStroke(2));
            g2d.fill(new Ellipse2D.Double(x - 4, y - 4, 8, 8));

            // Etiquetas del eje X
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(etiquetas[i], x - g2d.getFontMetrics().stringWidth(etiquetas[i])/2,
                          400 - margenInf + 20);

            // Valor del punto
            g2d.drawString(String.valueOf(datos[i]), x - 10, y - 10);
        }
    }
}