package mazeBuilding;

public class MBTile {
	
	boolean isUpWall = true;
	boolean isLeftWall = true;
	boolean isVisited = false;
	int x, y;
	
	public MBTile(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
