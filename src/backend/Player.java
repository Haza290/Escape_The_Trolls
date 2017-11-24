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

	public Coordinate getPosition() {
		return position;
	}

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
		if (e.getKeyCode() == DOWNKEY) {
			yMove = 1;
			c = 'v';
		}
		// Left key pressed
		if (e.getKeyCode() == LEFTKEY) {
			xMove = -1;
			c = '<';
		}
		// Right key pressed
		if (e.getKeyCode() == RIGHTKEY) {
			xMove = 1;
			c = '>';
		}
		move(xMove, yMove);

		if (position.equals(goal)) {
			gameLogic.isGameOver = true;
			gameLogic.win = true;
		}

		gameLogic.nextTurn();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// System.out.println("KeyReleased: " + e.getKeyChar());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// System.out.println("KeyTyped: " + e.getKeyChar());
	}

	/**
	 * Moves the player
	 * 
	 * @param xMove
	 * @param yMove
	 */
	private void move(int xMove, int yMove) {

		Coordinate targetCoordinate = position.addCoordinates(new Coordinate(xMove, yMove));
		Coordinate behinfTargetCoordinate = position.addCoordinates(new Coordinate(xMove * 2, yMove * 2));

		// If we can move to the tile move and update coordinate
		if (maze.move(targetCoordinate, position)) {
			position = targetCoordinate;
		}
		// Else if the next tile is a wall and there is no wall behind it push
		// the wall
		else if (maze.getTile(targetCoordinate).getUnit() instanceof Wall
				&& maze.isValidCoordiante(behinfTargetCoordinate)
				&& !(maze.getTile(behinfTargetCoordinate).getUnit() instanceof Wall)) {
			
			// If unit behind the wall in an enemy kill it
			if(maze.getTile(behinfTargetCoordinate).getUnit() instanceof Enemy) {
				gameLogic.removeEnemy(behinfTargetCoordinate);
			}
			maze.move(behinfTargetCoordinate, targetCoordinate);
			
			// If we are moving a wall onto web destroy the web
			if(maze.getTile(behinfTargetCoordinate).isWeb()) {
				maze.getTile(behinfTargetCoordinate).getWeb().removeFromWeb(behinfTargetCoordinate);
			}
		}
	}
}
