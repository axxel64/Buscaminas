package buscaminas;

import java.util.TimerTask;

import javax.swing.JLabel;

public class Cronometro extends TimerTask {

	private JLabel marcadorTiempo;

	public Cronometro(JLabel timer) {
		this.marcadorTiempo = timer;
	}

	@Override
	public void run() {
		this.marcadorTiempo
				.setText((Integer.valueOf(marcadorTiempo.getText()) + 1) + "");

	}

}
