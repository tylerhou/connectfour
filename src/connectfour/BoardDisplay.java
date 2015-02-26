package connectfour;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class BoardDisplay extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4593703609988780886L;
	private Tile[][] tiles = new Tile[6][7];
	private Color transparent = new Color(0, 0, 0, 0);
	
	public BoardDisplay() {
		setLayout(new GridLayout(6, 7));
		for (int row = 0; row < 6; ++row) {
			for (int col = 0; col < 7; ++col) {
				tiles[5-row][col] = new Tile(transparent);
				add(tiles[5-row][col]);
			}
		}
	}
	
	public void setState(BoardLogic board) {
		for (int row = 0; row < 6; ++row) {
			for (int col = 0; col < 7; ++col) {
				Color c;
				if (board.get(row, col) == 1) {
					c = Color.red;
				}
				else if (board.get(row, col) == -1) {
					c = Color.blue;
				}
				else {
					c = transparent;
				}
				tiles[row][col].setColor(c);
			}
		}
		repaint();
	}
}
