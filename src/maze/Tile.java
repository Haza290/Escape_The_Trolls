package maze;

import java.awt.Color;

import enemies.Web;

public class Tile {

	private Unit unit;
	private Color backgroundColour = Color.BLACK;
	private Web web = null;

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

	public boolean isWeb() {
		return !(web == null);
	}

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
