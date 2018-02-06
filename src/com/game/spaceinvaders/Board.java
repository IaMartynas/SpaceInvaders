package com.game.spaceinvaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable, Settings {
	private Dimension d;
	private ArrayList<Enemy> enemies;
	private ArrayList<Barrier> barriers;
	private Player player;
	private Bullet bullet;

	private int direction = -1; // left -1, right 1;
	private int numOfPassings = 0;
	private int score = 0;
	private int lives = NUM_OF_LIVES;
	private boolean inGame = true;
	private final String explosion_img = "src/com/game/spaceinvaders/explosion.png";
	private String message = "Game Over";
	private MusicPlayer mp = new MusicPlayer();

	private Thread animator;

	public Board() {
		initialize();
	}

	private void initialize() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		d = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
		setBackground(Color.black);
		mp.playEffect("src/com/game/spaceinvaders/background.wav", -30);

		initialize_game();
		setDoubleBuffered(true); // load images smoother

	}

	@Override
	public void addNotify() {
		super.addNotify();
		initialize_game();
	}

	public void initialize_game() {

		enemies = new ArrayList<>();
		int k = 0;
		for (int i = 0; i < ENEMIES_ROWS; i++) {
			for (int j = 0; j < ENEMIES_COLUMNS; j++) {
				enemies.add(new Enemy(ENEMY_INIT_X + 18 * j, ENEMY_INIT_Y + 18 * i, k));
				k++;
			}
		}

		barriers = new ArrayList<>();
		for (int i = 0; i < 2; i++) {// 2 rows
			for (int j = 1; j < 4; j++) {
				for (int l = -1; l < 2; l += 2) {
					barriers.add(
							new Barrier((j * SCREEN_WIDTH / 4) - (l * 6), GROUND - PLAYER_HEIGHT - 45 + (12 * i), k));
					k++;
				}
			}
		}

		player = new Player();
		bullet = new Bullet();

		if (animator == null || !inGame) {
			animator = new Thread(this);
			animator.start();
		}

	}

	public void reInitialize_game() {
		score = 0;
		int k = 0;
		Enemy temp;
		for (int i = 0; i < ENEMIES_ROWS; i++) {
			for (int j = 0; j < ENEMIES_COLUMNS; j++) {
				temp = enemies.get(k);
				temp.setX(ENEMY_INIT_X + 18 * j);
				temp.setY(ENEMY_INIT_Y + 18 * i);
				temp.setVisibility(true);
				temp.setDying(false);
				temp.reImage();
				temp.getBomb().setDestroyed(true);
				k++;
			}
		}
		Barrier temp_bar;
		k = 0;
		for (int i = 0; i < 2; i++) {// 2 rows
			for (int j = 1; j < 4; j++) {
				for (int l = -1; l < 2; l += 2) {
					temp_bar = barriers.get(k);
					temp_bar.setX((j * SCREEN_WIDTH / 4) - (l * 6));
					temp_bar.setY(GROUND - PLAYER_HEIGHT - 45 + (12 * i));
					temp_bar.setVisibility(true);
					temp_bar.setDying(false);
					temp_bar.reImage();
					k++;
				}
			}
		}

		player.setDying(false);
		player.setVisibility(true);
		player.reImage();
		inGame = true;
		animator = new Thread(this);
		animator.start();

	}

	public void drawEnemies(Graphics g) {
		for (Enemy enemy : enemies) {
			if (enemy.isVisible()) {
				g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);

			}
			if (enemy.isDying()) {
				enemy.destroy();
			}
		}
	}

	public void drawBarriers(Graphics g) {
		for (Barrier barrier : barriers) {
			if (barrier.isVisible()) {
				g.drawImage(barrier.getImage(), barrier.getX(), barrier.getY(), this);
			}
			if (barrier.isDying()) {
				barrier.destroy();
			}
		}
	}

	public void drawPlayer(Graphics g) {
		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}
		if (player.isDying()) {
			player.destroy();
			inGame = false;
		}
	}

	public void drawBullet(Graphics g) {
		if (bullet.isVisible()) {
			g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
		}

	}

	public void drawExplosion(Graphics g) {
		for (Enemy e : enemies) {
			Enemy.Bomb bomb = e.getBomb();
			if (!bomb.isDestoyed()) {
				g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.GREEN);

		if (inGame) {
			g.drawLine(0, GROUND, SCREEN_WIDTH, GROUND);
			drawBarriers(g);
			drawEnemies(g);
			drawPlayer(g);
			drawBullet(g);
			drawExplosion(g);

			Font small = new Font("Helvetica", Font.BOLD, 14);
			FontMetrics metr = this.getFontMetrics(small);

			g.setColor(Color.WHITE);
			g.setFont(small);
			message = "Score: " + score;
			g.drawString(message, (SCREEN_WIDTH - metr.stringWidth(message)) / 2, SCREEN_HEIGHT - 50);
			try {
				g.drawImage(ImageIO.read(new File("src/com/game/spaceinvaders/player.png")), 20, SCREEN_HEIGHT - 60,
						Board.this);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawString("x" + lives, metr.stringWidth("x" + lives) + 25, SCREEN_HEIGHT - 50);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void gameOver() {
		Graphics g = this.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);

		if (lives > 0 && message != "Game Won!") {
			g.setColor(new Color(0, 38, 48));
			g.fillRect(45, SCREEN_WIDTH / 2 - 55, SCREEN_WIDTH - 90, 80);
			g.setColor(Color.WHITE);
			g.fillRect(50, SCREEN_WIDTH / 2 - 50, SCREEN_WIDTH - 100, 70);

			g.setColor(Color.BLACK);
			g.setFont(small);
			message = lives + " lives remaining.";
			g.drawString(message, (SCREEN_WIDTH - metr.stringWidth(message)) / 2, (SCREEN_WIDTH / 2) - 25);
			g.drawString("Press SPACE to Restart", (SCREEN_WIDTH - metr.stringWidth("Press SPACE to Restart")) / 2,
					SCREEN_WIDTH / 2);

		} else {

			g.setColor(new Color(0, 38, 48));
			g.fillRect(45, SCREEN_WIDTH / 2 - 35, SCREEN_WIDTH - 90, 60);
			g.setColor(Color.WHITE);
			g.fillRect(50, SCREEN_WIDTH / 2 - 30, SCREEN_WIDTH - 100, 50);

			g.setColor(Color.BLACK);
			g.setFont(small);

			g.drawString(message, (SCREEN_WIDTH - metr.stringWidth(message)) / 2, SCREEN_WIDTH / 2);
		}
	}

	public void animationCycle() {
		if (score == NUMBER_OF_ENEMIES_TO_DESTROY) {
			inGame = false;
			message = "Game Won!";

		}
		player.move();

		if (bullet.isVisible()) {

			int shotX = bullet.getX();
			int shotY = bullet.getY();

			for (Enemy enemy : enemies) {

				int enemyX = enemy.getX();
				int enemyY = enemy.getY();

				if (enemy.isVisible() && bullet.isVisible()) {
					if ((Math.abs(shotX - enemyX) <= (BULLET_WIDTH / 2 + ENEMY_WIDTH / 2))
							&& (Math.abs(shotY - enemyY) <= (BULLET_HEIGHT / 2 + ENEMY_HEIGHT / 2))) {
						ImageIcon ii = new ImageIcon(explosion_img);
						enemy.setImage(ii.getImage());
						enemy.setDying(true);
						score++;
						bullet.destroy();
						mp.playEffect(DESTRUCTION, -30);
					}
				}
			}

			for (Barrier barrier : barriers) {

				int barrierX = barrier.getX();
				int barrierY = barrier.getY();

				if (barrier.isVisible() && bullet.isVisible()) {
					if ((Math.abs(shotX - barrierX) <= (BULLET_WIDTH / 2 + BARRIER_WIDTH / 2))
							&& (Math.abs(shotY - barrierY) <= (BULLET_HEIGHT / 2 + BARRIER_HEIGHT / 2))) {
						bullet.destroy();
						mp.playEffect(DESTRUCTION, -30);
					}
				}
			}

			int y = bullet.getY();
			y -= 4;

			if (y < 0) {
				bullet.destroy();
			} else {
				bullet.setY(y);
			}
		}
		// enemy movements

		for (Enemy enemy : enemies) {

			int x = enemy.getX();

			if (x >= SCREEN_WIDTH - BORDER_RIGHT && direction != -1) {
				numOfPassings++;
				direction = -1;

				if (numOfPassings >= DROP_DOWN) {
					Iterator i1 = enemies.iterator();

					numOfPassings = 0;

					while (i1.hasNext()) {

						Enemy a2 = (Enemy) i1.next();
						a2.setY(a2.getY() + GO_DOWN);
					}
				}

			}

			if (x <= BORDER_LEFT && direction != 1) {
				numOfPassings++;
				direction = 1;

				if (numOfPassings >= DROP_DOWN) {
					numOfPassings = 0;
					Iterator i1 = enemies.iterator();

					while (i1.hasNext()) {

						Enemy a = (Enemy) i1.next();
						a.setY(a.getY() + GO_DOWN);
					}
				}

			}
		}

		Iterator it = enemies.iterator();

		while (it.hasNext()) {

			Enemy enemy = (Enemy) it.next();

			if (enemy.isVisible()) {
				int y = enemy.getY();
				int x = enemy.getX();
				int playerX = player.getX();
				int playerY = player.getY();

				if (y > GROUND - ENEMY_HEIGHT || ((Math.abs(x - playerX) <= (ENEMY_WIDTH / 2 + PLAYER_WIDTH / 2))
						&& (Math.abs(y - playerY) <= (ENEMY_HEIGHT / 2 + PLAYER_HEIGHT / 2)))) {
					inGame = false;
					message = "Invasion!";
				}

				enemy.move(direction * ENEMY_SPEED);
			}
		}

		// bombs
		Random generator = new Random();

		for (Enemy enemy : enemies) {

			int shot = generator.nextInt(100);
			Enemy.Bomb b = enemy.getBomb();

			if (shot < CHANCE && enemy.isVisible() && b.isDestoyed()
					&& ((enemy.returnID() <= ((ENEMIES_ROWS - 1) * ENEMIES_COLUMNS) - 1
							&& !(enemies.get(enemy.returnID() + ENEMIES_COLUMNS).isVisible()))
							|| enemy.returnID() > ((ENEMIES_ROWS - 1) * ENEMIES_COLUMNS) - 1)) {

				b.setDestroyed(false);
				b.setX(enemy.getX());
				b.setY(enemy.getY() + 2);
				mp.playEffect(ENEMY_SHOT, -35);

			}

			int bombX = b.getX();
			int bombY = b.getY();
			int playerX = player.getX();
			int playerY = player.getY();

			if (player.isVisible() && !b.isDestoyed()) {

				if ((Math.abs(bombX - playerX) <= (BOMB_WIDTH / 2 + PLAYER_WIDTH / 2))
						&& (Math.abs(bombY - playerY) <= (BOMB_HEIGHT / 2 + PLAYER_HEIGHT / 2))) {
					ImageIcon ii = new ImageIcon(explosion_img);
					player.setImage(ii.getImage());
					player.setDying(true);
					b.setDestroyed(true);
					mp.playEffect(DESTRUCTION, -30);
				}
			}

			for (Barrier barrier : barriers) {

				int barrierX = barrier.getX();
				int barrierY = barrier.getY();

				if (barrier.isVisible() && !b.isDestoyed()) {
					if ((Math.abs(bombX - barrierX) <= (BOMB_WIDTH / 2 + BARRIER_WIDTH / 2))
							&& (Math.abs(bombY - barrierY) <= (BOMB_HEIGHT / 2 + BARRIER_HEIGHT / 2))) {

						ImageIcon ii = new ImageIcon(explosion_img);
						barrier.setImage(ii.getImage());
						barrier.setDying(true);
						b.setDestroyed(true);
						mp.playEffect(DESTRUCTION, -30);
					}
				}
			}

			if (!b.isDestoyed()) {

				b.setY(b.getY() + 1);

				if (b.getY() >= GROUND - BOMB_HEIGHT) {
					b.setDestroyed(true);
				}
			}
		}
	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();

		while (inGame) {

			repaint();
			animationCycle();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = 20 - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
		lives--;
		gameOver();

	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {

			player.keyRelease(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {

			player.keyPressed(e);

			int x = player.getX();
			int y = player.getY();

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {
				if (inGame) {
					if (!bullet.isVisible()) {
						mp.playEffect(ENEMY_SHOT, -20);
						bullet = new Bullet(x, y);
					}
				}
			}
			if (key == KeyEvent.VK_SPACE && inGame == false && lives > 0 && message != "Game Won!") {
				// inGame = true;

				reInitialize_game();

				// start again
			}
		}
	}
}
