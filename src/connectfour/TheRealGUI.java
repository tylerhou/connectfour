package connectfour;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TheRealGUI extends JFrame implements ActionListener{

	GUI cow = new GUI();
	private JButton[] board=new JButton[7];
	private JLabel label;
	private JFrame frame;
	private JTable table;
	
	public TheRealGUI(){
	

	setLayout(null);
	
	/*frame=new JFrame();
	add(frame);*/
	String[] names={"1","2","3"};
	
	
	
	label=new JLabel("test");
	label.setBounds(500,200,200,400);
	add(label);
	
	for(int a=0;a<board.length;a++)
	{
			board[a]= new JButton (" ");
			add(board[a]);
			board[a].setBounds(40+a*150,500,100,50);
			board[a].setActionCommand("" + a + "");
			board[a].addActionListener(this);
	}
	

	
	setSize(1100,600);  
	setVisible(true); 
	setTitle("Connect Four");
	setResizable(false);
	setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	
	}
	
	public void actionPerformed(ActionEvent e) {
		
		
		
	}
}
