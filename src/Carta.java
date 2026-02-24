import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Carta {

    private int indice;

    public Carta(Random r) {
        this.indice = r.nextInt(52) + 1;
    }

    public int getIndice() {
        return this.indice;
    }

    public void mostrar(int x, int y, JPanel pnl) {
        JLabel lbl = new JLabel();
        String nombreArchivo = "imagenes/carta" + this.indice + ".jpg";
        ImageIcon imagen = new ImageIcon(getClass().getResource(nombreArchivo));
        lbl.setIcon(imagen);
        lbl.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
        pnl.add(lbl);

        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JOptionPane.showMessageDialog(null,getNombre().toString() + " de " +getPinta().toString());
            }
        });
    }

    public Pinta getPinta() {
        if (indice <= 13) {
            return Pinta.TREBOL;
        } else if (indice <= 26) {
            return Pinta.PICA;
        } else if (indice <= 39) {
            return Pinta.CORAZON;
        } else {
            return Pinta.DIAMANTE;
        }
    }

    public NombreCarta getNombre() {
        int residuo = indice % 13;
        if (residuo == 0)
            residuo = 13;
        return NombreCarta.values()[residuo - 1];
    }

}
