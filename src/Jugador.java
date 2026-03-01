import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private  int MARGEN_IZQUIERDA = 10;
    private  int DISTANCIA_ENTRE_CARTAS = 50;
    private  int MARGEN_SUPERIOR = 10;

    private Random r = new Random();
    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        pnl.setLayout(null);
        int posicion = MARGEN_IZQUIERDA + DISTANCIA_ENTRE_CARTAS * (TOTAL_CARTAS - 1);
        for (Carta andrea : cartas) {
            andrea.mostrar(posicion, MARGEN_SUPERIOR, pnl);
            posicion -= DISTANCIA_ENTRE_CARTAS;
        }
        pnl.repaint();
    }

    // Devuelve una descripción de los grupos encontrados
     
    public String getGrupos() {
        String resultado = "No se encontraron grupos";

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }
        boolean hayGrupos = false;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                hayGrupos = true;
                break;
            }
        }
        if (hayGrupos) {
            resultado = "Se encontraron los siguientes grupos:\n";
            int indice = 0;
            for (int contador : contadores) {
                if (contador >= 2) {
                    resultado += Grupo.values()[contador].toString() + " de " + NombreCarta.values()[indice].toString()
                            + "\n";
                }
                indice++;
            }
        }
        return resultado;
    }


    //  devolvemos una descripción de las escaleras encontradas (secuencias de al menos 4 cartas consecutivas de la misma pinta).
    
    public String getEscaleras() {
        String resultado = "No se encontraron escaleras";
        boolean hayEscalera = false;

        // agrupar índices de cartas por pinta
        java.util.Map<Pinta, java.util.List<Integer>> porPinta = new java.util.EnumMap<>(Pinta.class);
        for (int i = 0; i < cartas.length; i++) {
            Pinta pinta = cartas[i].getPinta();
            porPinta.computeIfAbsent(pinta, k -> new java.util.ArrayList<>()).add(i);
        }

        StringBuilder sb = new StringBuilder();
        for (java.util.Map.Entry<Pinta, java.util.List<Integer>> entry : porPinta.entrySet()) {
            java.util.List<Integer> indices = entry.getValue();
            // ordenar por nombre de carta
            indices.sort(java.util.Comparator.comparingInt(i -> cartas[i].getNombre().ordinal()));

            int count = 1;
            int start = 0;
            for (int j = 1; j < indices.size(); j++) {
                int prev = cartas[indices.get(j - 1)].getNombre().ordinal();
            int curr = cartas[indices.get(j)].getNombre().ordinal();
                if (curr == prev + 1) {
                    count++;
                } else {
                    if (count >= 4) {
                        hayEscalera = true;
                        sb.append(buildEscaleraString(indices, start, start + count - 1, entry.getKey()));
                    }
                    start = j;
                    count = 1;
                }
            }
            // última secuencia
            if (count >= 4) {
                hayEscalera = true;
                sb.append(buildEscaleraString(indices, start, indices.size() - 1, entry.getKey()));
            }
        }

        if (hayEscalera) {
            resultado = "Se encontraron las siguientes escaleras:\n" + sb.toString();
        }
        return resultado;
    }

    private String buildEscaleraString(java.util.List<Integer> indices, int from, int to, Pinta pinta) {
        StringBuilder s = new StringBuilder();
        for (int k = from; k <= to; k++) {
            s.append(cartas[indices.get(k)].getNombre().toString());
            if (k < to) {
                s.append(", ");
            }
        }
        s.append(" de ").append(pinta.toString()).append("\n");
        return s.toString();
    }

    
     //  Calcula el puntaje del jugador

    public int getPuntaje() {
        boolean[] usados = obtenerCartasUsadasEnFiguras();
        int puntaje = 0;
        for (int i = 0; i < cartas.length; i++) {
            if (!usados[i]) {
                NombreCarta n = cartas[i].getNombre();
                switch (n) {
                    case AS:
                        puntaje += 10;
                        break;
                    case JACK:
                        puntaje += 10;
                        break;
                    case QUEEN:
                        puntaje += 10;
                        break;
                    case KING:
                        puntaje += 10;
                        break;
                    default:
                        
                        puntaje += n.ordinal() + 1;
                        break;
                }
            }
        }
        return puntaje;
    }

   
    //  Indica qué posiciones del arreglo de cartas están incluidas en grupos o escalerass

    private boolean[] obtenerCartasUsadasEnFiguras() {
        boolean[] usados = new boolean[cartas.length];

        // marcar cartas que pertenecen a grupos (par, terna, etc.)
        java.util.Map<NombreCarta, java.util.List<Integer>> porNombre = new java.util.HashMap<>();
        for (int i = 0; i < cartas.length; i++) {
            NombreCarta nombre = cartas[i].getNombre();
            porNombre.computeIfAbsent(nombre, k -> new java.util.ArrayList<>()).add(i);
        }
        for (java.util.List<Integer> lista : porNombre.values()) {
            if (lista.size() >= 2) {
                for (int idx : lista) {
                    usados[idx] = true;
                }
            }
        }

        // marcar cartas que pertenecen a escaleras
        java.util.Map<Pinta, java.util.List<Integer>> porPinta = new java.util.EnumMap<>(Pinta.class);
        for (int i = 0; i < cartas.length; i++) {
            Pinta pinta = cartas[i].getPinta();
            porPinta.computeIfAbsent(pinta, k -> new java.util.ArrayList<>()).add(i);
        }
        for (java.util.Map.Entry<Pinta, java.util.List<Integer>> entry : porPinta.entrySet()) {
            java.util.List<Integer> indices = entry.getValue();
            indices.sort(java.util.Comparator.comparingInt(i -> cartas[i].getNombre().ordinal()));
            int count = 1;
            int start = 0;
            for (int j = 1; j < indices.size(); j++) {
                int prev = cartas[indices.get(j - 1)].getNombre().ordinal();
                int curr = cartas[indices.get(j)].getNombre().ordinal();
                if (curr == prev + 1) {
                    count++;
                } else {
                    if (count >= 4) {
                        for (int k = start; k < start + count; k++) {
                            usados[indices.get(k)] = true;
                        }
                    }
                    start = j;
                    count = 1;
                }
            }
            if (count >= 4) {
                for (int k = indices.size() - count; k < indices.size(); k++) {
                    usados[indices.get(k)] = true;
                }
            }
        }

        return usados;
    }
}

