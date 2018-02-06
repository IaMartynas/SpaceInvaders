package com.game.spaceinvaders;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame implements Settings {

	public Game() {
		add(new Board());
		setTitle(GAME_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {// run it on a thread
			Game ex = new Game();
			ex.setVisible(true);
		});
	}
}
