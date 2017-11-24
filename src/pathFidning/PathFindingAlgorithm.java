package pathFidning;

import java.util.ArrayList;

import maze.Coordinate;
import maze.Maze;

public class PathFindingAlgorithm {

	ArrayList<Node> closedList = new ArrayList<Node>();
	ArrayList<Node> openList = new ArrayList<Node>();

	Coordinate targetCoordinate;
	Maze maze;

	/**
	 * 
	 * @param startCoordinate
	 *            The starting coordinate for the path finding
	 * @param targetCoordinate
	 *            The target coordinate for the path finding
	 * @param maze
	 *            The maze in which the path finding is being done
	 * @return The coordinate of the next tile to move to
	 */
	public Coordinate findPath(Coordinate startCoordinate,
			Coordinate targetCoordinate, Maze maze, boolean onlyTravelOnWebs) {

		this.targetCoordinate = targetCoordinate;
		this.maze = maze;
		openList.add(new Node(startCoordinate, calculateH(startCoordinate), 0,
				null));

		// While the open list is not empty we have more nodes to check
		while (!openList.isEmpty()) {

			Node currentNode = null;
			int lowestFScore = Integer.MAX_VALUE;

			// Find the node with the lowest F score in the open list
			for (Node node : openList) {
				if (node.getF() < lowestFScore) {
					currentNode = node;
					lowestFScore = currentNode.getF();
				}
			}

			// If we are at goal return
			if (currentNode.getCoordinate().equals(targetCoordinate)) {
				return getFirstCoordinate(currentNode);
			}

			// Remove the current node from open list and add it to the closed
			// list
			openList.remove(currentNode);
			closedList.add(currentNode);

			neighbourLoop: for (Coordinate coordinate : getNeighbours(currentNode, onlyTravelOnWebs)) {

				// Checks if neighbour is in the closest list and continues to
				// the next neighbour if it is.
				for (Node node : closedList) {
					if (node.getCoordinate().equals(coordinate)) {
						continue neighbourLoop;
					}
				}

				// Checks if neighbour is in the open list and then checks if
				// its gScore can be lowered before continuing on to the next
				// neighbour.
				for (Node node : openList) {
					if (node.getCoordinate().equals(coordinate)) {
						int newG = currentNode.getG();
						newG++;
						if (newG < node.getG()) {
							node.setG(newG);
						}
						continue neighbourLoop;
					}
				}

				// If we get here then the coordinate are not in the open or
				// closed list so we have to create a new node and add it to the
				// open list
				openList.add(new Node(coordinate, calculateH(coordinate),
						currentNode.getG() + 1, currentNode));
			}
		}
		System.out.println("no valid path found");
		
		// if we get to here then there is no valid path
		return null;
	}

	/**
	 * Returns the next coordinate in the path finding
	 * 
	 * @param node
	 * @return
	 */
	private Coordinate getFirstCoordinate(Node node) {
		Node currentNode = node;
		while (currentNode.getCameFrom().getCameFrom() != null) {
			currentNode = currentNode.getCameFrom();
		}
		return currentNode.getCoordinate();
	}

	/**
	 * Calculates the H score of a given coordinate
	 * 
	 * @param coordinate The coordinate that needs its H score calculated
	 * @return The H score as an int
	 */
	private int calculateH(Coordinate coordinate) {

		int dx = Math.abs(targetCoordinate.getXPos() - coordinate.getXPos());
		int dy = Math.abs(targetCoordinate.getYPos() - coordinate.getYPos());

		return dx + dy;
	}

	/**
	 * Get legal neighbours of a given node
	 * 
	 * @param node
	 *            The node we are finding the neighbours of
	 * @return An arrayList of coordinates of valid neighbours
	 */
	private ArrayList<Coordinate> getNeighbours(Node node,
			boolean onlyTravelOnWebs) {

		int nodeXPos = node.getCoordinate().getXPos();
		int nodeYPos = node.getCoordinate().getYPos();

		int xTemp = nodeXPos + 1;
		int yTemp = nodeYPos;

		ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();

		// Checks that neighbour is valid, is walkable and if only walk on web is true then checks if is web
		if (maze.isValidCoordiante(new Coordinate(xTemp, yTemp))
				&& maze.getMaze()[xTemp][yTemp].isWalkable()
				&& (!onlyTravelOnWebs || maze.getTile(new Coordinate(xTemp, yTemp)).isWeb())) {
			neighbors.add(new Coordinate(xTemp, yTemp));
		}
		
		xTemp = nodeXPos - 1;
		yTemp = nodeYPos;
		
		if (maze.isValidCoordiante(new Coordinate(xTemp, yTemp))
				&& maze.getMaze()[xTemp][yTemp].isWalkable()
				&& (!onlyTravelOnWebs || maze.getTile(new Coordinate(xTemp, yTemp)).isWeb())) {
			neighbors.add(new Coordinate(xTemp, yTemp));
		}
		
		xTemp = nodeXPos;
		yTemp = nodeYPos + 1;
		
		if (maze.isValidCoordiante(new Coordinate(xTemp, yTemp))
				&& maze.getMaze()[xTemp][yTemp].isWalkable()
				&& (!onlyTravelOnWebs || maze.getTile(new Coordinate(xTemp, yTemp)).isWeb())) {
			neighbors.add(new Coordinate(xTemp, yTemp));
		}
		
		xTemp = nodeXPos;
		yTemp = nodeYPos - 1;
		
		if (maze.isValidCoordiante(new Coordinate(xTemp, yTemp))
				&& maze.getMaze()[xTemp][yTemp].isWalkable()
				&& (!onlyTravelOnWebs || maze.getTile(new Coordinate(xTemp, yTemp)).isWeb())) {
			neighbors.add(new Coordinate(xTemp, yTemp));
		}

		return neighbors;
	}

}