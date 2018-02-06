package com.game.spaceinvaders;

import javax.swing.ImageIcon;

public class Barrier extends Sprite {
	private int mID;
	private final String barrier_img = "src/com/game/spaceinvaders/barrier.png";

	public Barrier() {

	}

	public Barrier(int x, int y, int ID) {
		mID = ID;
		setX(x);
		setY(y);
		ImageIcon icon = new ImageIcon(barrier_img);
		setImage(icon.getImage());
	}

	public int returnID() {
		return mID;
	}

	public void reImage() {
		ImageIcon icon = new ImageIcon(barrier_img);
		setImage(icon.getImage());
	}

}
