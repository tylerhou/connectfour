package connectfour;

import javax.swing.SwingWorker;

public class AIDisplay extends AIPlayer implements Player, Runnable {
	
	AIWorker worker;
	
	public AIDisplay(int depth) {
		super(depth);
	}
	
	public void setWorker(AIWorker worker) {
		this.worker = worker;
	}
	
	public void run() {
		move = getMove(depth);
	}
	
	private int getMove(int depth) {
		return negamax(depth).second();
	}
	
	protected IntegerPair negamax(int depth, int alpha, int beta) {
		worker.update(super.getState());
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return super.negamax(depth, alpha, beta);
	}
	
	protected IntegerPair negamax(int depth) {
		worker.update(super.getState());
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return super.negamax(depth);
	}
}