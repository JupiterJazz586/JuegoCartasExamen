import java.awt.Color;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {

    JPanel pnlJugador1, pnlJugador2;
    private Jugador jugador1 = new Jugador();
    private Jugador jugador2 = new Jugador();
    JTabbedPane tpJugadores;

    public FrmJuego() {
        setTitle("Juguemos al apuntado!");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        add(btnVerificar);

        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 40, getWidth() - 40, 150);
        add(tpJugadores);

        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(0, 255, 0));
        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));

        tpJugadores.add(pnlJugador1, "Martin Estrada Contreras");
        tpJugadores.add(pnlJugador2, "Raúl Vidal");

        btnRepartir.addActionListener(e -> {
            repartir();
        });

        btnVerificar.addActionListener(e -> {
            verificar();
        });
    }

    private void repartir() {
        jugador1.repartir();
        jugador2.repartir();
        jugador1.mostrar(pnlJugador1);
        jugador2.mostrar(pnlJugador2);
    }

    private void verificar() {
        String resultado = "";
        if (tpJugadores.getSelectedIndex() == 0) {
            resultado = jugador1.getGrupos();
        } else {
            resultado = jugador2.getGrupos();
        }
        JOptionPane.showMessageDialog(null, resultado);
    }
}
