package connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GUI implements ActionListener {

	private BoardDisplay board;
	private HumanPlayer human;
	private BoardLogic logic;
	private Player playerOne, playerTwo;
	private JPanel reset;
	private JButton resetButton;
	private JPanel game;
	private JTabbedPane pane;
	
	public GUI() {
		board = new BoardDisplay();
		human = new HumanPlayer();
		logic = new BoardLogic();
		playerOne = human;
		playerTwo = new AIPlayer(10, 1);
		resetButton = new JButton("Reset");
	}
	
	private void createAndShowGUI() {
		JFrame frame = new JFrame("connectfour");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add content to the window.
		
		pane = new JTabbedPane();
		
		game = new JPanel();
		game.setLayout(new BorderLayout());
		game.add(board, BorderLayout.CENTER);
		
		pane.add("Game", game);
		
		frame.add(pane);
		
		//Display the window.
		frame.pack();
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
	public void play() {
		while (!logic.isTerminal()) {
			if (playerOne instanceof HumanPlayer || playerTwo instanceof HumanPlayer) {
				game.add(human, BorderLayout.SOUTH);
			}
			board.setState(logic.getState());
			if (logic.getPlayer() == 1) {
				playerOne.setState(logic);
				logic.move(playerOne.getMove());
			}
			else if (logic.getPlayer() == -1) {
				playerTwo.setState(logic);
				logic.move(playerTwo.getMove());
			}
		}
		board.setState(logic.getState());
		game.remove(human);
		reset = new JPanel();
		reset.setLayout(new GridLayout(1, 3));
		int winner = logic.getWinnerColor();
		if (winner == 1) {
			reset.add(new Tile(Color.red));
			reset.add(new JLabel("wins."));
		}
		else if (winner == -1) {
			reset.add(new Tile(Color.blue));
			reset.add(new JLabel("wins."));
		}
		else {
			reset.add(new Tile(new Color(0, 0, 0, 0)));
			reset.add(new JLabel("wins."));
		}
		reset.add(resetButton);
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.createAndShowGUI();
		gui.play();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
