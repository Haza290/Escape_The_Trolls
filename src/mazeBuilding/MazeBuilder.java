package mazeBuilding;

import java.util.ArrayList;
import java.util.Random;

import maze.Blank;
import maze.Tile;
import maze.Wall;

/**
 * Uses a depth first search approach to create a maze
 * 
 * @author Harry
 *
 */
public class MazeBuilder {

	private ArrayList<MBTile> mbTilesStack;
	private MBTile[][] maze;

	public MazeBuilder(int width, int hight) {
		// Sets up the maze and mbTileStack arrays.
		maze = new MBTile[width][hight];
		mbTilesStack = new ArrayList<MBTile>();

		// Fill the maze with blank mbTiles.
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j] = new MBTile(i, j);
			}
		}
		buildMaze();
	}

	/**
	 * Builds the maze
	 */
	private void buildMaze() {

		Random random = new Random();
		mbTilesStack.add(maze[0][0]);

		while (!mbTilesStack.isEmpty()) {
			// Get the top mbTile from the stack and check if it has any
			// neighbours.
			MBTile currentTile = mbTilesStack.get(mbTilesStack.size() - 1);
			ArrayList<MBTile> neighbours = getUnvisitedNeighbors(currentTile.x,
					currentTile.y);

			// If neighbours is empty set currentTile to visited and remove it
			// from the stack and continue to the next loop.
			if (neighbours.isEmpty()) {
				currentTile.isVisited = true;
				mbTilesStack.remove(currentTile);
				continue;
			}

			// If there are neighbours pick one at random and add it to the
			// stack then update the tiles.
			MBTile newTile = neighbours
					.get(random.nextInt(neighbours.size()));
			mbTilesStack.add(newTile);

			// Updates the tiles to set if there is a wall below them or to the
			// left of them.
			if (newTile.x > currentTile.x) {
				newTile.isLeftWall = false;
			} else if (newTile.x < currentTile.x) {
				currentTile.isLeftWall = false;
			} else if (newTile.y > currentTile.y) {
				newTile.isUpWall = false;
			} else {
				currentTile.isUpWall = false;
			}
		}
	}

	/**
	 * Gets all unvisited neighbours that are not already on the stack.
	 * 
	 * @param x
	 *            xPos of the current tile.
	 * @param y
	 *            yPos of the current tile.
	 * @return An array of all the valid unvisited neighbour that are not
	 *         already on the stack.
	 */
	private ArrayList<MBTile> getUnvisitedNeighbors(int x, int y) {
		ArrayList<MBTile> neighbors = new ArrayList<MBTile>();

		// if a tile is valid, has not been visited and is not on the stack yet
		// add it as neighbour that can be travelled to.
		int xTemp = x + 1;
		int yTemp = y;
		if (isValidTile(xTemp, yTemp) && !maze[xTemp][yTemp].isVisited
				&& !mbTilesStack.contains(maze[xTemp][yTemp])) {
			neighbors.add(maze[xTemp][yTemp]);
		}
		xTemp = x - 1;
		yTemp = y;
		if (isValidTile(xTemp, yTemp) && !maze[xTemp][yTemp].isVisited
				&& !mbTilesStack.contains(maze[xTemp][yTemp])) {
			neighbors.add(maze[xTemp][yTemp]);
		}
		xTemp = x;
		yTemp = y + 1;
		if (isValidTile(xTemp, yTemp) && !maze[xTemp][yTemp].isVisited
				&& !mbTilesStack.contains(maze[xTemp][yTemp])) {
			neighbors.add(maze[xTemp][yTemp]);
		}
		xTemp = x;
		yTemp = y - 1;
		if (isValidTile(xTemp, yTemp) && !maze[xTemp][yTemp].isVisited
				&& !mbTilesStack.contains(maze[xTemp][yTemp])) {
			neighbors.add(maze[xTemp][yTemp]);
		}

		return neighbors;
	}

	/**
	 * Just checks that a set of coordinates are valid for the maze.
	 * 
	 * @param x
	 * @param y
	 * @return If x and y make a valid coordinate for the maze.
	 */
	private boolean isValidTile(int x, int y) {
		return (x >= 0 && x < maze.length && y >= 0 && y < maze[0].length);
	}

	public Tile[][] getConvertMaze() {

		// Creates the maze 4 times plus 1 bigger than current maze and fills it
		// with blank tiles.
		Tile[][] tempMaze = new Tile[(maze.length * 4) + 1][(maze[0].length * 4) + 1];
		for (int i = 0; i < tempMaze.length; i++) {
			for (int j = 0; j < tempMaze[i].length; j++) {
				tempMaze[i][j] = new Tile(new Blank());
			}
		}

		// For each of the mbTiles in the maze create the corresponding walls in
		// tempMaze
		for (MBTile[] mbTiles : maze) {
			for (MBTile mbTile : mbTiles) {
				int xOffset = mbTile.x * 4;
				int yOffset = mbTile.y * 4;

				// If up is a wall set the corresponding tiles in tempMaze to
				// wall.
				if (mbTile.isUpWall) {
					for (int i = 0; i < 5; i++) {
						tempMaze[xOffset + i][yOffset].setUnit(new Wall());
					}
				}
				// If up is a left set the corresponding tiles in tempMaze to
				// wall.
				if (mbTile.isLeftWall) {
					for (int i = 0; i < 5; i++) {
						tempMaze[xOffset][yOffset + i].setUnit(new Wall());
					}
				}
			}
		}
		
		// Add the walls around the bottom and the right of the maze
		for (int i = 0; i < tempMaze.length; i++) {
			tempMaze[i][tempMaze[i].length - 1].setUnit(new Wall());
		}
		for (int i = 0; i < tempMaze[0].length; i++) {
			tempMaze[tempMaze.length - 1][i].setUnit(new Wall());
		}
		
		return tempMaze;
	}
}
