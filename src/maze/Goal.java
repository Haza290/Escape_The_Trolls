package maze;

import java.awt.Color;

public class Goal extends Unit{
	
	public Goal() {
		c = '$';
		color = Color.YELLOW;
		walkable = true;
	}
}
