package com.game.spaceinvaders;

import javax.swing.ImageIcon;
public class Bullet extends Sprite{
	private final String bullet_img = "src/com/game/spaceinvaders/bullet.png";
	private final int H_SPACE = 6;
	private final int V_SPACE = 1;
	
	public Bullet() {
		
	}
	public Bullet(int x, int y) {
		ImageIcon icon = new ImageIcon(bullet_img);
		setImage(icon.getImage());
		setX(x+H_SPACE);
		setY(y-V_SPACE);
		
		
	}
}
