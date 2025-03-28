import java.awt.*;
import java.awt.geom.*;

public class TestGrafico extends Frame {

    //  Grafuca de la biblioteca (DIbujo)
    // private DiagramaBiblioteca grafica;// Diagrama Biblioteca

    // Graficos de barras
    private int[] valores = {4, 5, 2, 6, 7, 3, 9 };
    private String[] etiquetas = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio"};
    private GraficoEstadisticas grafica;

    public TestGrafico(String titulo) {
        super(titulo);
        // Aquí creamos la instancia del componente gráfico a crear
       // DiagramaBiblioteca grafica = new DiagramaBiblioteca(); //Biblioteca

        GraficoEstadisticas grafica = new GraficoEstadisticas(valores, etiquetas); // Grafico de barras
        add(grafica,"Center");
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

}