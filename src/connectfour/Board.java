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
	
	public int[][] getBoard() {
		return this.board;
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
			board[top[history[move-1]]-1][history[move-1]] = 0;
			// uses history to remove the top of the last moved
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
	
	public Coordinate getWinner() {
		
		if (move <= 0) return null;
		Coordinate last = new Coordinate(top[history[move-1]]-1, history[move-1]);
		
		if  (checkDown(last) >= 3 ||
			(checkLeft(last) + checkRight(last)) >= 3 ||
			(checkLeftDown(last) + checkRightUp(last)) >= 3 ||
			(checkRightDown(last) + checkLeftUp(last)) >= 3)
			return last;
		else 
			return null;
	}
	
	private int checkDown(Coordinate last)
	{
		if (last.first() < 0 && board[last.first()-1][last.second()]==-color) 
			return checkDown(new Coordinate(last.first()-1, last.second())) + 1;
		return 0;
	}
	
	private int checkLeft(Coordinate last)
	{
		if (last.second() < 0 && board[last.first()][last.second()-1]==-color) 
			return checkDown(new Coordinate(last.first(), last.second()-1)) + 1;
		return 0;
	}
	
	private int checkRight(Coordinate last)
	{
		if (last.second() < 6 && board[last.first()][last.second()+1]==-color) 
			return checkDown(new Coordinate(last.first(), last.second()+1)) + 1;
		return 0;
	}
	
	private int checkRightDown(Coordinate last)
	{
		if (last.first() > 0 && last.second() < 6 && board[last.first()-1][last.second()+1]==-color) 
			return checkDown(new Coordinate(last.first()-1, last.second()+1)) + 1;
		return 0;
	}
	
	private int checkLeftDown(Coordinate last)
	{
		if (last.first() > 0 && last.second() > 0 && board[last.first()-1][last.second()-1]==-color) 
			return checkDown(new Coordinate(last.first()-1, last.second()-1)) + 1;
		return 0;
	}
	
	private int checkRightUp(Coordinate last)
	{
		if (last.first() < 5 && last.second < 6 && board[last.first()+1][last.second()+1]==-color) 
			return checkDown(new Coordinate(last.first()+1, last.second()+1)) + 1;
		return 0;
	}
	
	private int checkLeftUp(Coordinate last)
	{
		
		if (last.first() < 5 && last.second() > 0 && board[last.first()+1][last.second()-1]==-color) 
			return checkDown(new Coordinate(last.first()-1, last.second()+1)) + 1;
		return 0;
	}
	
	
	public int getWinnerColor() {
		Coordinate w = getWinner();
		if (w == null)
			return 0;
		else return board[w.first()][w.second()];
		
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
