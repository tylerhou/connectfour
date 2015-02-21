package connectfour;

import java.util.ArrayList;

public class Board {
	
	int[][] board; // stores the board state, 7 cols by 6 rows
	int[] top; // for each column, points to the space where a new piece would drop to
	int color; // stores whose move it is, 1 for +, -1 for -
	int[] history; // stores the entire history of the game, based on which columns pieces were dropped into
	int move; // stores which move it is, used to find the history.
	
	Board() {
		this.board = new int[6][7];
		this.top = new int[7];
		for (int row = 0; row < 6; row++) { // initialize the board to 0
			for (int col = 0; col < 7; col++) {
				board[row][col] = 0;
			}
			top[row] = 0;
		}
		this.color = 1;
		this.move = 0;
		this.history = new int[42]; // 42 = 6*7
	}
	
	public int move(int place) {
		if (top[place] < 6) {
			board[top[place]++][place] = color;
			color = -color;
			history[move] = place;
			++move;
		}
		return top[place] - 1;
	}
	
	public void undo() {
		if (move > 0) {
			board[top[history[move-1]]-1][history[move-1]] = 0;
			--top[history[move-1]];
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
	
	public Pair<Coordinate,Coordinate> getWinner() {
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				if (row+3 < 6 && Math.abs(board[row][col]+board[row+1][col]+board[row+2][col]+board[row+3][col])==4) {
					return new Pair<Coordinate,Coordinate>(new Coordinate(row,col), new Coordinate(row+3,col));
				}
				if (col+3 < 7 && Math.abs(board[row][col]+board[row][col+1]+board[row][col+2]+board[row][col+3])==4) {
					return new Pair<Coordinate,Coordinate>(new Coordinate(row,col), new Coordinate(row,col+3));
				}
				if (row+3 < 6 && col+3 < 7 && Math.abs(board[row][col]+board[row+1][col+1]+board[row+2][col+2]+board[row+3][col+3])==4) {
					return new Pair<Coordinate,Coordinate>(new Coordinate(row,col), new Coordinate(row+3,col+3));
				}
				if (row-3 >=0 && col+3 < 7 && Math.abs(board[row][col]+board[row-1][col+1]+board[row-2][col+2]+board[row-3][col+3])==4) {
					return new Pair<Coordinate,Coordinate>(new Coordinate(row,col), new Coordinate(row-3,col+3));
				}
			}
		}
		return null;
	}
	
	public int getWinnerColor() {
		Pair<Coordinate,Coordinate> w = getWinner();
		if (w == null)
			return 0;
		else return get(w.first.first, w.first.second);
		
	}
	
	public boolean isDraw() {
		int total = 0;
		for (int col = 0; col < 7; col++) {
			total += top[col];
		}
		return total == 42;
	}
	
	@Override
	public String toString() {
		String s = "-----------------------------\n";
		for (int row = 0; row < 6; row++) {
			s += "|";
			for (int col = 0; col < 7; col++) {
				s += " " + (board[5-row][col] == 0 ? " " : (board[5-row][col] > 0 ? "+" : "-")) + " |";
			}
			s += "\n";
		}
		s += "-----------------------------";
		return s;
	}
	
}
