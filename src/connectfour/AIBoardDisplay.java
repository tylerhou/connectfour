package connectfour;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class AIBoardDisplay extends BoardDisplay {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8663223348245048369L;
	private BoardLogic real;
	
	public void setState(BoardLogic board) {
		real = board;
		super.setState(board);
	}
	
	public void setPrematureState(BoardLogic board) {
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
					c = Tile.transparent;
				}
				if (a != real.get(row, col)) {
					c = new Color(c.getRed(), 0, c.getBlue(), 128);
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