package enemies;

import java.util.ArrayList;
import java.util.Random;

import maze.Blank;
import maze.Coordinate;
import maze.Maze;
import maze.Unit;
import pathFidning.PathFindingAlgorithm;
import backend.GameLogic;
import backend.Player;

/**
 * Has functions TODO rewrite this
 * @author Harry
 *
 */
public abstract class Enemy extends Unit {

	public Coordinate position;
	public Maze maze;
	public GameLogic gameLogic;
	public Player player;

	public Enemy(Coordinate position, Maze maze, GameLogic gameLogic,
			Player player) {
		super();

		this.position = position;
		this.maze = maze;
		this.gameLogic = gameLogic;
		this.player = player;
		walkable = false;
		maze.setTile(position, this);
	}

	public abstract void act();
	
	public void moveTo(Coordinate targetCoordinate) {
		moveTo(targetCoordinate, false);
	}

	/**
	 * A* path finding for enemies
	 * 
	 * @param x
	 * @param y
	 */
	public boolean moveTo(Coordinate targetCoordinate, boolean onlyTravelOnWeb) {

		Coordinate newCoordinate = new PathFindingAlgorithm().findPath(
				position, targetCoordinate, maze, onlyTravelOnWeb);
		// if we get a null then there is no valid path
		if (newCoordinate == null) {
			return false;
		}
		// if move is valid then update coordinates
		if (maze.move(newCoordinate, position)) {
			position = newCoordinate;
		}
		return true;
	}

	/**
	 * Moves enemy to a random walkable tile next to them
	 */
	public void moveRandom() {
		ArrayList<Coordinate> movementOptions = new ArrayList<Coordinate>();

		// Checks if all the surrounding tiles are walkable and adds the
		// walkable ones coordinates to an arraylist.
		Coordinate tempCoordinate = new Coordinate(position.getXPos() + 1,
				position.getYPos());
		if (maze.isWalkable(tempCoordinate)) {
			movementOptions.add(tempCoordinate);
		}
		tempCoordinate = new Coordinate(position.getXPos() - 1,
				position.getYPos());
		if (maze.isWalkable(tempCoordinate)) {
			movementOptions.add(tempCoordinate);
		}
		tempCoordinate = new Coordinate(position.getXPos(),
				position.getYPos() + 1);
		if (maze.isWalkable(tempCoordinate)) {
			movementOptions.add(tempCoordinate);
		}
		tempCoordinate = new Coordinate(position.getXPos(),
				position.getYPos() - 1);
		if (maze.isWalkable(tempCoordinate)) {
			movementOptions.add(tempCoordinate);
		}

		// if we have no walkable tiles return.
		if (movementOptions.isEmpty()) {
			return;
		}

		// Pick a random coordinate from the walkable tiles nearby.
		Random random = new Random();
		moveTo(movementOptions.get(random.nextInt(movementOptions.size())));
	}
	
	public Coordinate getCoordinate() {
		return position;
	}
	
	public void die() {
		gameLogic.removeEnemy(this);
		maze.getTile(position).setUnit(new Blank());
	}
}