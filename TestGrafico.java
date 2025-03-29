import java.awt.*;
import java.awt.geom.*;

public class TestGrafico extends Frame {
    private int tipoGrafico = 4; // 0: Biblioteca, 1: Barras, 2: Pastel, 3: Lineal, 4: Dispersion, 5: Radar
    private DiagramaBiblioteca graficaBiblioteca;
    private int[] valores = {4, 5, 2, 6, 7, 3, 9 };
    private String[] etiquetas = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio"};
    private GraficoPastel graficaPastel;
    private GraficoBarras graficaBarras;
    private GraficoLineal graficaLineal;
    private GraficoDispersion graficaDispersion;
    private GraficoRadar graficaRadar;


    public TestGrafico(String titulo) {
        super(titulo);

        if (this.tipoGrafico == 0) {
            graficaBiblioteca = new DiagramaBiblioteca();
            add(graficaBiblioteca,"Center");
        } else if (this.tipoGrafico == 1) {
            graficaBarras = new GraficoBarras("Estadísticas Mensuales", valores, etiquetas);
            Canvas canvas = new Canvas() {
                @Override
                public void paint(Graphics g) {
                    graficaBarras.dibujar((Graphics2D) g);
                }
            };
            add(canvas, "Center");
        } else if (this.tipoGrafico == 2) {
            graficaPastel = new GraficoPastel("Estadísticas Mensuales", valores, etiquetas);
            Canvas canvas = new Canvas() {
                @Override
                public void paint(Graphics g) {
                    graficaPastel.dibujar((Graphics2D) g);
                }
            };
            add(canvas, "Center");
        } else if (this.tipoGrafico == 3) {
            graficaLineal = new GraficoLineal("Estadísticas Mensuales", valores, etiquetas);
            Canvas canvas = new Canvas() {
                @Override
                public void paint(Graphics g) {
                    graficaLineal.dibujar((Graphics2D) g);
                }
            };
            add(canvas, "Center");
        } else if (this.tipoGrafico == 4) {
            graficaDispersion = new GraficoDispersion("Estadísticas Mensuales", valores, etiquetas);
            Canvas canvas = new Canvas() {
                @Override
                public void paint(Graphics g) {
                    graficaDispersion.dibujar((Graphics2D) g);
                }
            };
            add(canvas, "Center");
        }else if (this.tipoGrafico == 5) {
            graficaRadar = new GraficoRadar("Estadísticas Mensuales", valores, etiquetas);
            Canvas canvas = new Canvas() {
                @Override
                public void paint(Graphics g) {
                    graficaRadar.dibujar((Graphics2D) g);
                }
            };
            add(canvas, "Center");
        }
        else {
            System.out.println("Tipo de gráfico no válido");
        }


    }

    public boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_DESTROY) {
            hide();
            dispose();
            return true;
        }
        return super.handleEvent(e);
    }

    public static void main(String[] args) {
        TestGrafico  grafico = new TestGrafico ("Grafico");
        grafico.resize(600,400);
        grafico.show();
    }

    public int getTipoGrafico() {
        return tipoGrafico;
    }
}