package connectfour;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class HumanPlayer extends JPanel implements Player, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1666151199582848862L;
	int move;
	Button[] buttons;
	
	public HumanPlayer() {
		setLayout(new GridLayout(1, 7));
		buttons = new Button[7];
		for (int a = 0; a < 7; a++) {
			buttons[a] = new Button("Drop!");
			add(buttons[a]);
			buttons[a].addActionListener(this);
		}
	}
	
	public void disable(int col) {
		buttons[col].setEnabled(false);
	}
	
	public void enable(int col) {
		buttons[col].setEnabled(true);
	}
	
	public void disableAll() {
		for (Button button: buttons) {
			button.setEnabled(false);
		}
	}
	
	public void enableAll() {
		for (Button button: buttons) {
			button.setEnabled(true);
		}
	}
	
	public void setLabels(String s) {
		for (Button button: buttons) {
			button.setLabel(s);
		}
	}
	
	public Button[] getButtons() {
		return buttons;
	}
	
	@Override
	public int getMove() {
		synchronized (this) {
			try {
                wait();
            }
			catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
		return move;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		synchronized (this) {
			move = java.util.Arrays.asList(buttons).indexOf((Button) e.getSource());
			notify();
		}
	}
	
	public void setState(BoardLogic board) {
		
	}
}
