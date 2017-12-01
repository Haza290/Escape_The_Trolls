package backend;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import maze.Coordinate;
import maze.Maze;
import maze.Unit;
import maze.Wall;
import display.Display;
import enemies.Enemy;

public class Player extends Unit implements KeyListener {

	private int UPKEY = 38;
	private int DOWNKEY = 40;
	private int LEFTKEY = 37;
	private int RIGHTKEY = 39;

	private Coordinate position;
	private Maze maze;
	private GameLogic gameLogic;
	private Coordinate goal;

	public Player(Coordinate position, Maze maze, Display display,
			GameLogic gameLogic, Coordinate goal) {
		super();

		c = 'v';
		color = Color.MAGENTA;
		walkable = true;

		this.position = position;
		this.maze = maze;
		this.gameLogic = gameLogic;
		this.goal = goal;

		display.mainFrame.addKeyListener(this);

		maze.setTile(this.position, this);
	}

	/**
	 * Returns the current position of the player
	 * @return The current coordinate of the player
	 */
	public Coordinate getPosition() {
		return position;
	}

	/**
	 * Runs when key is pressed to check if the player is moving
	 */
	@Override
	public void keyPressed(KeyEvent e) {
				
		// if game is over just return
		if (gameLogic.isGameOver) {
			return;
		}

		int xMove = 0;
		int yMove = 0;

		// Up key pressed
		if (e.getKeyCode() == UPKEY) {
			yMove = -1;
			c = '^';
		}
		// Down key pressed
		else if (e.getKeyCode() == DOWNKEY) {
			yMove = 1;
			c = 'v';
		}
		// Left key pressed
		else if (e.getKeyCode() == LEFTKEY) {
			xMove = -1;
			c = '<';
		}
		// Right key pressed
		else if (e.getKeyCode() == RIGHTKEY) {
			xMove = 1;
			c = '>';
		}
		else {
			return;
		}
		move(xMove, yMove);

		// Checks if player is out the goal
		if (position.equals(goal)) {
			gameLogic.setIsGameOver(true);
			gameLogic.setWin(true);
		}

		gameLogic.nextTurn();
	}

	/**
	 * Moves the player
	 * 
	 * @param xMove X position of target move
	 * @param yMove Y position of target move
	 */
	private void move(int xMove, int yMove) {

		Coordinate targetCoordinate = position.addCoordinates(new Coordinate(xMove, yMove));
		Coordinate behindTargetCoordinate = position.addCoordinates(new Coordinate(xMove * 2, yMove * 2));

		// If we can move to the tile move and update coordinate
		if (maze.move(targetCoordinate, position)) {
			position = targetCoordinate;
		}
		// Else if the next tile is a wall and there is no wall behind it push
		// the wall
		else if (maze.getTile(targetCoordinate).getUnit() instanceof Wall
				&& maze.isValidCoordiante(behindTargetCoordinate)
				&& !(maze.getTile(behindTargetCoordinate).getUnit() instanceof Wall)) {
			
			// If unit behind the wall in an enemy kill it
			if(maze.getTile(behindTargetCoordinate).getUnit() instanceof Enemy) {
				gameLogic.removeEnemy(behindTargetCoordinate);
			}
			maze.move(behindTargetCoordinate, targetCoordinate);
			
			// If we are moving a wall onto web destroy the web
			if(maze.getTile(behindTargetCoordinate).isWeb()) {
				maze.getTile(behindTargetCoordinate).getWeb().removeFromWeb(behindTargetCoordinate);
			}
		}
	}

	/**
	 * Needs to be included to implement KeyListener
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	/**
	 * Needs to be included to implement KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {	
	}
}
