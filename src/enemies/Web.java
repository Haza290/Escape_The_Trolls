package enemies;

import java.util.ArrayList;

import maze.Coordinate;
import maze.Maze;

public class Web {

	private ArrayList<Coordinate> web;
	private Maze maze;

	public Web(Coordinate coordinate, Maze maze) {
		this.maze = maze;
		web = new ArrayList<Coordinate>();
		web.add(coordinate);
		maze.getTile(coordinate).setWeb(this);
		mergeNewWebs(coordinate);
	}

	public Web(ArrayList<Coordinate> web, Maze maze) {
		this.web = web;
		this.maze = maze;
		for (Coordinate coordinate : web) {
			maze.getTile(coordinate).setWeb(this);
		}
		updateTiles();
	}

	private void updateTiles() {
		for (Coordinate coordinate : web) {
			maze.getTile(coordinate).setWeb(this);
		}
	}

	public void addToWeb(Coordinate coordinate) {
		web.add(coordinate);
	}

	/**
	 * Returns web
	 * 
	 * @return web
	 */
	public ArrayList<Coordinate> getWeb() {
		return web;
	}

	/**
	 * Combine this web with anther
	 * 
	 * @param web
	 */
	public void combineWebs(Web web) {
		// Updates titles coming from new web
		for (Coordinate coordinate : web.getWeb()) {
			maze.getTile(coordinate).setWeb(this);
		}
		this.web.addAll(web.getWeb());
	}

	/**
	 * Removes a coordinate from the web
	 * 
	 * @param coordinate
	 */
	public void removeFromWeb(Coordinate coordinate) {
		for (int i = 0; i < web.size(); i++) {
			if (web.get(i).equals(coordinate)) {
				web.remove(i);
				maze.getTile(coordinate).setWeb(null);
				break;
			}
		}
		checkContiguity();
	}

	private void checkContiguity() {
		ArrayList<Coordinate> openList = new ArrayList<Coordinate>();
		ArrayList<Coordinate> closedList = new ArrayList<Coordinate>();

		// Checks for an empty web. This should never happen but am keeping it
		// for now just in case
		if (web.isEmpty()) {
			return;
		}

		ArrayList<ArrayList<Coordinate>> arrayOfWebArrays = new ArrayList<ArrayList<Coordinate>>();

		while (web.size() != 0) {

			// Add's the first coordinate from web to the openList
			openList.add(web.get(0));

			// While the openList isn't empty
			while (!openList.isEmpty()) {

				// Grabs a coordinate from the openList, removes it and adds it
				// to the closedList
				Coordinate currentCoordinate = openList.get(0);
				openList.remove(currentCoordinate);
				closedList.add(currentCoordinate);

				// Puts all neighbours into a temp array then adds all the
				// neighbours that are in the web to an array
				ArrayList<Coordinate> temNeighbours = maze
						.getNeighbourCoordinates(currentCoordinate);
				ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
				for (Coordinate neighbour : temNeighbours) {
					for (Coordinate webCoordinate : web) {
						if (neighbour.equals(webCoordinate)) {
							neighbours.add(webCoordinate);
						}
					}
				}

				// Adds neighbours to the openList as long as they aren't
				// already on the open or closed list
				for (Coordinate coordinate : neighbours) {
					if (!openList.contains(coordinate) && !closedList.contains(coordinate)) {
						openList.add(coordinate);
					}
				}
			}

			// closedList contains a contiguous complete web so we add it to a
			// tempWebArray and add that to an array of all contiguous webs
			// created remove all the coordinates in the closedList from web and
			// then clear the closedList
			ArrayList<Coordinate> tempWebArray = new ArrayList<Coordinate>();
			tempWebArray.addAll(closedList);
			arrayOfWebArrays.add(tempWebArray);
			web.removeAll(closedList);
			closedList.clear();
		}

		// Updates this web to be the first contiguous web from the array of web
		// arrays
		web.addAll(arrayOfWebArrays.get(0));
		updateTiles();

		// Creates a new web for each of the other contiguous webs
		for (int i = 1; i < arrayOfWebArrays.size(); i++) {
			new Web(arrayOfWebArrays.get(i), maze);
		}
	}

	/**
	 * Merges new web with any other webs touching it
	 * @param coordinate
	 */
	private void mergeNewWebs(Coordinate coordinate) {
		// Gets neighbours and if any are in a different web then combine webs
		ArrayList<Coordinate> neighbours = maze
				.getNeighbourCoordinates(coordinate);
		for (Coordinate neighbourCoordinate : neighbours) {
			if (maze.getTile(neighbourCoordinate).isWeb()
					&& !(maze.getTile(neighbourCoordinate).getWeb() == this)) {
				combineWebs(maze.getTile(neighbourCoordinate).getWeb());
			}
		}
	}
}
