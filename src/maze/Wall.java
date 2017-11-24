package maze;

import java.awt.Color;

/**
 * Wall unit that stops movement of the player and most enemies
 * @author Harry
 *
 */
public class Wall extends Unit {

	public Wall() {
		c = '#';
		walkable = false;
		color = Color.WHITE;
	}
}
