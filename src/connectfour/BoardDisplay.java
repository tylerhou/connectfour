package connectfour;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class BoardDisplay extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4593703609988780886L;
	private int BOARD_WIDTH = 7;
	private int BOARD_HEIGHT = 6;
	private int PLAYER_ONE = 1;
	private int PLAYER_TWO = -1;
	private Tile[][] tiles = new Tile[6][7];
	private Color transparent = new Color(0, 0, 0, 0);
	
	public BoardDisplay() {
		setLayout(new GridLayout(BOARD_HEIGHT, BOARD_WIDTH));
		for (int row = 0; row < BOARD_HEIGHT; ++row) {
			for (int col = 0; col < BOARD_WIDTH; ++col) {
				tiles[5-row][col] = new Tile(transparent);
				add(tiles[5-row][col]);
			}
		}
	}
	
	public void setState(BoardLogic board) {
		for (int row = 0; row < BOARD_HEIGHT; ++row) {
			for (int col = 0; col < BOARD_WIDTH; ++col) {
				Color c;
				int a;
				if ((a = board.get(row, col)) == PLAYER_ONE) {
					c = Color.red;
				}
				else if (a == PLAYER_TWO) {
					c = Color.blue;
				}
				else {
					c = transparent;
				}
				tiles[row][col].setColor(c);
			}
		}
		repaint();
		Pair<IntegerPair, IntegerPair> w;
		if ((w = board.getWinner()) != null) {
			int dx = w.second().first() - w.first().first();
			int dy = w.second().second() - w.first().second();
			dx = dx / (Math.abs(dx) != 0 ? Math.abs(dx) : 1);
			dy = dy / (Math.abs(dy) != 0 ? Math.abs(dy) : 1);
			for (int l = 0; dx * l + w.first().first() != w.second().first() || 
							dy * l + w.first().second() != w.second().second(); ++l) {
				tiles[dx * l + w.first().first()][dy * l + w.first().second()].setColor(
				tiles[dx * l + w.first().first()][dy * l + w.first().second()].getColor().darker());
			}
			tiles[w.second().first()][w.second().second()].setColor(
			tiles[w.second().first()][w.second().second()].getColor().darker());
		}
	}
}
