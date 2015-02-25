package connectfour;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardDisplay extends JPanel {
	
	Human human;
	
	public BoardDisplay() {
		setLayout(new GridLayout(6, 7));
		for (int a = 0; a < 42; a++)
			add(new Tile(Color.red));
	}
	
	 private void createAndShowGUI() {
	        //Create and set up the window.
		 JFrame frame = new JFrame("Connect Four");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	         
		 //Add content to the window.
		 human = new Human();
		 frame.add(human);
	         
		 //Display the window.
		 frame.pack();
		 frame.setSize(500,500);
		 frame.setVisible(true);
	 }
	 
	 public static void main(String[] args) {
		 BoardDisplay display = new BoardDisplay();
		 display.createAndShowGUI();
		 while (true) {
			 System.out.println(display.human.getMove());
		 }
	 }
}
