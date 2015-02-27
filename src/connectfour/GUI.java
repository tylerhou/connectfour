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
		
		/** player one settings **/
		c.fill = GridBagConstraints.BOTH;
		c.weightx = c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		settings.add(new PlayerSettings("Player One"), c);
		
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
		settings.add(new PlayerSettings("Player Two"), c);
		
		pane.add("Settings", settings);
		
		frame.add(pane);
		
		pane.addChangeListener(this);
		
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
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == pane) {
			if (pane.getSelectedIndex() == 1) {
				gameSize = frame.getBounds().getSize();
				frame.setMinimumSize(settingsSize);
				frame.setSize(settingsSize);
				frame.setResizable(false);
			}
			else if (pane.getSelectedIndex() == 0) {
				frame.setResizable(true);
				frame.setMinimumSize(minimumSize);
				frame.setSize(gameSize);
			}
		}
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.createAndShowGUI();
		gui.play();		
	}
}
