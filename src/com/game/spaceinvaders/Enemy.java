package com.game.spaceinvaders;

import javax.swing.ImageIcon;

public class Enemy extends Sprite {
	private Bomb mBomb;
	private int mID;
	private final String enemy_image = "src/com/game/spaceinvaders/enemy.png";

	public Enemy(int x, int y, int ID) {
		this.x = x;
		this.y = y;
		mID = ID;

		mBomb = new Bomb(x, y);
		ImageIcon icon = new ImageIcon(enemy_image);
		setImage(icon.getImage());
	}

	public void reImage() {
		ImageIcon icon = new ImageIcon(enemy_image);
		setImage(icon.getImage());
	}

	public void move(int direction) {
		this.x += direction;
	}

	Bomb getBomb() {
		return mBomb;
	}

	public int returnID() {
		return mID;
	}

	public class Bomb extends Sprite {
		private final String bomb_image = "src/com/game/spaceinvaders/bomb.png";
		private boolean mDestroyed;

		public Bomb(int x, int y) {
			setDestroyed(true);
			this.x = x;
			this.y = y;
			ImageIcon icon = new ImageIcon(bomb_image);
			setImage(icon.getImage());
		}

		public void setDestroyed(boolean destroyed) {
			mDestroyed = destroyed;
		}

		public boolean isDestoyed() {
			return mDestroyed;
		}
	}

}
