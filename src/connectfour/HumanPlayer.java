package connectfour;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class HumanPlayer extends JPanel implements Player, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1666151199582848862L;
	int move;
	JButton[] buttons;
	
	public HumanPlayer() {
		setLayout(new GridLayout(1, 7));
		buttons = new JButton[7];
		for (int a = 0; a < 7; a++) {
			buttons[a] = new JButton("Drop!");
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
		for (JButton button: buttons) {
			button.setEnabled(false);
		}
	}
	
	public void enableAll() {
		for (JButton button: buttons) {
			button.setEnabled(true);
		}
	}
	
	public void setLabels(String s) {
		for (JButton button: buttons) {
			button.setText(s);
		}
	}
	
	public JButton[] getButtons() {
		return buttons;
	}
	
	@Override
	public int getMove() {
		synchronized (this) {
			try {
				enableAll();
				wait();
			} catch (InterruptedException e) {
				return -1;
			}
		}
		return move;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		synchronized (this) {
			move = java.util.Arrays.asList(buttons).indexOf((JButton) e.getSource());
			disableAll();
			notify();
		}
	}
	
	public void setState(BoardLogic board) {
		
	}
}
