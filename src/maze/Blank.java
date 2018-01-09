package maze;

/**
 * A blank unit means that there is no other unit on this tile
 *
 */
public class Blank extends Unit {

	public Blank() {
		charIcon = ' ';
		walkable = true;
	}
}
