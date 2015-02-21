package connectfour;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TheRealGUI extends JFrame implements ActionListener {

	Board b = new Board();
	private JButton[] board = new JButton[7];
	private JLabel[][] labels = new JLabel[7][6];

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
				labels[a][b] = new JLabel("O");
				add(labels[a][b]);
				labels[a][b].setBounds(85 + a * 150, 15 + b * 100, 100, 40);
				labels[a][b].setForeground(Color.red);
			}
		}

<<<<<<< HEAD
	for(int a=1;a<board.length;a++)
	{
			board[a]= new JButton (" ");
=======
		// label=new JLabel("test");
		// label.setBounds(500,200,200,400);
		// add(label);

		for (int a = 0; a < board.length; a++) {
			board[a] = new JButton(" ");
>>>>>>> 612868451c5a412b7a23055f8b5a90248d13d381
			add(board[a]);
			board[a].setBounds(40 + a * 150, 575, 100, 40);
			board[a].setActionCommand("" + a + "");
			board[a].addActionListener(this);
		}

		setSize(1100, 700);
		setVisible(true);
		setTitle("Connect Four");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("1")) {
			board[1].setText("Color.green");
		}
		for (int a = 0; a < 7; a++) {
			if (e.getActionCommand().equals("" + a + "")) {
				board[a].setText("OK");
				b.move(a);
			}
<<<<<<< HEAD
	for(int a=0;a<7;a++)
	{
		if(e.getActionCommand().equals(""+a+""))
		{
			board[a].setText("OK");
			labels[a][b.move(a)].setText("test");
			
=======
>>>>>>> 612868451c5a412b7a23055f8b5a90248d13d381
		}
	}

	public void main(String[] args) {
		TheRealGUI GUI = new TheRealGUI();
	}
}
