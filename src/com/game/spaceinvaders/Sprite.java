package com.game.spaceinvaders;

import java.awt.Image;

public class Sprite {
	private boolean mVisible;
	private Image mImage;
	protected int x;
	protected int y;
	protected boolean mDying;
	protected int dx;

	public Sprite() {
		mVisible = true;
	}

	public void destroy() {
		mVisible = false;
	}

	public void setVisibility(boolean visibility) {
		mVisible = visibility;
	}

	public boolean isVisible() {
		return mVisible;
	}

	public Image getImage() {
		return mImage;
	}

	public void setImage(Image image) {
		mImage = image;
	}

	public void setX(int pos_x) {
		x = pos_x;
	}

	public void setY(int pos_y) {
		y = pos_y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setDying(boolean dying) {
		mDying = dying;
	}

	public boolean isDying() {
		return mDying;
	}
}
