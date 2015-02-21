package connectfour;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.*;

public class TheRealGUI extends JFrame implements ActionListener {

	Board b = new Board();
	private JButton[] board = new JButton[7];
	private JLabel[][] labels = new JLabel[7][6];
	private JLabel label;
	int turns=0;

	// private JFrame frame;
	// private JTable table;

	public TheRealGUI() {

		setLayout(null);

		/*
		 * Object[][] data={{"1"},{"2"},{"3"}}; String[]
		 * columnNames={"1","2","3"}; JTable table = new JTable(data,
		 * columnNames); table.setBackground(Color.green); could've used JTable
		 */

		for (int a = 0; a < 7; a++) {
			for (int b = 0; b < 6; b++) {
				labels[a][b] = new JLabel("l");
				add(labels[a][b]);
				labels[a][b].setBounds(83 + a * 150, 20 + (-b+5) * 100, 100, 40);
				labels[a][b].setForeground(Color.white);
				labels[a][b].setFont(new Font("Wingdings", Font.BOLD, 24));
			}
		}
		for (int a = 0; a < board.length; a++) {
			board[a] = new JButton(" ");
			add(board[a]);
			board[a].setBounds(40 + a * 150, 580, 100, 40);
			board[a].setActionCommand("" + a + "");
			board[a].addActionListener(this);
		}

		label=new JLabel("Winner:"+b.getWinnerColor());
		add(label);
		label.setBounds(10,600,100,100);
		
		setSize(1100, 700);
		setVisible(true);
		setTitle("Connect Four");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int a = 0; a < 7; a++) {
			if (e.getActionCommand().equals("" + a + "")) {
				board[a].setText("OK");
				turns++;
				if(turns % 2 ==0 )
				labels[a][b.move(a)].setForeground(Color.red);
				if(turns % 2 ==1)
				labels[a][b.move(a)].setForeground(Color.blue);
			}
		}
		if(b.isDraw())
		{
			for(int c= 0;c<board.length;c++)
			{
					board[c].setEnabled(false);
			}
		}	
		
	}

	public static void main(String[] args) {
		TheRealGUI GUI = new TheRealGUI();
	}
}
