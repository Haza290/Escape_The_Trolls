package enemies;

import java.awt.Color;

import maze.Coordinate;
import maze.Maze;
import backend.GameLogic;
import backend.Player;

/**
 * Troll moves randomly until it sees a player, then it changes colour and moves directly to the player.
 * If the troll loses sight of the player it changes colour to green and moves towards the last know location
 * of the player. If we are at the last know position of the player and we still can't see the player then change
 * colour back to blue and and go back to moving randomly
 */
public class Troll extends Enemy {

	private Coordinate playersLastCoordinate;
	private boolean seenPlayer;
	private boolean currentSeePlayer;

	public Troll(Coordinate position, Maze maze, GameLogic gameLogic, Player player) {
		super(position, maze, gameLogic, player);
		charIcon = 'T';
		color = Color.BLUE;
		seenPlayer = false;
	}

	/**
	 * Call to make the troll "take a turn"
	 */
	public void act() {	
		
		findPlayer();
		
		// If we have seen the player but don't currently see the player turn green
		if (!currentSeePlayer && seenPlayer) {
			color = Color.GREEN;
		}		
		
		// If we have seen the player then go to the players last know coordinate
		// Note if we currently see the player then playersLastCoordinate = the players current coordinates
		if (seenPlayer) {
			moveTo(playersLastCoordinate);
			// If we are at the last known position of the player then we don't know where they are now.
			if (position.equals(playersLastCoordinate)) {
				seenPlayer = false;
				color = Color.blue;
			}
			// If we haven't seen the player move randomly
		} else {
			moveRandom();
		}
	}

	/**
	 * Checks if player is within sight, if it is, set the seenPlayer true and
	 * sets the players last x and y position
	 */
	private void findPlayer() {

		// Checks if all tiles between us and the player are walkable to see if we can see the player as
		// only walkable tiles can be seen through. 
		for (Coordinate coordinate : maze.getCoordinatesBetween2Points(position, player.getPosition())) {
			// If a tile isn't walkable then return
			if(!maze.getTile(coordinate).isWalkable()) {
				currentSeePlayer = false;
				return;
			}
		}
		// If player can be seen then:
		currentSeePlayer = true;
		seenPlayer = true;
		color = Color.RED;		
		playersLastCoordinate = player.getPosition();
	}
}
