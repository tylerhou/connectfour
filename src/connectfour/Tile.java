package connectfour;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class Tile extends JComponent {

	Color c; 
	public Tile(Color c) {
		this.c = c;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		double size = getHeight() < getWidth() ? getHeight() : getWidth();
		size *= .8;
		Shape circle = new Ellipse2D.Double((getWidth()-size)/2, (getHeight()-size)/2, size, size);
		g2d.setColor(this.c);
		g2d.fill(circle);
        g2d.dispose();
	}
}
