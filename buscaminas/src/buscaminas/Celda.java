package buscaminas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Celda {

	private int x;
	private int y;
	private boolean hayMina;
	private int vecinos;
	// estado-->0 sin descubrir, estado-->1 descubierta, estado-->2 bandera
	private int estado;
        private int color;
	private JButton boton;
	private ImageIcon imagenMina = new ImageIcon(getClass().getResource(
			"/resources/mina.png"));
	private ImageIcon imagenBandera = new ImageIcon(getClass().getResource(
			"/resources/bandera.png"));
	private ImageIcon imagenErrorMina = new ImageIcon(getClass().getResource(
			"/resources/fallo.png"));

	public Celda() {

	}

	public Celda(int y, int x, boolean hayMina) {
		this.x = x;
		this.y = y;
		this.hayMina = hayMina;
		this.estado = 0;
                this.color=0;
		this.boton = new JButton();
	}

	public void destaparCelda() {
            Font fuente=new Font("Monospaced", Font.BOLD, 36);
            
		if (this.hayMina == true) {
			this.boton.setIcon(imagenMina);
			this.boton.setBackground(Color.RED);
			return;
		} else if (this.vecinos > 0) {
			this.boton.setText(String.valueOf(vecinos));

		} 
                
                else {
			this.boton.setText("");

		}
		this.boton.setBackground(Color.white);
                this.boton.setFont(fuente);
                this.boton.setForeground(Color.red);
		//this.boton.setBorder(null);
		this.boton.setEnabled(true);
                        
	}

	public void mostrarBombas() {
		this.boton.setIcon(imagenMina);
	}

	public void mostrarError() {
		this.boton.setIcon(imagenErrorMina);
	}

	public void ponerBandera() {
		this.boton.setIcon(imagenBandera);
	}

	public void quitarBandera() {
		this.boton.setIcon(null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean tieneMina() {
		return hayMina;
	}

	public void setMina(boolean hayMina) {
		this.hayMina = hayMina;
	}

	public int getVecinos() {
		return vecinos;
	}

	public void setVecinos(int vecinos) {
		this.vecinos = vecinos;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public JButton getBoton() {
		return boton;
	}

	public void setBoton(JButton boton) {
		this.boton = boton;
	}
        
        public boolean opcionNoMina(){
            this.boton.setBackground(Color.GREEN);
            return true;
           
	    
        }
        
        public boolean ProbaMina(){
          this.boton.setBackground(Color.ORANGE);
          return true;
        }
        
        public boolean bandera(){
            this.boton.setBackground(Color.BLUE);
            return true;
        }
        public int getColor(){
            return color;
        }
        public void setColor(int color){
            this.color=color;
        }

}
