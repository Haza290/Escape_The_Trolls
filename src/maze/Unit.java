package maze;

import java.awt.Color;

public class Unit {
	
	public char c;
	public Color color = Color.WHITE;
	public boolean walkable;
	
	public Unit() {
	}
	
	public boolean isWalkable() {
		return walkable;
	}
	
	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
