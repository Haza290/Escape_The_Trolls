package maze;

import java.awt.Color;

import enemies.Web;

/**
 * The maze is made up of tiles
 * @author Harry
 *
 */
public class Tile {

	private Unit unit;		// The unit that is in this tile
	private Color backgroundColour = Color.BLACK;
	private Web web = null;	// The web this tile is a part of

	public Tile(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Checks if a tile is walkable
	 * @return
	 */
	public boolean isWalkable() {
		return unit.isWalkable();
	}
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Color getBackgroundColour() {
		return backgroundColour;
	}

	public void setBackgroundColour(Color backgroundColour) {
		this.backgroundColour = backgroundColour;
	}

	/**
	 * Checks if this tile is part of a web
	 */
	public boolean isWeb() {
		return !(web == null);
	}

	/**
	 * Sets the 
	 * @param web
	 */
	public void setWeb(Web web) {
		this.web = web;
		if (web == null) {
			backgroundColour = Color.BLACK;
		} else {
			backgroundColour = Color.GRAY;
		}
	}
	
	public Web getWeb() {
		return web;
	}
}
