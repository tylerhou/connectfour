package connectfour;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BoardLogic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -155050824689527696L;
	private int BOARD_WIDTH = 7;
	private int BOARD_HEIGHT = 6;
	private int EMPTY = 0;
	private int[][] board; // stores the board state, 7 cols by 6 rows
	private int[] top; // for each column, points to the space where a new piece would
				// drop to
	private int color; // stores whose move it is, 1 for +, -1 for -
	private int[] history; // stores the entire history of the game, based on which
					// columns pieces were dropped into
	private int move; // stores which move it is, used to find the history.

	public BoardLogic() {
		board = new int[BOARD_HEIGHT][BOARD_WIDTH];
		top = new int[BOARD_WIDTH];
		history = new int[42]; // 42 = 6*7
		init();
	}
		
	public void init() {
		for (int col = 0; col < BOARD_WIDTH; ++col) { // initialize the board to EMPTY
			for (int row = 0; row < BOARD_HEIGHT; ++row)  {
				board[row][col] = EMPTY;
			}
			top[col] = 0; // and top to 0
		}
		color = 1; // 1 is the first player's move, -1 is the second
		move = 0;
	}

	public int[][] getBoard() {
		return board;
	}
	
	public BoardLogic getState() {
		return this;
	}
	

	public int getMove() {
		return move;
	}
	
	public int[] getTop() {
		return top;
	}
	
	public int getPlayer() {
		return color;
	}

	public IntegerPair move(int place) {
		if (top[place] < BOARD_HEIGHT) { // if the column is not full
			board[top[place]++][place] = color; // drops the tile and increments the top of the column
			color = -color; // switches player
			history[move] = place; // remembers the place moved
			++move;
		}
		return new IntegerPair(top[place] - 1, place);
	}

	/*public void undo() {
		if (move > 0) {
			board[top[history[move - 1]] - 1][history[move - 1]] = 0; // sets the last place moved to 0
			--top[history[move - 1]]; // decrements the top row of the last column moved
			--move;
			color = -color; // switches player
		}
	}
	*/

	public int get(int row, int col) {
		return board[row][col]; // returns the value of the board at row and col
	}
	
	public IntegerPair getLastMove() {
		return new IntegerPair(top[history[move - 1]] - 1, history[move - 1]); // gets the last move
	}

	public Pair<IntegerPair,IntegerPair> getWinner() {
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				if (row+3 < 6 && Math.abs(board[row][col]+board[row+1][col]+board[row+2][col]+board[row+3][col])==4) {
					return new Pair<IntegerPair,IntegerPair>(new IntegerPair(row,col), new IntegerPair(row+3,col));
				}
				if (col+3 < 7 && Math.abs(board[row][col]+board[row][col+1]+board[row][col+2]+board[row][col+3])==4) {
					return new Pair<IntegerPair,IntegerPair>(new IntegerPair(row,col), new IntegerPair(row,col+3));
				}
				if (row+3 < 6 && col+3 < 7 && Math.abs(board[row][col]+board[row+1][col+1]+board[row+2][col+2]+board[row+3][col+3])==4) {
					return new Pair<IntegerPair,IntegerPair>(new IntegerPair(row,col), new IntegerPair(row+3,col+3));
				}
				if (row-3 >=0 && col+3 < 7 && Math.abs(board[row][col]+board[row-1][col+1]+board[row-2][col+2]+board[row-3][col+3])==4) {
					return new Pair<IntegerPair,IntegerPair>(new IntegerPair(row,col), new IntegerPair(row-3,col+3));
				}
			}
		}
		return null;
	}

	public int getWinnerColor() {
		Pair<IntegerPair, IntegerPair> w = getWinner(); // gets the color of the first 
														// IntegerPair of getWinnerColor()
		if (w == null)
			return EMPTY;
		else
			return get(w.first.first, w.first.second);
	}

	public boolean isDraw() {
		return move == 42 && getWinnerColor() == EMPTY;
	}

	public boolean isTerminal() {
		return move == 42 || getWinnerColor() != EMPTY;
	}

	@Override
	public String toString() {
		String s = "-----------------------------\n";
		for (int row = 0; row < BOARD_HEIGHT; row++) {
			s += "|";
			for (int col = 0; col < BOARD_WIDTH; col++) {
				// space if == 0, + if == 1 and - if == -1.
				s += " " + (board[5 - row][col] == 0 ? " " : (board[5 - row][col] > 0 ? "+" : "-")) + " |";
			}
			s += "\n";
		}
		s += "-----------------------------";
		return s;
	}
	
	public BoardLogic deepCopy() {
		/** returns a complete, deep copy of the object.
		 *  from http://alvinalexander.com/java/java-deep-clone-example-source-code **/
		
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(bout);
			oout.writeObject(this);
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			ObjectInputStream oin = new ObjectInputStream(bin);
			return (BoardLogic) oin.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
