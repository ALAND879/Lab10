import java.awt.*;
import java.awt.geom.*;

public class GraficoEstadisticas extends Canvas {
    private int[] datos;
    private String[] etiquetas;

    public GraficoEstadisticas(int[] datos, String[] etiquetas) {
        this.datos = datos;
        this.etiquetas = etiquetas;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int margen = 40;
        int anchoGrafico = getWidth() - 2 * margen;
        int altoGrafico = getHeight() - 2 * margen;
        int maximo = 0;
        for(int valor : datos) {
            maximo = Math.max(maximo, valor);
        }
        int anchoBarra = anchoGrafico / datos.length;

        for(int i = 0; i < datos.length; i++) {
            int altoBarra = (int)((datos[i] * altoGrafico) / maximo);
            int x = margen + i * anchoBarra;
            int y = getHeight() - margen - altoBarra;
            g2d.setColor(new Color(0, 100, 200));
            g2d.fill(new Rectangle2D.Double(x, y, anchoBarra-5, altoBarra));
            g2d.setColor(Color.BLACK);
            g2d.drawString(etiquetas[i], x, getHeight() - margen/2);
            g2d.drawString(String.valueOf(datos[i]), x, y - 5);
        }

    }


}
