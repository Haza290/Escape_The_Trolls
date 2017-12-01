package display;

import java.awt.FlowLayout;
import javax.swing.JFrame;

import maze.Coordinate;
import maze.Maze;
import maze.Tile;
import asciiPanel.AsciiPanel;

/**
 * Display is in charge of all the visuals of the game
 *
 */
public class Display extends JFrame {

	private static final long serialVersionUID = 1060623638149583738L;
	public JFrame mainFrame;
	private AsciiPanel mapPanel;
	private Maze maze;

	public Display(Maze maze) {
		this.maze = maze;

		// Create the main frame
		mainFrame = new JFrame("Escape The Trolls");
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Creates the AsciiPanels
		mapPanel = new AsciiPanel(maze.getLength(), maze.getHeight());
		// Add's the AsciiPanels to the mainFrame
		mainFrame.add(mapPanel);
		// Packs, sets minimum size, makes un-resizable and make the mainFrame visible
		mainFrame.setVisible(false);
		mainFrame.pack();
		mainFrame.setMinimumSize(mainFrame.getSize());
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}

	/**
	 * Updates the AsciiPanel and repaints the mainFrame
	 */
	public void newFrame() {
		// Loops threw the maze ands writes the corresponding maze unit char's
		// in the right place in the AsciiPanel
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getLength(); x++) {
				Tile tilebuff = maze.getTile(new Coordinate(x, y));
				mapPanel.write(tilebuff.getUnit().getC(), x, y, tilebuff
						.getUnit().getColor(), tilebuff.getBackgroundColour());
				mainFrame.repaint();
			}
		}
	}
	
	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	/**
	 * Displays Game Over screen
	 * @param win True if game is won
	 */
	public void gameOver(boolean win) {
		
		mapPanel.clear();
		mapPanel.write("Game Over", 0, 0);
		
		if(win){			
			mapPanel.write("You Win!", 0, 1);
		} else {
			mapPanel.write("You Lose!", 0, 1);
		}
	}
}
