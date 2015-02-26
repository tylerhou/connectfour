package connectfour;

public class AIPlayer implements Player {

	private int depth;
	private BoardLogic state;
	private int[][] bS = {{3, 4, 5, 7, 5, 4, 3}, 
            		    {4, 6, 8, 10, 8, 6, 4},
            		    {5, 8, 11, 13, 11, 8, 5}, 
            		    {5, 8, 11, 13, 11, 8, 5},
            		    {4, 6, 8, 10, 8, 6, 4},
            		    {3, 4, 5, 7, 5, 4, 3}};
	private int[] cS = {0, 10, 100};
		
	public AIPlayer(int depth) {
		this.depth = depth;
	}
	
	public void setState(BoardLogic state) {
		this.state = state.deepCopy();
	}
	
	public int evaluate() {
		int winner = state.getWinnerColor();
		if (winner == 1) {
			return Integer.MAX_VALUE;
		}
		else if (winner == -1) {
			return -Integer.MAX_VALUE;
		}
		else if (state.isDraw()) {
			return 0;
		}
		int total = 0;
		total += boardStrength();
		total += connectedStrength();
		return total;
	}
	
	public int getMove() {
		return getMove(depth);
	}
	
	private int getMove(int depth) {
		return negamax(depth, -Integer.MAX_VALUE, Integer.MAX_VALUE).second();
	}
	
	private IntegerPair negamax(int depth, int alpha, int beta) {
		if (depth == 0 || state.isTerminal()) {
			return new IntegerPair(evaluate() * state.getPlayer(), null);
		}
		IntegerPair best = new IntegerPair(Integer.MIN_VALUE, null), value;
		for (int i = 0; i < 7; ++i) {
			if (state.getTop()[i] < 6) {
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
				total += state.getBoard()[row][col] * bS[row][col];
			}
		}
		return total;
	}
	
	public int connectedStrength() {
		int total = 0, color;
		IntegerPair a = null, b = null;
		int[][] s = {{1,1,0,-1},{0,1,1,1}};
		//s = {{-1,0,-1,1},{0,-1,-1,-1}}
		IntegerPair last;
		boolean[][] visited = new boolean[6][7];
		for (int row = 0; row < 6; ++row) {
			for (int col = 0; col < 7; ++col) {
				if (!visited[row][col]) {
					last = new IntegerPair(row, col);
					//System.out.print(last + " ");
					color = state.get(row, col);
					if (color != 0) {
						//System.out.println();
						for (int i = 0; i < 4; ++ i) {
							a = check(last, s[0][i], s[1][i]);
							b = check(last, -s[0][i], -s[1][i]);
							if (a.second() + b.second() >= 3) {
								//System.out.print("\t-> " + a + "," + b + ": ");
								total += color * cS[a.first() + b.first()];
								//System.out.println(total);
							}
						}
					}
					visited[row][col] = true;
				}
			}
		}
		return total;
	}
	
	private IntegerPair check(IntegerPair last, int d1, int d2) { // returns the # in a row
		//in row direction d1 and col direction d2 and also if the next one is free
		int len = 1, player = state.get(last.first(), last.second());
		while (last.first() + len * d1 >= 0
		    && last.second() + len * d2 >= 0
		    && last.first() + len * d1 <= 5
		    && last.second() + len * d2 <= 6
		    && state.get(last.first() + len * d1, last.second() + len * d2) == player) {
			len += 1;
		}
		int same = len;
		while (last.first() + len * d1 >= 0
		    && last.second() + len * d2 >= 0
		    && last.first() + len * d1 <= 5
		    && last.second() + len * d2 <= 6
		    && state.get(last.first() + len * d1, last.second() + len * d2) == 0) {
			len += 1;
		}
		return new IntegerPair(same-1, len-1);
	}
}