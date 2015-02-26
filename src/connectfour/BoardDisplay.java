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
		Pair<IntegerPair, IntegerPair> w;
		if ((w = board.getWinner()) != null) {
			int dx = w.second().first() - w.first().first();
			int dy = w.second().second() - w.first().second();
			dx = dx / (Math.abs(dx) != 0 ? Math.abs(dx) : 1);
			dy = dy / (Math.abs(dy) != 0 ? Math.abs(dy) : 1);
			System.out.println(dx + ", " + dy);
			for (int l = 0; dx * l + w.first().first() <= w.second().first() && 
							dy * l + w.first().second() <= w.second().second(); ++l) {
				System.out.println(dx * l + w.first().first() + ", " + dy * l + w.first().second());
				tiles[dx * l + w.first().first()][dy * l + w.first().second()].setColor(
				tiles[dx * l + w.first().first()][dy * l + w.first().second()].getColor().darker());
			}
		}
	}
}
