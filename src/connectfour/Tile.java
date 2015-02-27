package connectfour;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class Tile extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8767644151517255592L;
	private Color c;
	public static final Color transparent = new Color(0, 0, 0, 0);
	
	public Tile(Color c) {
		this.c = c;
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double size = getHeight() < getWidth() ? getHeight() : getWidth();
		size *= .8;
		Shape circle = new Ellipse2D.Double((getWidth()-size)/2, (getHeight()-size)/2, size, size);
		g2d.setColor(c);
		g2d.fill(circle);
        g2d.dispose();
	}

	public Color getColor() {
		return c;
	}
}
