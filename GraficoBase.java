import java.awt.*;

public abstract class GraficoBase implements IGrafico {
    protected String titulo;
    protected int[] datos;
    protected String[] etiquetas;
    protected static final Color[] COLORES_BARRAS = {
            new Color(65, 105, 225),
            new Color(220, 20, 60),
            new Color(50, 205, 50),
            new Color(255, 140, 0),
            new Color(138, 43, 226),
            new Color(30, 144, 255),
            new Color(255, 215, 0)
    };

    public GraficoBase(String titulo, int[] datos, String[] etiquetas) {
        this.titulo = titulo;
        this.datos = datos;
        this.etiquetas = etiquetas;
    }

    protected int calcularEscala() {
        int maximo = 0;
        for (double dato : datos) {
            maximo = Math.max(maximo, (int) dato);
        }
        return ((maximo + 9) / 10) * 10;
    }

    @Override
    public void actualizarDatos(int[] datos) {
        this.datos = datos;
    }

    @Override
    public void setEtiquetas(String[] etiquetas) {
        this.etiquetas = etiquetas;
    }
}