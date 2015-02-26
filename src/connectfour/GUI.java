package connectfour;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GUI extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9090850268301525642L;
	static BoardDisplay board;
	static HumanPlayer human;
	static BoardLogic logic;
	
	public GUI() {
		JTabbedPane tabs = new JTabbedPane();
		
		JPanel game = new JPanel();
		game.setLayout(new BorderLayout());
		
		board = new BoardDisplay();
		game.add(board, BorderLayout.CENTER);
		
		//human = new HumanPlayer();
		//game.add(human, BorderLayout.SOUTH);
		
		tabs.add("Game", game);
		
		add(tabs);
	}
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("connectfour");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add content to the window.
		frame.add(new GUI());
		
		//Display the window.
		frame.pack();
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		createAndShowGUI();
	}

}
