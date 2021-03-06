package connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI implements ActionListener, ChangeListener {

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
	private Dimension settingsSize, gameSize, minimumSize;
	private SwingWorker<Void,BoardLogic> playWorker;
	private Tile winTile;
	private JLabel winLabel;
	
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
		
		settingsSize = new Dimension(300, 300);
		minimumSize = new Dimension(450, 450);
		gameSize = minimumSize;
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
		settings.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		playerOneSettings = new PlayerSettings("Player One", human);
		playerTwoSettings = new PlayerSettings("Player Two", human);
		
		/** player one settings **/
		c.fill = GridBagConstraints.BOTH;
		c.weightx = c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		settings.add(playerOneSettings, c);
		
		/** divider **/
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		settings.add(new JSeparator(SwingConstants.HORIZONTAL), c);
		
		/** player two settings **/
		c.fill = GridBagConstraints.BOTH;
		c.weightx = c.weighty = 1;
		c.gridx = 0;
		c.gridy = 2;
		settings.add(playerTwoSettings, c);
		
		pane.add("Settings", settings);
		
		frame.add(pane);
		
		pane.addChangeListener(this);
		
		/** win/reset pane **/
		reset = new JPanel();
		reset.setLayout(new GridLayout(1, 3));
		
		winTile = new Tile(Tile.transparent);
		winLabel = new JLabel();
		reset.add(winTile);
		reset.add(winLabel);
		reset.add(resetButton);
		
		/** display the window **/
		frame.pack();
		frame.setSize(gameSize);
		frame.setMinimumSize(minimumSize);
		frame.setVisible(true);
	}
	
	public void play() {
		if (playerOne instanceof HumanPlayer || playerTwo instanceof HumanPlayer) {
			game.add(human, BorderLayout.SOUTH);
		}
		else {
			game.remove(human);
		}
		playWorker = new SwingWorker<Void,BoardLogic>() {
			@Override
			protected Void doInBackground() throws Exception {
				int move;
				while (!logic.isTerminal() && !isCancelled()) {
					publish(logic.getState());
					if (logic.getPlayer() == 1) {
						playerOne.setState(logic);
						move = playerOne.getMove();
						if (isCancelled()) return null;
							logic.move(move);
					}
					else if (logic.getPlayer() == -1) {
						playerTwo.setState(logic);
						move = playerTwo.getMove();
						if (isCancelled()) return null;
						logic.move(move);
					}
				}
				return null;
			}
			
			protected void done() {
				if (isCancelled()) {
					//System.out.println("Worker stopped.");
					return;
				}
				if (logic.isTerminal()) {
					board.setState(logic.getState());
					game.remove(human);

					int winner = logic.getWinnerColor();
					if (winner == 1) {
						winTile.setColor(Color.red);
						winLabel.setText("wins.");
					}
					else if (winner == -1) {
						winTile.setColor(Color.blue);
						winLabel.setText("wins.");
					}
					else if (logic.isDraw()) {
						winTile.setColor(Tile.transparent);
						winLabel.setText("Tie.");
					}
					game.add(reset, BorderLayout.SOUTH);
					frame.repaint();
				}
			}
			
			protected void process(List<BoardLogic> chunks) {
				BoardLogic boardState = chunks.get(chunks.size()-1);
				board.setState(boardState);
			}
		};
		playWorker.execute();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetButton) {
			game.remove(reset);
			logic.init();
			pane.repaint();
			play();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == pane) {
			if (pane.getSelectedIndex() == 1) {
				System.out.print(playWorker.cancel(true));
				gameSize = frame.getBounds().getSize();
				frame.setMinimumSize(settingsSize);
				frame.setSize(settingsSize);
				frame.setResizable(false);
			}
			else if (pane.getSelectedIndex() == 0) {
				frame.setResizable(true);
				frame.setMinimumSize(minimumSize);
				frame.setSize(gameSize);
				playerOne = playerOneSettings.getPlayer();
				playerTwo = playerTwoSettings.getPlayer();
				play();
			}
		}
	}

	public static void main(String[] args) {
		GUI gui = new GUI(); 
		gui.createAndShowGUI();
		gui.play();		
	}
}
