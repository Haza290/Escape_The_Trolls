package enemies;

import java.awt.Color;

import maze.Coordinate;
import maze.Maze;
import backend.GameLogic;
import backend.Player;

// TODO rewrite web code
public class Spider extends Enemy {


	public Spider(Coordinate position, Maze maze, GameLogic gameLogic, Player player) {
		super(position, maze, gameLogic, player);
		c = 'S';
		color = Color.CYAN;
	}

	public void act() {
		color = Color.CYAN;

		// If the title we are on isn't a web, make it a web
		if (!maze.getTile(position).isWeb()) {
			new Web(position, maze);

		// If player and spider are on the same web move to player
		} else if (maze.getTile(position).getWeb() == maze.getTile(player.getPosition()).getWeb()) {
			moveTo(player.getPosition(), true);
			color = Color.RED;

		// Else move randomly
		} else {
			moveRandom();
		}
	}
}
