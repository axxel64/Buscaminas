package buscaminas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BotonAccion extends MouseAdapter {

    private Celda celda;
    private Tablero tablero;
    private Celda[][] celdas;
    private Estadisticas estadisticas;

    public BotonAccion(Celda celda, Tablero tablero, Estadisticas estadisticas) {
        this.celda = celda;
        this.tablero = tablero;
        this.celdas = tablero.getCeldas();
        this.estadisticas = estadisticas;
    }

    public void mouseClicked(MouseEvent e) {
        if (!tablero.isJuegoAcabado()) {
            jugar(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (!tablero.isJuegoAcabado()) {
            estadisticas.getBtnSmiley().setIcon(estadisticas.getSurprise());
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (!tablero.isJuegoAcabado()) {
            estadisticas.getBtnSmiley().setIcon(estadisticas.getSmiley());
        }
    }

    public void jugar(MouseEvent e) {

        Celda celda = this.celda;

        // Click bot�n izquierdo
        if (e.getButton() == e.BUTTON1 && celda.getEstado() != 2) {
            if (celda.tieneMina()) {
                celda.setEstado(1);
                finJuego(estadisticas.getCry());
                celda.destaparCelda();

            } else {
                vaciar(celda.getX(), celda.getY());
                IA(/*celda.getX(), celda.getY()*/);
            }

            // Click bot�n derecho
        } else if (e.getButton() == e.BUTTON3) {
            if (celda.getEstado() == 2) {
                celda.setEstado(0);
                celda.quitarBandera();
                estadisticas.sumarBandera();
                IA();
            } else if (celda.getEstado() == 0
                    && Integer.valueOf(estadisticas.getMarcadorBanderas()
                            .getText()) > 0) {
                celda.setEstado(2);
                celda.ponerBandera();
                estadisticas.restarBandera();
                IA();
                if (Integer.valueOf(estadisticas.getMarcadorBanderas()
                        .getText()) == 0) {
                    if (tablero.comprobar()) {
                        finJuego(estadisticas.getLOL());
                        JOptionPane
                                .showMessageDialog(new JFrame(),
                                        "Enhorabuena has encontrado todas las minas!! ;)");
                    } else {
                        finJuego(estadisticas.getCry());
                        JOptionPane
                                .showMessageDialog(new JFrame(),
                                        "No has colocado las banderas correctamente :(");
                    }
                }
            }
        }
    }

    public void finJuego(ImageIcon icon) {
        estadisticas.getBtnSmiley().setIcon(icon);
        estadisticas.getTimer().cancel();
        estadisticas.getTimer().purge();
        tablero.setJuegoAcabado();
    }

    public void vaciar(int x, int y) {
        if (x < 0 || x > this.tablero.getnColumnas() - 1 || y < 0
                || y > this.tablero.getnFilas() - 1) {
            return;
        }
        Celda celda = celdas[y][x];
        if (celda.getVecinos() > 0 && celda.getEstado() != 2) {
            celda.setEstado(1);
            celda.destaparCelda();
        }
        if (x >= 0 && y >= 0 && x < this.tablero.getnColumnas()
                && y < this.tablero.getnFilas()) {
            if (!celda.tieneMina() && celda.getEstado() == 0) {
                celda.setEstado(1);
                celda.destaparCelda();
                // Vaciar recursivamente el tablero
                vaciar(x, y + 1);
                vaciar(x, y - 1);
                vaciar(x + 1, y);
                vaciar(x - 1, y);
                vaciar(x - 1, y - 1);
                vaciar(x - 1, y + 1);
                vaciar(x + 1, y + 1);
                vaciar(x + 1, y - 1);
            }
        }
    }
//-----------------------METODOS IA ---------------------------------------------------------------

    public void IA(/*int x, int y*/) {
        /*Celda celda = celdas[y][x];
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (j >= 0 && i >= 0 && j < this.tablero.getnColumnas()
                        && i < this.tablero.getnFilas()) {
                    Celda cel = celdas[i][j];
                    if (cel != celda && cel.getEstado() == 0) {
                        cel.opcionNoMina();
                    }
                }
            }
        }*/
        seleccionarCasilla();
    }

    // busca la casilla a seleccionar 
    public void seleccionarCasilla() {
        //int x = 0, y = 0;
        Celda celda = celdas[0][0];
        for (int i = 0; i < this.tablero.getnFilas(); i++) {
            for (int j = 0; j < this.tablero.getnColumnas(); j++) {
                Celda cel = celdas[i][j];

                if (cel.getEstado() == 1) {

                    if (cel.getVecinos() != 0) {
                        probabilidadMinas(cel.getX(), cel.getY());
                    }

                }

            }
        }
    }

    //busca la probabilidad a la casilla a sacar 
    // cont = numero de banderas 
    public void probabilidadMinas(int x, int y) {
        Celda celda = celdas[y][x];
        int num = celda.getVecinos();
        int numtapadas = 0, cont = 0;
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (j >= 0 && i >= 0 && j < this.tablero.getnColumnas()
                        && i < this.tablero.getnFilas()) {
                    Celda cel = celdas[i][j];
                    if (cel != celda && cel.getEstado() == 0) {
                        numtapadas++;

                    }
                    if (cel != celda && cel.getEstado() == 2) {
                        cont++;
                    }

                }
            }
        }
        if (numtapadas == num && cont == 0) {
            indicarBandera(celda.getX(), celda.getY());
        } else if (cont == num /*&& numtapadas + cont > num*/) {
            indicarCasillaLibre(celda.getX(), celda.getY());
        } else if (cont + numtapadas == num ) {
            indicarBandera(celda.getX(), celda.getY());
        }
    }

    //señala las posibles ubicaciones de mina 
    public void indicarBandera(int x, int y) {
        Celda celda = celdas[y][x];
        
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (j >= 0 && i >= 0 && j < this.tablero.getnColumnas()
                        && i < this.tablero.getnFilas()) {
                    Celda cel = celdas[i][j];
                    if (cel != celda && cel.getEstado() == 0 && cel.getColor()==0) {
                       
                            cel.bandera();
                        
                    }
                }
            }
        }

    }

    //indica las casillas libres y pone bandera 
    public void indicarCasillaLibre(int x, int y) {
        Celda celda = celdas[y][x];
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (j >= 0 && i >= 0 && j < this.tablero.getnColumnas()
                        && i < this.tablero.getnFilas()) {
                    Celda cel = celdas[i][j];
                    if (cel != celda && cel.getEstado() == 0) {

                        cel.opcionNoMina();
                        cel.setColor(1);

                    }

                }
            }
        }
    }


    
   
}
