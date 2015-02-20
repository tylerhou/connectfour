package connectfour;

import java.util.ArrayList;

public class Board {
	
	int[][] board;
	int[] top;
	int color;
	
	Board() {
		board = new int[6][7];
		top = new int[7];
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				board[row][col] = 0;
			}
			top[row] = 0;
		}
		color = 1;
	}
	
	public void move(int place) {
		if (top[place] < 6) {
			board[++top[place]][place] = color;
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
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 4; col++) {
				if (Math.abs(board[row][col]+board[row+1][col]+board[row+2][col]+board[row+3][col])==4) {
					return new Pair<Coordinate,Coordinate>(new Coordinate(row,col), new Coordinate(row+3,col));
				}
				if (Math.abs(board[row][col]+board[row][col+1]+board[row][col+2]+board[row][col+3])==4) {
					return new Pair<Coordinate,Coordinate>(new Coordinate(row,col), new Coordinate(row,col+3));
				}
				if (Math.abs(board[row][col]+board[row+1][col+1]+board[row+2][col+2]+board[row+3][col+3])==4) {
					return new Pair<Coordinate,Coordinate>(new Coordinate(row,col), new Coordinate(row+3,col+3));
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
