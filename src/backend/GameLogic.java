package backend;

import java.util.ArrayList;
import java.util.Random;

import display.Display;
import maze.Blank;
import maze.Coordinate;
import maze.Goal;
import maze.Maze;
import mazeBuilding.MazeBuilder;
import enemies.Enemy;
import enemies.Ghost;
import enemies.Spider;
import enemies.Troll;

public class GameLogic {

	private Maze maze;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private Display display;
	private Player player;
	
	private boolean isGameOver = false;		// Has the current game ended
	private boolean win = false;			// Has the game been won

	// Number of enemies to spawn
	private int NUMOFTROLLS = 3;
	private int NUMOFSPIDERS = 3;
	private int NUMOFGHOSTS = 3;

	public static void main(String[] args) {
		new GameLogic();
	}

	/**
	 * Sets up the game by creating the maze, display, player and all the enemies.
	 */
	public GameLogic() {
		maze = new Maze(new MazeBuilder(19, 5).convertMaze());	// Create a new maze using the Maze Builder
		display = new Display(maze);
		player = new Player(getRandomBlankTileCoordinate(), maze, display, this, spawnGoal());
		spawnEnemies();
		display.setMaze(maze);
		display.newFrame();
	}

	/**
	 * Runs the next turn after the player has made a move
	 */
	public void nextTurn() {
		// Makes all the enemies take there turn
		for (Enemy enemy : enemies) {
			enemy.act();
		}
		display.newFrame();
		
		// Checks for game over
		if (isGameOver()) {
			display.gameOver(win);
		}
	}
	
	/**
	 * Spawns the end goal in a blank tile and returns the coordinates of the goal
	 * @return Goal coordinates
	 */
	private Coordinate spawnGoal() {
		Coordinate goalCoordinate = getRandomBlankTileCoordinate();
		maze.setTile(goalCoordinate, new Goal());
		return goalCoordinate;
	}

	/**
	 * Spawns in enemies in random blank tiles.
	 */
	private void spawnEnemies() {

		enemies = new ArrayList<Enemy>();
		
		// Creates Trolls
		for (int i = 0; i < NUMOFTROLLS; i++) {
			enemies.add(new Troll(getRandomBlankTileCoordinate(), maze, this, player));
		}
		// Creates Spiders
		for (int i = 0; i < NUMOFSPIDERS; i++) {
			enemies.add(new Spider(getRandomBlankTileCoordinate(), maze, this, player));
		}
		// Creates Ghosts
		for (int i = 0; i < NUMOFGHOSTS; i++) {
			enemies.add(new Ghost(getRandomBlankTileCoordinate(), maze, this, player));
		}
	}

	/**
	 * Finds and returns a random blank tiles coordinate by picking a random
	 * coordinate and checking if its blank. If its not blank pick new
	 * coordinates and check again.
	 * 
	 * @return The coordinate of a random blank Tile.
	 */
	private Coordinate getRandomBlankTileCoordinate() {
		Random random = new Random();
		Coordinate coordinate;
		do {
			int x = random.nextInt(maze.getLength());
			int y = random.nextInt(maze.getHeight());
			coordinate = new Coordinate(x, y);
		} while (!(maze.getTile(coordinate).getUnit() instanceof Blank));
		return coordinate;
	}

	/**
	 * Checks all the enemies to see if they are standing on the player to find out if the game is over
	 * TODO Maybe rewrite/move this function to give the player a number of lives.
	 * @return
	 */
	private boolean isGameOver() {
		if(isGameOver == true) {
			return true;
		}
		
		for (Enemy enemy : enemies) {
			if (enemy.getCoordinate().equals(player.getPosition())) {
				return true;
			}
		}
		return false;
	}
	
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
		maze.setTile(enemy.getCoordinate(), new Blank());
	}
	
	public void removeEnemy(Coordinate coordinate) {
		Enemy enemy = getEnemy(coordinate);
		if(enemy == null) {
			return;
		}
		removeEnemy(enemy);
	}
	
	/**
	 * Gets enemy from a given coordinate
	 * @param coordinate The coordinate of the enemy
	 * @return
	 */
	private Enemy getEnemy(Coordinate coordinate) {
		for (Enemy enemy : enemies) {
			if(enemy.getCoordinate().equals(coordinate)) {
				return enemy;
			}
		}
		// If we get here then there is no enemy on given coordinate
		return null;
	}
	
	public void setWin(boolean win) {
		this.win = win;
	}

	public void setIsGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
		
	}
	
	public boolean getIsGameOver() {
		return isGameOver;
	}
}