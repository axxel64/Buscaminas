package buscaminas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Estadisticas {

	private ImageIcon smiley = new ImageIcon(getClass().getResource(
			"/resources/smile.gif"));
	private ImageIcon surprise = new ImageIcon(getClass().getResource(
			"/resources/surprise.gif"));
	private ImageIcon cry = new ImageIcon(getClass().getResource(
			"/resources/cry.gif"));
	private ImageIcon LOL = new ImageIcon(getClass().getResource(
			"/resources/LOL.gif"));

	private int numBanderas;
	private JLabel marcadorTiempo;
	private JButton btnSmiley;
	private JLabel marcadorBanderas;
	private Timer timer;

	public Estadisticas() {
		this.marcadorBanderas = new JLabel("", null, JLabel.CENTER);
		this.btnSmiley = new JButton(smiley);
		this.marcadorTiempo = new JLabel("0", null, JLabel.CENTER);
	}

	public void prepararPanel(JPanel panel, int numMinas) {

		panel.removeAll();

		this.numBanderas = numMinas;
		panel.setLayout(new GridLayout());

		this.marcadorBanderas.setText(String.valueOf(numBanderas));
		this.marcadorBanderas
				.setFont(new Font("OCR A Extended", Font.BOLD, 35));
		this.marcadorBanderas.setOpaque(true);
		this.marcadorBanderas.setBackground(Color.black);
		this.marcadorBanderas.setForeground(Color.red);
		panel.add(this.marcadorBanderas);

		this.btnSmiley.setFocusable(false);
		panel.add(this.btnSmiley);

		this.marcadorTiempo.setFont(new Font("OCR A Extended", Font.BOLD, 35));
		this.marcadorTiempo.setOpaque(true);
		this.marcadorTiempo.setBackground(Color.black);
		this.marcadorTiempo.setForeground(Color.red);
		panel.add(this.marcadorTiempo);
		iniciarCronometro();

		panel.revalidate();

	}

	public void iniciarCronometro() {
		this.timer = new Timer();
		this.timer.schedule(new Cronometro(marcadorTiempo), 1000, 1000);
	}

	public void inicializarBanderas(int numBanderas) {
		this.numBanderas = numBanderas;
		this.marcadorBanderas.setText(String.valueOf(this.numBanderas));
	}

	public void restarBandera() {
		this.marcadorBanderas.setText(Integer.valueOf(this.marcadorBanderas
				.getText()) - 1 + "");
	}

	public void sumarBandera() {
		this.marcadorBanderas.setText(Integer.valueOf(this.marcadorBanderas
				.getText()) + 1 + "");
	}

	public int getNumBanderas() {
		return numBanderas;
	}

	public void setNumBanderas(int numBanderas) {
		this.numBanderas = numBanderas;
	}

	public JLabel getMarcadorTiempo() {
		return marcadorTiempo;
	}

	public void setMarcadorTiempo(String marcadorTiempo) {
		this.marcadorTiempo.setText(marcadorTiempo);
	}

	public JButton getBtnSmiley() {
		return btnSmiley;
	}

	public JLabel getMarcadorBanderas() {
		return marcadorBanderas;
	}

	public Timer getTimer() {
		return timer;
	}

	public ImageIcon getSmiley() {
		return smiley;
	}

	public ImageIcon getSurprise() {
		return surprise;
	}

	public ImageIcon getCry() {
		return cry;
	}

	public ImageIcon getLOL() {
		return LOL;
	}

}
