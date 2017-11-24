package enemies;

import java.awt.Color;

import maze.Coordinate;
import maze.Maze;
import backend.GameLogic;
import backend.Player;

public class Troll extends Enemy {

	private Coordinate playersLastCoordinate;
	private boolean seenPlayer;
	private boolean currentSeePlayer;

	public Troll(Coordinate position, Maze maze, GameLogic gameLogic, Player player) {
		super(position, maze, gameLogic, player);
		c = 'T';
		color = Color.BLUE;
		seenPlayer = false;
	}

	public void act() {	
		
		findPlayer();
		if (!currentSeePlayer && seenPlayer) {
			color = Color.GREEN;
		}		
		
		if (seenPlayer) {
			moveTo(playersLastCoordinate);
			// If we are at the last known position of the player then we don't know where they are now.
			if (position.equals(playersLastCoordinate)) {
				seenPlayer = false;
				color = Color.blue;
			}
		} else {
			moveRandom();
		}
	}

	/**
	 * Checks if player is within sight, if it is, set the seenPlayer true and
	 * sets the players last x and y position TODO rewrite this code.
	 */
	private void findPlayer() {

		for (Coordinate coordinate : maze.getCoordinatesBetween2Points(position, player.getPosition())) {
			if(!maze.getTile(coordinate).isWalkable()) {
				currentSeePlayer = false;
				return;
			}
		}
		currentSeePlayer = true;
		seenPlayer = true;
		color = Color.RED;		
		playersLastCoordinate = player.getPosition();
	}
}
