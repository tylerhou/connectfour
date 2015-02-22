package connectfour;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.*;

public class TheRealGUI extends JFrame implements ActionListener {

	Board b = new Board();
	private JButton[] buttons = new JButton[7];
	private JLabel[][] labels = new JLabel[6][7];
	private JLabel Winner;
	private JLabel colorOfWinner;
	private JButton Reset;
	int turns=0;

	public TheRealGUI() {

		setLayout(null);
		
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				labels[row][col] = new JLabel("l");
				add(labels[row][col]);
				labels[row][col].setBounds(83 + col * 150, 20 + (-row+5) * 100, 100, 40);
				labels[row][col].setForeground(Color.white);
				labels[row][col].setFont(new Font("Wingdings", Font.BOLD, 24));
			}
		}
		for (int a = 0; a < buttons.length; a++) {
			buttons[a] = new JButton(" ");
			add(buttons[a]);
			buttons[a].setBounds(40 + a * 150, 580, 100, 40);
			buttons[a].setActionCommand("" + a + "");
			buttons[a].addActionListener(this);
		}

		Reset=new JButton("Reset");
		add(Reset);
		Reset.setActionCommand("Reset");
		Reset.addActionListener(this);
		
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
			b.move(a);
			buttons[a].setText("OK");
			}
		}
		//int[][] board = b.getBoard(); I think this is not needed...
		for (int row = 0; row < 6; ++row) {
			for (int col = 0; col < 7; ++col) {
				if (b.board[row][col] == 0) {
					labels[row][col].setForeground(Color.white);
				}
				else if (b.board[row][col] == 1) {
					labels[row][col].setForeground(Color.red);
				}
				else if (b.board[row][col] == -1) {
					labels[row][col].setForeground(Color.blue);
				}
			}
		}
		
		if (b.getWinner()!=null)
		{
			colorOfWinner.setText(Integer.toString(b.getWinnerColor()));
			for (JButton jb : buttons)
			jb.setVisible(false);
		}
		
		if (b.isDraw()) colorOfWinner.setText("Draw");
		
		if(e.getActionCommand().equals("Reset"))
		{
			for (int row = 0; row < 6; ++row) {
				for (int col = 0; col < 7; ++col) {
						labels[row][col].setForeground(Color.white);
						colorOfWinner.setText("");
					}
			}	
		}
		
		/*for (int a = 0; a < 7; a++) {
			if (e.getActionCommand().equals("" + a + "")) {
				board[a].setText("OK");
				turns++;
					if(turns % 2 == 0)
						labels[a][b.move(a)].setForeground(Color.red);
					if(turns % 2 == 1)
						labels[a][b.move(a)].setForeground(Color.blue);
			}
			if(b.move(a) > 0)
			board[a].setEnabled(false);
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
		}*/
	}
	public static void main(String[] args) {
		TheRealGUI GUI = new TheRealGUI();
	}
}
