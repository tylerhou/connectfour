package connectfour;

import java.util.ArrayList;

public class Board {

	int[][] board; // stores the board state, 7 cols by 6 rows
	int[] top; // for each column, points to the space where a new piece would
				// drop to
	int color; // stores whose move it is, 1 for +, -1 for -
	int[] history; // stores the entire history of the game, based on which
					// columns pieces were dropped into
	int move; // stores which move it is, used to find the history.

	Board() {
		this.board = new int[6][7];
		this.top = new int[7];
		this.history = new int[42]; // 42 = 6*7
		init();
	}
	
	public void init()
	{
		for (int row = 0; row < 6; row++) { // initialize the board to 0
			for (int col = 0; col < 7; col++) {
				board[row][col] = 0;
			}
			top[row] = 0;
		}
		this.color = 1;
		this.move = 0;
	}

	public int[][] getBoard() {
		return this.board;
	}

	public int getMove() {
		return move;
	}

	public int move(int place) {
		if (top[place] < 6) {
			board[top[place]++][place] = color;
			color = -color;
			history[move] = place; // remembers the place moved
			++move;
		}
		return top[place] - 1;
	}

	public void undo() {
		if (move > 0) {
			board[top[history[move - 1]] - 1][history[move - 1]] = 0;
			// uses history to remove the top of the last moved
			--top[history[move - 1]];
			--move;
			color = -color;
		}
	}

	public ArrayList<Integer> getMoves() {
		ArrayList<Integer> m = new ArrayList<Integer>(7);
		for (int col = 0; col < 7; col++) {
			if (top[col] < 6) {
				m.add(col);
			}
		}
		return m;
	}

	public int get(int row, int col) {
		return board[row][col];
	}

	public Pair<IPair, IPair> getWinner() {

		if (move <= 0)
			return null;
		IPair last = new IPair(top[history[move - 1]] - 1, history[move - 1]);
		int a, b;
		if ((a = check(last, -1, 0)) >= 3) { // check down
			return new Pair<IPair, IPair>(last, new IPair(last.first() - a,
					last.second()));
		}
		if ((a = check(last, 0, -1)) + (b = check(last, 0, 1)) >= 3) { // check
																		// left
																		// and
																		// right
			return new Pair<IPair, IPair>(new IPair(last.first(), last.second()
					- a), new IPair(last.first(), last.second() + b));
		}
		if ((a = check(last, -1, -1)) + (b = check(last, 1, 1)) >= 3) { // check
																		// right
																		// up
																		// and
																		// left
																		// down
			return new Pair<IPair, IPair>(new IPair(last.first() - a,
					last.second() - a), new IPair(last.first() + b,
					last.second() + b));
		}
		if ((a = check(last, 1, -1)) + (b = check(last, -1, 1)) >= 3) { // check
																		// left
																		// up
																		// and
																		// right
																		// down
			return new Pair<IPair, IPair>(new IPair(last.first() + a,
					last.second() - a), new IPair(last.first() - b,
					last.second() + b));
		}
		return null;
	}

	public int check(IPair last, int d1, int d2) {
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
		Pair<IPair, IPair> w = getWinner();
		if (w == null)
			return 0;
		else
			return get(w.first.first, w.first.second);
	}

	public boolean isDraw() {
		int total = 0;
		for (int col = 0; col < 7; col++) {
			total += top[col];
		}
		return total == 42;
	}

	public boolean isTerminal() {
		return isDraw() || getWinnerColor() != 0;
	}

	@Override
	public String toString() {
		String s = "-----------------------------\n";
		for (int row = 0; row < 6; row++) {
			s += "|";
			for (int col = 0; col < 7; col++) {
				s += " "
						+ (board[5 - row][col] == 0 ? " "
								: (board[5 - row][col] > 0 ? "+" : "-")) + " |";
			}
			s += "\n";
		}
		s += "-----------------------------";
		return s;
	}

}
