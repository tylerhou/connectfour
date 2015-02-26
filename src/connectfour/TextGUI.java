package connectfour;

import java.util.Scanner;

public class TextGUI {

	public static void main(String[] args) throws InterruptedException {

		Scanner cin = new Scanner(System.in);
		BoardLogic b = new BoardLogic();
		
		AIPlayer ai = new AIPlayer(10, 1);
		int player = 1, c = 0;
		while (!b.isTerminal()) {
			System.out.println("  0   1   2   3   4   5   6"); // type in
			System.out.println(b);
			ai.setState(b);
			if (player == 1) {
				if ((c=cin.nextInt()) <= 6 && c >= -1) {
					if (c == -1) {
						b.undo();
						b.undo();
						player = -player;
					}
					else {
						b.move(c);						/************TEXT-BASED BOARD************/
					}
				}
				//b.move(ai.getMove(5));
			}
			else {
				b.move(ai.getMove());
			}
			player = -player;
			//Thread.sleep(250);
		}
		System.out.println("  0   1   2   3   4   5   6"); // type in
		System.out.println(b);
		System.out.println(b.getWinnerColor());
		System.out.println(b.getWinner());
		cin.close();
	}
}
