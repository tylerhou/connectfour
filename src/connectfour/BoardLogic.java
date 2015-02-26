package connectfour;

public class BoardLogic {

	int[][] board; // stores the board state, 7 cols by 6 rows
	int[] top; // for each column, points to the space where a new piece would
				// drop to
	int color; // stores whose move it is, 1 for +, -1 for -
	int[] history; // stores the entire history of the game, based on which
					// columns pieces were dropped into
	int move; // stores which move it is, used to find the history.

	public BoardLogic() {
		board = new int[6][7];
		top = new int[7];
		history = new int[42]; // 42 = 6*7
		init();
	}
	
	public void init()
	{
		for (int col = 0; col < 7; ++col) { // initialize the board to 0
			for (int row = 0; row < 6; ++row)  {
				board[row][col] = 0;
			}
			top[col] = 0; // and top to 0
		}
		color = 1; // 1 is the first player's move, -1 is the second
		move = 0;
	}

	public int[][] getBoard() {
		return board;
	}

	public int getMove() {
		return move;
	}

	public IntegerPair move(int place) {
		if (top[place] < 6) { // if the column is not full
			board[top[place]++][place] = color; // drops the tile and increments the top of the column
			color = -color; // switches player
			history[move] = place; // remembers the place moved
			++move;
		}
		return new IntegerPair(top[place] - 1, place);
	}

	public void undo() {
		if (move > 0) {
			board[top[history[move - 1]] - 1][history[move - 1]] = 0; // sets the last place moved to 0
			--top[history[move - 1]]; // decrements the top row of the last column moved
			--move;
			color = -color; // switches player
		}
	}

	public int get(int row, int col) {
		return board[row][col]; // returns the value of the board at row and col
	}

	public Pair<IntegerPair, IntegerPair> getWinner() {
		if (move <= 0)
			return null;
		IntegerPair last = new IntegerPair(top[history[move - 1]] - 1, history[move - 1]); // gets the last move
		int a, b;
		if ((a = check(last, -1, 0)) >= 3) { // check down
			return new Pair<IntegerPair, IntegerPair>(last, 
													  new IntegerPair(last.first() - a, last.second()));
		}
		if ((a = check(last, 0, -1)) + (b = check(last, 0, 1)) >= 3) { // check left and right
			return new Pair<IntegerPair, IntegerPair>(new IntegerPair(last.first(), last.second() - a),
													  new IntegerPair(last.first(), last.second() + b));
		}
		if ((a = check(last, -1, -1)) + (b = check(last, 1, 1)) >= 3) { // check right-up and left-down
			return new Pair<IntegerPair, IntegerPair>(new IntegerPair(last.first() - a, last.second() - a),
													  new IntegerPair(last.first() + b, last.second() + b));
		}
		if ((a = check(last, 1, -1)) + (b = check(last, -1, 1)) >= 3) { // check left-up and right-down
			return new Pair<IntegerPair, IntegerPair>(new IntegerPair(last.first() + a, last.second() - a), 
													  new IntegerPair(last.first() - b, last.second() + b));
		}
		return null;
	}

	private int check(IntegerPair last, int d1, int d2) { // returns the # in a row in row 
														  // direction d1 and col direction d2
		int len = 1;
		while (last.first() + len * d1 >= 0
				&& last.second() + len * d2 >= 0
				&& last.first() + len * d1 <= 5
				&& last.second() + len * d2 <= 6
				&& board[last.first() + len * d1][last.second() + len * d2] == -color) {
			len += 1;
		}
		return len - 1;
	}

	public int getWinnerColor() {
		Pair<IntegerPair, IntegerPair> w = getWinner(); // gets the color of the first 
														// coordinate of getWinnerColor()
		if (w == null)
			return 0;
		else
			return get(w.first.first, w.first.second);
	}

	public boolean isDraw() {
		return move == 42 && getWinnerColor() == 0;
	}

	public boolean isTerminal() {
		return move == 42 || getWinnerColor() != 0;
	}

	@Override
	public String toString() {
		String s = "-----------------------------\n";
		for (int row = 0; row < 6; row++) {
			s += "|";
			for (int col = 0; col < 7; col++) {
				// space if == 0, + if == 1 and - if == -1.
				s += " " + (board[5 - row][col] == 0 ? " " : (board[5 - row][col] > 0 ? "+" : "-")) + " |";
			}
			s += "\n";
		}
		s += "-----------------------------";
		return s;
	}

}
