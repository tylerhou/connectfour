package connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

public class GUI implements ActionListener {

	private BoardDisplay board;
	private HumanPlayer human;
	private BoardLogic logic;
	private Player playerOne, playerTwo;
	private AIPlayer analyze;
	private JPanel reset, game, settings;
	private JButton resetButton;
	private JTabbedPane pane;
	private JFrame frame;
	private PlayerSettings playerOneSettings, playerTwoSettings;
	
	public GUI() {
		board = new BoardDisplay();
		human = new HumanPlayer();
		logic = new BoardLogic();
		playerOne = human;
		playerTwo = human;
		analyze = new AIPlayer(10);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		
		for (JButton button: human.getButtons()) {
			button.addActionListener(this);
		}
	}
	
	private void createAndShowGUI() {
		frame = new JFrame("connectfour");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/** tabbed pane **/
		pane = new JTabbedPane();
		
		/** game tab **/
		game = new JPanel();
		game.setLayout(new BorderLayout());
		game.add(board, BorderLayout.CENTER);
		
		pane.add("Game", game);
		
		/** settings tab **/
		settings = new JPanel();
		
		
		
		
		pane.add("Settings", settings);
		
		frame.add(pane);
		
		//Display the window.
		frame.pack();
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
	public void play() {
		if (playerOne instanceof HumanPlayer || playerTwo instanceof HumanPlayer) {
			game.add(human, BorderLayout.SOUTH);
		}
		SwingWorker<Void,BoardLogic> worker = new SwingWorker<Void,BoardLogic>() {
			@Override
			protected Void doInBackground() throws Exception {
				while (!logic.isTerminal()) {
					publish(logic.getState());
					if (logic.getPlayer() == 1) {
						playerOne.setState(logic);
						logic.move(playerOne.getMove());
					}
					else if (logic.getPlayer() == -1) {
						playerTwo.setState(logic);
						logic.move(playerTwo.getMove());
					}
				}
				return null;
			}
			
			protected void done() {
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
				game.add(reset, BorderLayout.SOUTH);
				pane.repaint();
			}
			
			protected void process(List<BoardLogic> chunks) {
				BoardLogic boardState = chunks.get(chunks.size()-1);
				board.setState(boardState);
				analyze.setState(boardState);
				System.out.println(analyze.connectedStrength());
			}
		};
		worker.execute();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetButton) {
			game.remove(reset);
			logic.init();
			game.add(human, BorderLayout.SOUTH);
			pane.repaint();
			play();
		}
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.createAndShowGUI();
		gui.play();		
	}
}
