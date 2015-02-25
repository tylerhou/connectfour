package connectfour;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class HumanPlayer extends JPanel implements Player, ActionListener {

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
}
