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
	private JLabel Winner, colorOfWinner;
	private JButton Reset;
	private String w;
	int turns=0, r, c;

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
		Reset.setBounds(950,640,100,30);
		Reset.setActionCommand("Reset");
		Reset.addActionListener(this);
		
		Winner=new JLabel("Winner:");
		add(Winner);
		Winner.setBounds(10,600,60,100);
		
		colorOfWinner=new JLabel("");
		add(colorOfWinner);
		colorOfWinner.setBounds(70,600,150,100);
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
				if (b.top[a]==6){
					buttons[a].setText("Full");
					buttons[a].setEnabled(false);
				}
			c=a;
			}
		}
		
		r = b.top[c]-1;
		if (b.board[r][c] == 0) {
			labels[r][c].setForeground(Color.white);
		}
		else if (b.board[r][c] == 1) {
			labels[r][c].setForeground(Color.red);
		}
		else if (b.board[r][c] == -1) {
			labels[r][c].setForeground(Color.blue);
		}
		
		if (b.getWinner()!=null)
		{
			if (b.getWinnerColor()==-1) w = "Blue wins!";
			else w = "Red wins!";
			colorOfWinner.setText(w);
			for (JButton jb : buttons)
			jb.setEnabled(false);
		}
		
		if (b.isDraw()) colorOfWinner.setText("Draw");
		
		if(e.getActionCommand().equals("Reset"))
		{
			for (int col = 0; col < 7; ++col) {
				for (int row = 0; row < 6; ++row) {
						labels[row][col].setForeground(Color.white);
					}
				buttons[col].setEnabled(true);
				buttons[col].setText("");
			}
			colorOfWinner.setText("");
			b.init();
		}
	}
	
	public static void main(String[] args) {
		TheRealGUI GUI = new TheRealGUI();
	}
}
