package com.game.spaceinvaders;


public interface Settings {
	public static final String GAME_TITLE = "ECE382 Space Invaders";
    public static final int SCREEN_WIDTH = 358;
    public static final int SCREEN_HEIGHT = 370;
    public static final int ENEMIES_ROWS = 5;
    public static final int ENEMIES_COLUMNS = 5;
    public static final int NUM_OF_LIVES = 3;

    public static final int NUMBER_OF_ENEMIES_TO_DESTROY = ENEMIES_ROWS*ENEMIES_COLUMNS;
    
    public static final int GROUND = SCREEN_HEIGHT - 80;
    
    public static final int BOMB_HEIGHT = 5;
    public static final int BOMB_WIDTH = 5;
    
    public static final int ENEMY_HEIGHT = 12;
    public static final int ENEMY_WIDTH = 12;
    public static final int ENEMY_SPEED = 1;//how fast invader moves
    public static final int DROP_DOWN = 1;//how many iterations before invader goes down
    public static final int GO_DOWN = 15; //px value of how much down each invader will go
    public static final int CHANCE = 5; // (CHANCE/100)% is the chance that invader will shoot
    
    public static final int BORDER_RIGHT = 30;
    public static final int BORDER_LEFT = 5;
    
    public static final int BARRIER_WIDTH = 12;
    public static final int BARRIER_HEIGHT = 12;
    public static final int PLAYER_WIDTH = 15;
    public static final int PLAYER_HEIGHT = 10;
	public static final int ENEMY_INIT_X = 150;
	public static final int ENEMY_INIT_Y = 5;
	public static final int BULLET_WIDTH = 1;
	public static final int BULLET_HEIGHT = 5;
	
	public static final String DESTRUCTION = "src/com/game/spaceinvaders/destruction.wav";
	public static final String SHOT = "src/com/game/spaceinvaders/shot.wav";
	public static final String ENEMY_SHOT = "src/com/game/spaceinvaders/shot.wav";
}

