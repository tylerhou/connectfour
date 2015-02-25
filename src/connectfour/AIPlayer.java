package connectfour;

public class AIPlayer implements Player {

	int depth, bSc;
	BoardLogic state;
	int[][] strength = {{3, 4, 5, 7, 5, 4, 3}, 
            		    {4, 6, 8, 10, 8, 6, 4},
            		    {5, 8, 11, 13, 11, 8, 5}, 
            		    {5, 8, 11, 13, 11, 8, 5},
            		    {4, 6, 8, 10, 8, 6, 4},
            		    {3, 4, 5, 7, 5, 4, 3}};
		
	AIPlayer(int depth, int bSc) {
		setState(state);
		this.depth = depth;
		this.bSc = bSc;
	}
	
	public void setState(BoardLogic state) {
		this.state = state;
	}
	
	public int evaluate() {
		int winner = state.getWinnerColor();
		if (winner == 1) {
			return Integer.MAX_VALUE;
		}
		else if (winner == -1) {
			return -Integer.MAX_VALUE;
		}
		if (state.isDraw()) {
			return 0;
		}
		int total = 0;
		total += bSc * boardStrength();
		return total;
	}
	
	public int getMove() {
		return getMove(this.depth);
	}
	
	public int getMove(int depth) {
		return negamax(depth, -Integer.MAX_VALUE, Integer.MAX_VALUE).second();
	}
	
	public IntegerPair negamax(int depth, int alpha, int beta) {
		if (depth == 0 || state.isTerminal()) {
			return new IntegerPair(evaluate() * state.color, null);
		}
		IntegerPair best = new IntegerPair(Integer.MIN_VALUE, null), value;
		for (int i = 0; i < 7; ++i) {
			if (state.top[i] < 6) {
				state.move(i);
				value = new IntegerPair(-negamax(depth-1, -beta, -alpha).first, i);
				//System.out.println(new String(new char[depth-1]).replace("\0", "\t") + value + ":" + -state.color);
				if (value.first > best.first) {
					best = value;
				}
				alpha = Math.max(alpha, value.first);
				state.undo();
				if (alpha >= beta) break;
			}
		}
		return best;
	}
	
	private int boardStrength() {
		int total = 0;
		for (int row = 0; row < 6; ++row) {
			for (int col = 0; col < 7; ++col) {
				total += state.board[row][col] * strength[row][col];
			}
		}
		return total;
	}
}