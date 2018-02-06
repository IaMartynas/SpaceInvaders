package com.game.spaceinvaders;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Player extends Sprite implements Settings {

	private final int START_X = SCREEN_WIDTH / 2;
	private final int START_Y = GROUND - PLAYER_HEIGHT;

	private final String player_img = "src/com/game/spaceinvaders/player.png";

	public Player() {
		ImageIcon icon = new ImageIcon(player_img);
		setImage(icon.getImage());
		setX(START_X);
		setY(START_Y);
	}

	public void reImage() {
		ImageIcon icon = new ImageIcon(player_img);
		setImage(icon.getImage());
	}

	public void move() {
		x += dx;

		if (x <= 2)
			x = 2;

		if (x >= SCREEN_WIDTH - 2 * PLAYER_WIDTH)
			x = SCREEN_WIDTH - 2 * PLAYER_WIDTH;

	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT)
			dx = -2;
		else if (key == KeyEvent.VK_RIGHT)
			dx = 2;

	}

	public void keyRelease(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
			dx = 0;
	}

}
