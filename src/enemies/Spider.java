package enemies;

import java.awt.Color;

import maze.Coordinate;
import maze.Maze;
import backend.GameLogic;
import backend.Player;

/**
 * Spiders move randomly making webs on non web titles. If a player walks onto a web that is connected to the spider
 * then the spider travels the shortest distance along its web to the player. If the player leaves the web then the 
 * spider does back to randomly moving
 */
public class Spider extends Enemy {


	public Spider(Coordinate position, Maze maze, GameLogic gameLogic, Player player) {
		super(position, maze, gameLogic, player);
		charIcon = 'S';
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
