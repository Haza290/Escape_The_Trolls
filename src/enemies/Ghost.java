package enemies;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import maze.Blank;
import maze.Coordinate;
import maze.Maze;
import maze.Wall;
import backend.GameLogic;
import backend.Player;

/**
 * Ghost move about randomly and can move threw walls, but not other enemies.
 *
 */
public class Ghost extends Enemy {
	
	public Ghost(Coordinate coordinate, Maze maze, GameLogic gameLogic,
			Player player) {
		super(coordinate, maze, gameLogic, player);
		charIcon = 'G';
		color = Color.GRAY;
	}

	/**
	 * The ghost should move randomly, even through walls
	 */
	public void act() {

		// If we are currently on a wall reset its colour back to white
		if (maze.getTile(position).getUnit() instanceof Wall) {
			maze.getTile(position).getUnit().setColor(Color.WHITE);
		}
		
		// Else we are not on a wall, we remove the ghost from the tile
		else {
			maze.getTile(position).setUnit(new Blank());
		}

		// Find next coordinates to move to
		Coordinate nextCoordinate = getNextCoordinate();

		// If next coordinate is a wall we change its colour to grey
		if (maze.getTile(nextCoordinate).getUnit() instanceof Wall) {
			maze.getTile(nextCoordinate).getUnit().setColor(Color.GRAY);
		}

		// Else if next coordinate is not a wall set the tile's unit to us
		else {
			maze.getTile(nextCoordinate).setUnit(this);
		}
		
		// Set are coordinate to are new coordinate
		position = nextCoordinate;
	}

	/**
	 * Selects a random coordinate next to the ghost position.
	 * 
	 * @return Coordinate
	 */
	private Coordinate getNextCoordinate() {

		ArrayList<Coordinate> movementOptions = new ArrayList<Coordinate>();

		// Checks if all the surrounding tiles are valid and not occupied by an
		// enemy TODO rewrite this
		Coordinate tempCoordinate = new Coordinate(position.getXPos() + 1,
				position.getYPos());
		if (maze.isValidCoordiante(tempCoordinate)
				&& !(maze.getTile(tempCoordinate).getUnit() instanceof Enemy)) {
			movementOptions.add(tempCoordinate);
		}
		tempCoordinate = new Coordinate(position.getXPos() - 1,
				position.getYPos());
		if (maze.isValidCoordiante(tempCoordinate)
				&& !(maze.getTile(tempCoordinate).getUnit() instanceof Enemy)) {
			movementOptions.add(tempCoordinate);
		}
		tempCoordinate = new Coordinate(position.getXPos(),
				position.getYPos() + 1);
		if (maze.isValidCoordiante(tempCoordinate)
				&& !(maze.getTile(tempCoordinate).getUnit() instanceof Enemy)) {
			movementOptions.add(tempCoordinate);
		}
		tempCoordinate = new Coordinate(position.getXPos(),
				position.getYPos() - 1);
		if (maze.isValidCoordiante(tempCoordinate)
				&& !(maze.getTile(tempCoordinate).getUnit() instanceof Enemy)) {
			movementOptions.add(tempCoordinate);
		}

		// if no valid move available then 'move' to current coordinates
		if (movementOptions.isEmpty()) {
			return position;
		}

		// Pick a random coordinate from the valid tiles nearby.
		Random random = new Random();
		return movementOptions.get(random.nextInt(movementOptions.size()));
	}

}
