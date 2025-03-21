import java.awt.*;
import java.awt.geom.*;

public class TestGrafico extends Frame {

    //  Aquí definimos el manejador de la clase que nos interesa probar (atributo de la clase)
    private DiagramaBiblioteca grafica;

    public TestGrafico(String titulo) {
        super(titulo);
        // Aquí creamos la instancia del componente gráfico a crear
        DiagramaBiblioteca grafica = new DiagramaBiblioteca();
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