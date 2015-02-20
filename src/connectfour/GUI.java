package connectfour;

import java.util.Scanner;

public class GUI {

	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);
		Board b = new Board();
		int c;
		while (b.getWinner() == null) {
			System.out.println("  0   1   2   3   4   5   6"); // type in
			System.out.println(b);
			if ((c=cin.nextInt()) <= 6 && c >= -1)
				if (c == -1) {
					b.undo();
				}
				else {
				b.move(c);
				}
		}
		System.out.println(b.getWinnerColor());
		System.out.println(b.getWinner());
	}

}
