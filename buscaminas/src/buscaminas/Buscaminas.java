package buscaminas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Robot;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class Buscaminas {

	// // dimensiones del tablero por defecto 
	private int alto = 8;
	private int ancho = 8;
	private int anchoBoton = 100;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscaminas buscaminas = new Buscaminas();
                                        

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Buscaminas() {
		initialize();
	}

	private void initialize() {

		//Inicializar los componentes (JFrame, JPanels..)
		// El frame est� compuesto de un JPanel Contenedor con 2 paneles (1 para
		// las estad�sticas y el otro para el tablero)
		JFrame frame = new JFrame();
                JPanel contenedor = new JPanel();
		JPanel titulo = new JPanel();
		JPanel panelPrincipal = new JPanel();
		frame.setLayout(new BorderLayout());
		contenedor.setLayout(new BorderLayout());
		frame.setTitle("Buscaminas");
                

		Estadisticas estadisticas = new Estadisticas();
		estadisticas.prepararPanel(titulo, alto * ancho / 6);

		// ActionListener de smiley del panel superior
		estadisticas.getBtnSmiley().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				estadisticas.setMarcadorTiempo("0");
				reset(ancho, alto, panelPrincipal, frame, estadisticas);

			}
		});

		frame.getContentPane().add(titulo, BorderLayout.NORTH);

		// Inicializar el tablero del buscaminas
		Tablero tablero = new Tablero(alto, alto, estadisticas);
		tablero.crearTablero(panelPrincipal);
		frame.getContentPane().add(panelPrincipal, BorderLayout.CENTER);
                

		// Crear el men� superior
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Archivo");
		JMenuItem nuevo = new JMenuItem("Nueva partida");

		nuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				estadisticas.setMarcadorTiempo("0");
				reset(ancho, alto, panelPrincipal, frame, estadisticas);
			}
		});

		JMenuItem salir = new JMenuItem("Salir");

		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		
		JRadioButtonMenuItem intermedio = new JRadioButtonMenuItem("Intermedio");
	

		

		intermedio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//principiante.setSelected(false);
				//experto.setSelected(false);
				estadisticas.getMarcadorTiempo().setFont(
						new Font("OCR A Extended", Font.BOLD, 35));
				estadisticas.setMarcadorTiempo("0");
				alto = 10;
				ancho = 10;
				reset(ancho, alto, panelPrincipal, frame, estadisticas);
			}
		});



		intermedio.setSelected(true);
		fileMenu.add(nuevo);
		fileMenu.add(salir);
		

		frame.setBounds(500,  50, anchoBoton * ancho + 15, anchoBoton * alto + 100);
		frame.setResizable(false);
		frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                 JOptionPane
                .showMessageDialog(new JFrame(),
										"Buscaminas\n" +"reglas\n"
                                                                                + "1-Clic izquierdo para abrir casillas\n"
                                                                                +"2- Clic derecho para poner bandera\n"
                                                                                +"Las casillas azules indican la probabilidad de mina\n"
                                                                                +"Las casillas verdes indican las casillas libres de minas\n");

	}

	public void reset(int ancho, int alto, JPanel panel, JFrame frame,
			Estadisticas estadisticas) {
		// Reiniciar tablero, temporizador, etc..
		Tablero tablero = new Tablero(alto, alto, estadisticas);
		estadisticas.getTimer().cancel();
		estadisticas.getTimer().purge();
		estadisticas.getBtnSmiley().setIcon(estadisticas.getSmiley());
		estadisticas.iniciarCronometro();
		estadisticas.inicializarBanderas(tablero.getnColumnas()
				* tablero.getnFilas() / 6);
		tablero.crearTablero(panel);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setBounds(500, 50, anchoBoton * ancho + 15, anchoBoton * alto + 100);
	}

}