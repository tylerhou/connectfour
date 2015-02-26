package connectfour;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class BoardDisplay extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4593703609988780886L;
	Tile[][] tiles = new Tile[6][7];
	Color transparent = new Color(0, 0, 0, 0);
	
	public BoardDisplay() {
		setLayout(new GridLayout(6, 7));
		for (int row = 0; row < 6; ++row) {
			for (int col = 0; col < 7; ++col) {
				tiles[row][col] = new Tile(Color.red);
				add(tiles[row][col]);
			}
		}
	}
	
	public void update(int[][] board) {
		for (int row = 0; row < 6; ++row) {
			for (int col = 0; col < 7; ++col) {
				Color c;
				if (board[row][col] == 1) {
					c = Color.red;
				}
				else if (board[row][col] == -1) {
					c = Color.blue;
				}
				else {
					c = transparent;
				}
				tiles[row][col].setColor(c);
			}
		}
	}
}
