package pathFidning;

import maze.Coordinate;

public class Node {
	
	private Coordinate coordinate;
	private int f, g, h;
	private Node cameFrom;
	
	public Node(Coordinate coordinate, int h, int g, Node cameFrom) {
		this.coordinate = coordinate;
		this.h = h;
		this.g = g;
		this.cameFrom = cameFrom;
		
		f = g + h;
	}

	public Node getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(Node cameFrom) {
		this.cameFrom = cameFrom;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	public int getF() {
		return f;
	}
	
	public int getG() {
		return g;
	}
	
	public void setG(int g) {
		this.g = g;
		f = this.g + h;
	}
}
