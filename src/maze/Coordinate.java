package maze;

public class Coordinate {
	
	private int xPos, yPos;
	
	public Coordinate(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public boolean equals(Coordinate coordinate) {
		return(coordinate.getXPos() == xPos && coordinate.getYPos() == yPos);
	}
	
	public Coordinate addCoordinates(Coordinate coordinate) {
		return new Coordinate(xPos + coordinate.getXPos(), yPos + coordinate.getYPos());
	}
}
