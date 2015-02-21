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
	private JLabel Winner;
	private JLabel colorOfWinner;
	int turns=0;

	public TheRealGUI() {

		setLayout(null);
		
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

		Winner=new JLabel("Winner:");
		add(Winner);
		Winner.setBounds(10,600,60,100);
		
		colorOfWinner=new JLabel("");
		add(colorOfWinner);
		colorOfWinner.setBounds(70,600,100,100);
		colorOfWinner.setFont(new Font("Wingdings",Font.BOLD,24));
		
		setSize(1100, 700);
		setVisible(true);
		setTitle("Connect Four");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {

		for (int a = 0; a < 7; a++) {
			if (e.getActionCommand().equals("" + a + "")) {
				board[a].setText("OK");
				turns++;
					if(turns % 2 == 0)
						labels[a][b.move(a)].setForeground(Color.red);
					if(turns % 2 == 1)
						labels[a][b.move(a)].setForeground(Color.blue);
			}
		}
		for(int c=0; c < board.length; c++)
		{
		if(b.isDraw())
			{
			colorOfWinner.setText("draw");
			}	
		if(b.getWinnerColor() == -1){
			board[c].setEnabled(false);
			colorOfWinner.setText("l");
			colorOfWinner.setForeground(Color.red);
			}
		if(b.getWinnerColor() == 1) {
			board[c].setEnabled(false);
			colorOfWinner.setText("l");
			colorOfWinner.setForeground(Color.blue);
			}
		}
	}
	public static void main(String[] args) {
		TheRealGUI GUI = new TheRealGUI();
	}
}
