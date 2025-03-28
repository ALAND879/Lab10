import java.awt.*;

public interface IGrafico {
    void dibujar(Graphics2D g2d);
    void actualizarDatos(int[] datos);
    void setEtiquetas(String[] etiquetas);
}