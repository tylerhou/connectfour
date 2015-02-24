package connectfour;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.*;

public class TheRealGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7479647450255253743L;
	Board b = new Board();
	AI ai =  new AI(1);
	IPair last;
	private JButton[] buttons = new JButton[7];
	private JLabel[][] labels = new JLabel[6][7];
	private JLabel colorOfWinner, difficulty;
	private JButton Reset;
	private String w;
	private int turns=0, r, c, diff=3;
	private JComboBox difficult;

	public TheRealGUI() {

		setLayout(null);
		
		for (int row = 0; row < 6; row++) {    //creating the 7 column 6 row board
			for (int col = 0; col < 7; col++) {
			labels[row][col] = new JLabel("�");
				add(labels[row][col]);
				labels[row][col].setBounds(83 + col * 150, 20 + (-row+5) * 100, 100, 40);
				labels[row][col].setForeground(Color.white);
				labels[row][col].setFont(new Font("Times New Roman", Font.BOLD, 120));
			}
		}
		for (int a = 0; a < buttons.length; a++) {  //creating the 7 buttons at the bottom
			buttons[a] = new JButton(" ");
			add(buttons[a]);
			buttons[a].setBounds(50 + a * 150, 580, 100, 40);
			buttons[a].setActionCommand("" + a + "");
			buttons[a].addActionListener(this);
		}

		Reset=new JButton("Reset");  //reset button
		add(Reset);
		Reset.setBounds(950,640,100,30);
		Reset.setActionCommand("Reset");
		Reset.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent f) {
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
	     			return;
	     		}
	         }
	      });
		
		difficulty= new JLabel("Difficulty:");  //"Difficulty:"
		add(difficulty);
		difficulty.setBounds(450,640,100,30);
		
		difficult = new JComboBox();  //Dropdown list for difficulty options, 1-5
		for(int i=1;i<6;i++)
			difficult.addItem(i);
		add(difficult);
		difficult.setBounds(550,640,100,30);
		difficult.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent f) {
	        	 {
	     			diff = (difficult.getSelectedIndex()*2+2);
	        	 }
	         }
	      });
		
		colorOfWinner=new JLabel("");  //appears at bottom left of the window when win or lose
		add(colorOfWinner);
		colorOfWinner.setBounds(60,600,150,100);
		colorOfWinner.setFont(new Font("Times New Roman",Font.BOLD,24));
		
		setSize(1100, 700); //JFrame
		setVisible(true);
		setTitle("Connect Four");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {

		//when button is pressed, move
		for (int a = 0; a < 7; a++) {
			if (e.getActionCommand().equals("" + a)) {
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
		labels[r][c].setForeground(Color.red);
		
		if(check()) AImove();
	}
		
	public void AImove() {
		ai.setState(b);
		last=b.move(ai.getMove(diff)); 
		//try { Thread.sleep(500); } catch (InterruptedException e) {}
		labels[last.first][last.second].setForeground(Color.blue);
		check();
	}
		
	public boolean check() {
		if (b.top[last.second]==6){
			buttons[last.second].setText("Full");
			buttons[last.second].setEnabled(false);
		}
		if (b.getWinner()!=null)
		{
			if (b.getWinnerColor()==-1) w = "You lost!";
			else w = "You won!";
			colorOfWinner.setText(w);
			for (JButton jb : buttons)
			jb.setEnabled(false);
		}
		
		if (b.isDraw()) colorOfWinner.setText("Draw");
		
		return !b.isTerminal();
	}
	
	public static void main(String[] args) {
		TheRealGUI GUI = new TheRealGUI();
	}
}
