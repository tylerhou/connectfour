package connectfour;

import java.util.Scanner;

public class GUI {

	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);
		Board b = new Board();
		while (b.getWinner() == null) {
			System.out.println("  0   1   2   3   4   5   6");
			System.out.println(b);
			b.move(cin.nextInt());
		}
		System.out.println(b.getWinnerColor());
		System.out.println(b.getWinner());
	}

}
