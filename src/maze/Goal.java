package maze;

import java.awt.Color;

/**
 * The Goal the player to trying to get to
 *
 */
public class Goal extends Unit{
	
	public Goal() {
		charIcon = '$';
		color = Color.YELLOW;
		walkable = true;
	}
}
