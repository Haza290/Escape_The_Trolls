package maze;

import java.awt.Color;

/**
 * Wall unit that stops movement of the player and most enemies
 *
 */
public class Wall extends Unit {

	public Wall() {
		charIcon = '#';
		walkable = false;
		color = Color.WHITE;
	}
}
