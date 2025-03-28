import java.awt.*;
import java.awt.geom.Arc2D;

public class GraficoPastel extends GraficoBase {

    public GraficoPastel(String titulo, int[] datos, String[] etiquetas) {
        super(titulo, datos, etiquetas);
    }

    @Override
    public void dibujar(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar título
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(titulo, 300 - 80, 30);

        // Calcular el total de los datos
        int total = 0;
        for (double dato : datos) {
            total += dato;
        }

        // Dibujar gráfico de pastel
        int diametro = Math.min(600 - 100, 400 - 100);
        int x = (600 - diametro) / 2;
        int y = (400 - diametro) / 2;
        int anguloInicio = 0;

        for (int i = 0; i < datos.length; i++) {
            int angulo = (int) Math.round(360.0 * datos[i] / total);
            g2d.setColor(COLORES_BARRAS[i % COLORES_BARRAS.length]);
            g2d.fill(new Arc2D.Double(x, y, diametro, diametro, anguloInicio, angulo, Arc2D.PIE));

            double porcentaje = (datos[i] * 100.0) / total;
            double anguloMedio = Math.toRadians(anguloInicio + angulo / 2.0);
            int textoX = (int) (x + diametro / 2 + (diametro / 2.5) * Math.cos(anguloMedio));
            int textoY = (int) (y + diametro / 2 - (diametro / 2.5) * Math.sin(anguloMedio));
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.format("%.1f%%", porcentaje), textoX - 15, textoY + 5);

            anguloInicio += angulo;
        }

        // Dibujar leyenda
        int leyendaX = 600 - 100;
        int leyendaY = 50;
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int i = 0; i < etiquetas.length; i++) {
            double porcentaje = (datos[i] * 100.0) / total;
            g2d.setColor(COLORES_BARRAS[i % COLORES_BARRAS.length]);
            g2d.fill(new Rectangle(leyendaX, leyendaY + i * 20, 15, 15));
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.format("%s (%d - %.1f%%)", etiquetas[i], datos[i], porcentaje),
                          leyendaX + 20, leyendaY + i * 20 + 12);
        }
    }
}