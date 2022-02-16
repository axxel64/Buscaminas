package buscaminas;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Tablero {

	private int nFilas;
	private int nColumnas;
	private int nBombas;
	private Celda[][] celdas;
	private boolean juegoAcabado;
	private Estadisticas estadisticas;

	public Tablero(int nFilas, int nColumnas, Estadisticas estadisticas) {
		this.nFilas = nFilas;
		this.nColumnas = nColumnas;
		this.nBombas = nFilas * nColumnas / 5;
		this.estadisticas = estadisticas;
		this.celdas = new Celda[nColumnas][nFilas];
		this.juegoAcabado = false;

		// Crear las celdas que componen el tablero
		for (int i = 0; i < nFilas; i++)
			for (int j = 0; j < nColumnas; j++) {
				this.celdas[i][j] = new Celda(i, j, false);
			}

		ponerBombas(generaAleatorios(nFilas * nColumnas));
		calcularMinasVecinos();

	}

	public ArrayList<Integer> generaAleatorios(int total) {
		ArrayList<Integer> aleatorios = new ArrayList<Integer>();
		// Generar 1/5 parte de las celdas con minas
		for (int i = 0; i < total / 6; i++) {
			int aleatorio = (int) Math.floor(Math.random() * total);
			// Evitar las repeticiones
			while (aleatorios.contains(aleatorio)) {
				aleatorio = (int) Math.floor(Math.random() * total);
			}
			aleatorios.add(aleatorio);
		}
		// System.out.println(aleatorios);
		return aleatorios;
	}

	public void ponerBombas(ArrayList<Integer> aleatorios) {
		int k = 0;
		for (int i = 0; i < nFilas; i++) {
			for (int j = 0; j < nColumnas; j++) {
				if (aleatorios.contains(k)) {
					celdas[i][j].setMina(true);
                                        
					//celdas[i][j].getBoton().setBackground(Color.red);
				}
				k++;
			}
		}
	}

	public void calcularMinasVecinos() {
		for (int i = 0; i < nFilas; i++)
			for (int j = 0; j < nColumnas; j++) {
				celdas[i][j].setVecinos(numMinasVecinos(i, j));
			}
	}

	public boolean estaEnTablero(int x, int y) {
		return x >= 0 && y >= 0 && x < this.nColumnas && y < this.nFilas;
	}

	public int numMinasVecinos(int y, int x) {
		int numMinasVecinos = 0;
		for (int i = y - 1; i <= y + 1; i++) {
			for (int j = x - 1; j <= x + 1; j++) {
				if (j == x && i == y) {
				} else {
					if (estaEnTablero(j, i)) {
						if (celdas[i][j].tieneMina())
							numMinasVecinos++;
					}
				}
			}
		}
		return numMinasVecinos;
	}

	public void crearTablero(JPanel panel) {
		panel.removeAll();
		panel.setLayout(new GridLayout(nFilas, nColumnas));
		for (int i = 0; i < nFilas; i++) {
			for (int j = 0; j < nColumnas; j++) {
				panel.add(celdas[i][j].getBoton());
				celdas[i][j].getBoton().addMouseListener(
						new BotonAccion(celdas[i][j], this, estadisticas));
			}
		}
		panel.revalidate();
	}

	public boolean JuegoAcabado() {
		return juegoAcabado;
	}

	public void setJuegoAcabado() {
		this.juegoAcabado = true;
		descubrirBombas();
	}

	public void reiniciarTablero(int ancho, int alto) {
		reiniciarCeldas();
		ponerBombas(generaAleatorios(ancho * alto));
		calcularMinasVecinos();
	}

	public void reiniciarCeldas() {
		for (int i = 0; i < nFilas; i++)
			for (int j = 0; j < nColumnas; j++) {
				celdas[i][j].setVecinos(0);
				celdas[i][j].setEstado(0);
				celdas[i][j].setMina(false);
				celdas[i][j].getBoton().setEnabled(true);
				celdas[i][j].getBoton().setBorder(new JButton().getBorder());
				celdas[i][j].getBoton().setIcon(null);
				celdas[i][j].getBoton().setText(null);
				celdas[i][j].getBoton().setBackground(
						new JButton().getBackground());
			}
	}

	public void descubrirBombas() {
		for (int i = 0; i < nFilas; i++) {
			for (int j = 0; j < nColumnas; j++) {
				// celdas[i][j].getBoton().setEnabled(false);
				if (celdas[i][j].tieneMina() && celdas[i][j].getEstado() != 2) {
					celdas[i][j].mostrarBombas();
				} else if (celdas[i][j].getEstado() == 2
						&& !celdas[i][j].tieneMina()) {
					celdas[i][j].mostrarError();
				}
			}
		}
	}

	public boolean comprobar() {
		int valor = 0;
		for (int i = 0; i < nFilas; i++) {
			for (int j = 0; j < nColumnas; j++) {
				if (celdas[i][j].tieneMina()) {
					if (celdas[i][j].getEstado() != 2) {
						valor += 1;
					}
				}
			}
		}
		if (valor > 0) {
			return false;
		} else {
			return true;
		}
	}

	public int getnFilas() {
		return nFilas;
	}

	public int getnColumnas() {
		return nColumnas;
	}

	public int getnBombas() {
		return nBombas;
	}

	public Celda[][] getCeldas() {
		return celdas;
	}

	public boolean isJuegoAcabado() {
		return juegoAcabado;
	}

	public void setJuegoAcabado(boolean juegoAcabado) {
		this.juegoAcabado = juegoAcabado;
	}

}
