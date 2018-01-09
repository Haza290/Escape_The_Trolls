package maze;

import java.awt.Color;

/**
 * All game charters ie Enemies and player are Units
 * This abstract class provides the basic data structure of all units
 *
 */
public abstract class Unit {
	
	public char charIcon;				// The char used to display the unit
	public Color color = Color.WHITE;	// The colour the unit will be
	public boolean walkable;			// Can the unit walk
	
	public boolean isWalkable() {
		return walkable;
	}
	
	public char getCharIcon() {
		return charIcon;
	}

	public void setCharIcon(char charIcon) {
		this.charIcon = charIcon;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
