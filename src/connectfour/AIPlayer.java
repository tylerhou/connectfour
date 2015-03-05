package connectfour;

public class AIPlayer implements Player, Runnable {

	private int BOARD_WIDTH = 7;
	private int BOARD_HEIGHT = 6;
	private int PLAYER_ONE = 1;
	private int PLAYER_TWO = -1;
	private int EMPTY = 0;
	protected int depth;
	private BoardLogic state;
	private int[][] bS = {{3, 4, 5, 7, 5, 4, 3}, 
            		    {4, 6, 8, 10, 8, 6, 4},
            		    {5, 8, 11, 13, 11, 8, 5}, 
            		    {5, 8, 11, 13, 11, 8, 5},
            		    {4, 6, 8, 10, 8, 6, 4},
            		    {3, 4, 5, 7, 5, 4, 3}};
	private int[] cS = {0, 10, 100};
	protected int move;
		
	public AIPlayer(int depth) {
		this.depth = depth;
	}
	
	public void setState(BoardLogic state) {
		this.state = state.deepCopy();
	}
	
	public BoardLogic getState() {
		return state;
	}
	
	public int evaluate() {
		int winner = state.getWinnerColor();
		//System.out.println("Winner: " + winner + ", Last move: " + state.getLastMove());
		//System.out.println(state);
		if (winner == PLAYER_ONE) {
			return Integer.MAX_VALUE;
		}
		else if (winner == PLAYER_TWO) {
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
		synchronized (this) {
			try {
				Thread negamax = new Thread(this);
				negamax.start();
				negamax.join();
			} catch (InterruptedException e) {
				return -1;
			}
		}
		return move;
	}
	
	public void run() {
		move = getMove(depth);
	}
	
	private int getMove(int depth) {
		return negamax(depth, -Integer.MAX_VALUE, Integer.MAX_VALUE).second();
	}
	
	protected IntegerPair negamax(int depth, int alpha, int beta) {
		if (depth == 0 || state.isTerminal()) {
			return new IntegerPair(evaluate() * state.getPlayer(), null);
		}
		IntegerPair best = new IntegerPair(Integer.MIN_VALUE, null), value;
		for (int i = 0; i < BOARD_WIDTH; ++i) {
			if (state.getTop()[i] < BOARD_HEIGHT) {
				state.move(i);
				value = new IntegerPair(-negamax(depth-1, -beta, -alpha).first, i);
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
	
	protected IntegerPair negamax(int depth) {
		if (depth == 0 || state.isTerminal()) {
			return new IntegerPair(evaluate() * state.getPlayer(), null);
		}
		IntegerPair best = new IntegerPair(Integer.MIN_VALUE, null), value;
		for (int i = 0; i < BOARD_WIDTH; ++i) {
			if (state.getTop()[i] < BOARD_HEIGHT) {
				state.move(i);
				value = new IntegerPair(-negamax(depth-1).first, i);
				if (value.first > best.first) {
					best = value;
				}
				state.undo();
			}
		}
		return best;
	}
	
	private int boardStrength() {
		int total = 0;
		for (int row = 0; row < BOARD_HEIGHT; ++row) {
			for (int col = 0; col < BOARD_WIDTH; ++col) {
				total += state.getBoard()[row][col] * bS[row][col];
			}
		}
		return total;
	}
	
	public int connectedStrength() {
	    int total = 0, color;
	    IntegerPair a = null, b = null;
	    int[][] s = {{1,1,0,-1},{0,1,1,1}}; //up/down, up-right/down-left, right/left, down-right/up-left
	    IntegerPair last;
	    boolean[][][] visited = new boolean[4][BOARD_HEIGHT][BOARD_WIDTH];
	    for (int row = 0; row < BOARD_HEIGHT; ++row) {
	        for (int col = 0; col < BOARD_WIDTH; ++col) { // loop through each tile position possible
                last = new IntegerPair(row, col);
                color = state.get(row, col);
                if (color != EMPTY) { // if square isn't empty
                    for (int i = 0; i < 4; ++ i) { // loop through four directions
        	            if (!visited[i][row][col]) {
	                        a = check(last, s[0][i], s[1][i], visited, i); // get lengths
	                        b = check(last, -s[0][i], -s[1][i], visited, i);
	                        if (a.second() + b.second() >= 3) { // if possible to create a four with this chain
	                            total += color * cS[a.first() + b.first()]; // add to heuristic
	                        }
	                        visited[i][row][col] = true;
        	            }
                    }
                }
	        }
	    }
	    return total;
	}

	private IntegerPair check(IntegerPair last, int d1, int d2, boolean[][][] visited, int i) { // returns the # in a row
	    //in row direction d1 and col direction d2 and also if the next one is free
	    int len = 1, player = state.get(last.first(), last.second());
	    while (last.first() + len * d1 >= 0
	        && last.second() + len * d2 >= 0
	        && last.first() + len * d1 < BOARD_HEIGHT
	        && last.second() + len * d2 < BOARD_WIDTH // while inbounds
	        && state.get(last.first() + len * d1, last.second() + len * d2) == player) { // and while the tiles are the same color as the player's
	        visited[i][last.first() + len * d1][last.second() + len * d2] = true; 
	        len += 1;
	    }
	    int same = len;
	    while (last.first() + len * d1 >= 0
	        && last.second() + len * d2 >= 0
	        && last.first() + len * d1 < BOARD_HEIGHT
	        && last.second() + len * d2 < BOARD_WIDTH // again, while inbounds
	        && state.get(last.first() + len * d1, last.second() + len * d2) != -player) { // while the tiles are not the enemy's 
	        visited[i][last.first() + len * d1][last.second() + len * d2] = true;
	        len += 1;
	    }
	    return new IntegerPair(same-1, len-1);
	}
}