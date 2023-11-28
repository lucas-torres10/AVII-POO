package br.com.mariojp.animacao1;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;

	public Principal() {
		initializeUI();
	}

	private void initializeUI() {
		add(new Jogo());
		setResizable(false);
		pack();
		setTitle("Estrela");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Principal ex = new Principal();
				ex.setVisible(true);
			}
		});
	}
}

