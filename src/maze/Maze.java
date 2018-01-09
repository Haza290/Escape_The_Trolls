package maze;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Contains a 2d Array of tiles that represents the maze. Also includes
 * functions for interacting with the maze.
 * 
 * @author Harry
 *
 */
public class Maze {

	private Tile[][] maze;

	public Maze(Tile[][] maze) {
		this.maze = maze;
	}

	/**
	 * Moves units from 1 tile to anther by setting the new tile to the unit and
	 * setting the old tile to blank. Meant to be used to move a unit only 1 tile
	 * 
	 * @param newPosition The new coordinate of the unit
	 * @param oldPosition The old coordinate of the unit
	 * @param unit The unit being moved
	 * @return boolean whether move is available
	 */
	public boolean move(Coordinate newPosition, Coordinate oldPosition) {

		// If new coordinate is walkable move the unit
		if (isWalkable(newPosition)) {
			maze[newPosition.getXPos()][newPosition.getYPos()].setUnit(getTile(oldPosition).getUnit());
			maze[oldPosition.getXPos()][oldPosition.getYPos()].setUnit(new Blank());
			return true;
		}
		return false;
	}

	/**
	 * Returns the Tile from a given coordinate
	 * 
	 * @param coordinate
	 *            The coordinate of the Tile
	 * @return The Tile from a given coordinate
	 */
	public Tile getTile(Coordinate coordinate) {
		return maze[coordinate.getXPos()][coordinate.getYPos()];
	}

	/**
	 * Gets the maze as a Tile[][]
	 * 
	 * @return The maze as a Tile[][]
	 */
	public Tile[][] getMaze() {
		return maze;
	}

	/**
	 * Gets the height of the maze
	 * 
	 * @return int height of the maze
	 */
	public int getHeight() {
		return maze[0].length;
	}

	/**
	 * Gets the length of the maze
	 * 
	 * @return int length of the maze
	 */
	public int getLength() {
		return maze.length;
	}

	/**
	 * Sets the tile at a given coordinate
	 * 
	 * @param coordinate	the coordinate of the tile to be set
	 * @param unit			the unit to set the tile at a given unit
	 */
	public void setTile(Coordinate coordinate, Unit unit) {
		maze[coordinate.getXPos()][coordinate.getYPos()].setUnit(unit);
	}

	/**
	 * Resets all the background colours of the maze. Mainly used for debugging
	 * but might be useful to keep around.
	 */
	public void resetColour() {
		for (Tile[] tiles : maze) {
			for (Tile tile : tiles) {
				tile.setBackgroundColour(Color.BLACK);
			}
		}
	}

	/**
	 * Checks is a given coordinate is within the maze
	 * 
	 * @param coordinate	the coordinate being checked
	 * @return boolean 		checking if the coordinate is within the maze
	 */
	public boolean isValidCoordiante(Coordinate coordinate) {
		return (getLength() > coordinate.getXPos()
				&& getHeight() > coordinate.getYPos()
				&& coordinate.getXPos() >= 0 && coordinate.getYPos() >= 0);
	}

	/**
	 * Checks if a given tile is within the maze and if it is walkable
	 * 
	 * @param coordinate	the coordinate of the tile being checked
	 * @return boolean 		if the coordinate tile is walkable
	 */
	public boolean isWalkable(Coordinate coordinate) {
		if (!isValidCoordiante(coordinate)) {
			return false;
		}
		return getTile(coordinate).isWalkable();
	}
	
	/**
	 * Finds all the coordinates between to coordinates
	 * @param startCoordinate
	 * @param endCoordinate
	 * @return An ArrayList of coordinates
	 */
	public ArrayList<Coordinate> getCoordinatesBetween2Points(Coordinate startCoordinate, Coordinate endCoordinate) {
		
		// Checks that the 2 coordinates are valid
		//if(!isValidCoordiante(startCoordinate) && !isValidCoordiante(endCoordinate)) {
		//	System.err.println("start or end coordinate was not valid");
		//	return null;
		//}
		
		ArrayList<Coordinate> returnCoordinates = new ArrayList<Coordinate>();
		
		int deltaX = Math.abs(endCoordinate.getXPos() - startCoordinate.getXPos());
		int deltaY = Math.abs(endCoordinate.getYPos() - startCoordinate.getYPos());
		
		int sx = (startCoordinate.getXPos() < endCoordinate.getXPos()) ? 1 : -1;
		int sy = (startCoordinate.getYPos() < endCoordinate.getYPos()) ? 1 : -1;
				
		int error = deltaX - deltaY;
		int x = startCoordinate.getXPos();
		int y = startCoordinate.getYPos();
				
		while (true) {
						
			if (x == endCoordinate.getXPos() && y == endCoordinate.getYPos()) {
				break;
			}
			int error2 = 2 * error;
			
			if(error2 > -deltaY) {
				error -= deltaY;
				x += sx;
			}
			if(error2 < deltaX) {
				error += deltaX;
				y += sy;
			}
			returnCoordinates.add(new Coordinate(x, y));
			}		
		return returnCoordinates;
	}
	
	/**
	 * Gets all valid neighbours
	 * TODO maybe move this and other utility functions to a utility class
	 * @param coordinate
	 * @return
	 */
	public ArrayList<Coordinate> getNeighbourCoordinates(Coordinate coordinate) {
		
		ArrayList<Coordinate> tempNeighbours = new ArrayList<Coordinate>();
		
		int xTemp = coordinate.getXPos();
		int yTemp = coordinate.getYPos();
		
		// Adds all possible neighbours to a temp array
		tempNeighbours.add(new Coordinate(xTemp + 1, yTemp));
		tempNeighbours.add(new Coordinate(xTemp - 1, yTemp));
		tempNeighbours.add(new Coordinate(xTemp, yTemp + 1));
		tempNeighbours.add(new Coordinate(xTemp, yTemp - 1));
		
		ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
		
		// Checks that neighbours are valid and then adds them to the neighbours array
		for (Coordinate coordinate2 : tempNeighbours) {
			if (isValidCoordiante(coordinate2)) {
				neighbours.add(coordinate2);
			}
		}
		
		return neighbours;
	}
}
